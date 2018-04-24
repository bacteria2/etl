package com.ydp.etl.middleware.engine;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.function.BinaryOperator;

public class AbstractExecutor {
    protected String host;
    protected CloseableHttpClient httpClient;
    protected boolean isXml=false;

    private Logger log = LoggerFactory.getLogger(AbstractExecutor.class);

    public AbstractExecutor() {

    }

    public AbstractExecutor(String host, CloseableHttpClient httpClient) {
        this.host = host;
        this.httpClient = httpClient;
    }


    protected HttpGet getRequestGen(String url, List<NameValuePair> parameter) {
        Preconditions.checkNotNull(url);
        URI uri = uriBuild(url, parameter);
        Preconditions.checkNotNull(uri);
        HttpGet getRequest = new HttpGet(uri);
        log.info("GetRequest generate completed,url:%s", url);
        return getRequest;
    }

    protected HttpPost postRequestGen(String url, List<NameValuePair> parameter) {
        Preconditions.checkNotNull(url);
        HttpPost postRequest=  new HttpPost(url);
        postRequest.setHeader("Content-Type","application/x-www-form-urlencoded");
        if(parameter!=null){
            try {
                postRequest.setEntity(new StringEntity(parameter.stream()
                        .map((Function<NameValuePair, String>) input -> String.format("%s=%s",input.getName(),input.getValue()))
                        .reduce((s, s2) -> String.format("%s&%s",s,s2)).toString()));
            } catch (UnsupportedEncodingException e) {
                log.error("post body parse error:", e);
            }
        }

        return postRequest;
    }

    protected HttpPost registryPostRequest(String url,String body){
        Preconditions.checkNotNull(url);
        Preconditions.checkNotNull(body);

        HttpPost post=new HttpPost(url);
        try {
            post.setEntity(new StringEntity(body));
        } catch (UnsupportedEncodingException e) {
            log.error("post body parse error:", e);
        }

        return post;
    }

    protected String doExecutor(HttpRequest request) {
        Preconditions.checkNotNull(this.host);
        String returnValue = "";
        try {
            CloseableHttpResponse response = httpClient.execute(new HttpHost(this.host), request);
            returnValue= EntityUtils.toString(response.getEntity());
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnValue;
    }


    private URI uriBuild(String url, List<NameValuePair> parameter) {
        try {
            URIBuilder uriBuilder = new URIBuilder(url);
            if(parameter!=null) uriBuilder.addParameters(parameter);
            return uriBuilder.build();
        } catch (URISyntaxException e) {
            log.error("uri builde error", e);
        }
        return null;
    }


}
