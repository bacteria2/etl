package com.ydp.etl.middleware.engine;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.dom4j.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


/**
 *
 * */
public class Executor {
    protected String host;
    protected String port;
    protected CloseableHttpClient httpClient;
    private String username;
    private String password;
    protected DocumentFactory documentFactory;

    private static final String PASSWORD_ENCRYPTED_PREFIX="Encrypted ";
    private static final int RADIX = 16;

    private Logger log = LoggerFactory.getLogger(Executor.class);

    protected Executor(String host,String port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.httpClient = httpClient();
        this.documentFactory = DocumentFactory.getInstance();
    }

    protected Executor(ClusterInfo clusterInfo){
        this(clusterInfo.getHost(),clusterInfo.getPort(),clusterInfo.getUsername(),clusterInfo.getPassword());
    }


    protected HttpGet getRequestGen(String url) {
        return getRequestGen(url, null);
    }

    protected HttpGet getRequestGen(String url, List<NameValuePair> parameter) {
        Preconditions.checkNotNull(url);
        HttpGet getRequest = new HttpGet();

        try {
            URI uri = uriBuild(url, parameter);
            getRequest.setURI(uri);
        } catch (URISyntaxException e) {
            log.error("uri build error", e);
        }
        log.info("GetRequest generate completed,url:{}", url);
        return getRequest;
    }

    protected HttpPost postRequestGen(String url, List<NameValuePair> parameter) {
        Preconditions.checkNotNull(url);
        HttpPost postRequest = new HttpPost(url);
        postRequest.setHeader("Content-Type", "application/x-www-form-urlencoded");
        if (parameter != null) {
            try {
                postRequest.setEntity(new StringEntity(parameter.stream()
                        .map((Function<NameValuePair, String>) input -> String.format("%s=%s", input.getName(), input.getValue()))
                        .reduce((s, s2) -> String.format("%s&%s", s, s2)).toString()));
            } catch (UnsupportedEncodingException e) {
                log.error("post body parse error:", e);
            }
        }

        return postRequest;
    }

    protected HttpPost registryPostRequest(String url, String body) {
        Preconditions.checkNotNull(url);
        Preconditions.checkNotNull(body);

        HttpPost post = new HttpPost(url);
        try {
            post.setEntity(new StringEntity(body));
        } catch (UnsupportedEncodingException e) {
            log.error("post body parse error:", e);
        }

        return post;
    }

    protected String doExecutor(HttpRequest request) {
       return  doExecutor(request,this.host,this.port);
    }

    protected String doExecutor(HttpRequest request,String host,String port){
        Preconditions.checkNotNull(this.host);
        String returnValue = "";
        try {
            CloseableHttpResponse response = httpClient.execute(new HttpHost(host,Integer.parseInt(port)), request);
            returnValue = EntityUtils.toString(response.getEntity());
            response.close();
        } catch (IOException e) {
            log.error("request etl cluster error",e);
        }
        return returnValue;
    }


    protected String getStringTextByXpath(Element element, String xpath) {
        Node node = element.selectSingleNode(xpath);
        return node==null ? "" : node.getText();
    }

    //解密
    protected  String decode( String encodedPassword, boolean optionallyEncrypted ) {

        if ( encodedPassword == null ) {
            return null;
        }

        if ( optionallyEncrypted ) {

            if ( encodedPassword.startsWith( PASSWORD_ENCRYPTED_PREFIX ) ) {
                encodedPassword = encodedPassword.substring( PASSWORD_ENCRYPTED_PREFIX.length() );
                return decryptPasswordInternal( encodedPassword );
            } else {
                return encodedPassword;
            }
        } else {
            return decryptPasswordInternal( encodedPassword );
        }
    }

    private String decryptPasswordInternal( String encrypted ) {
        if ( encrypted == null ) {
            return "";
        }
        if ( encrypted.length() == 0 ) {
            return "";
        }
        BigInteger bi_confuse = new BigInteger( "0933910847463829827159347601486730416058" );

        try {
            BigInteger bi_r1 = new BigInteger( encrypted, RADIX );
            BigInteger bi_r0 = bi_r1.xor( bi_confuse );

            return new String( bi_r0.toByteArray() );
        } catch ( Exception e ) {
            return "";
        }
    }

    private URI uriBuild(String url, List<NameValuePair> parameter) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder(url);
        if (parameter != null) uriBuilder.addParameters(parameter);
        return uriBuilder.build();
    }

    private CloseableHttpClient httpClient() {
        //默认授权
        Header securityHeader = new BasicHeader("Authorization", "Basic " + basicEncode());
        List<Header> defaultHeader = new LinkedList<>();
        defaultHeader.add(securityHeader);

        return HttpClientBuilder.create()
                .disableCookieManagement()
                .setDefaultHeaders(defaultHeader)
                .build();
    }

    private String basicEncode() {
        return Base64.getEncoder().encodeToString(String.format("%s:%s", username, password).getBytes());
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    /**
     * 移除任务
     * */
    protected boolean cleanup(String url) throws DocumentException {
        HttpGet request = getRequestGen(url);
        String xmlBody = doExecutor(request);

        Document document = DocumentHelper.parseText(xmlBody);
        Element status = document.getRootElement();
        String resp = getStringTextByXpath(status, "/webresult/result");
        if ("OK".equals(resp)) {
            return true;
        }
        log.info("remove file error,detail:{}", getStringTextByXpath(status, "/webresult/message"));
        return false;

    }
}
