package com.ydp.etl.middleware;

import com.google.common.collect.Lists;
import org.apache.http.Header;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.LinkedList;
import java.util.List;


@SpringBootApplication
@EnableEurekaClient

public class KettleServiceStarter {

    @Value("kettle.cluster.username")
    public String username;

    @Value("kettle.cluster.password")
    public String password;



    public static void main(String[] args) {

        new SpringApplicationBuilder()
                .sources(KettleServiceStarter.class)
                .bannerMode(Banner.Mode.OFF)
                .web(true)
                .logStartupInfo(false)
                .build()
                .run(args);
    }

    @Bean(name = "restClient")
    public RestTemplate template() {
        return new RestTemplate();
    }

    @Bean(name = "kettleHttpClient")
    public org.apache.http.client.HttpClient httpClient() {
        //默认授权
        Header securityHeader=new BasicHeader("Authorization", "Basic "+basicEncode());
        List<Header> defaultHeader=new LinkedList<>();
        defaultHeader.add(securityHeader);

        return HttpClientBuilder.create()
                .disableCookieManagement()
                .setDefaultHeaders(defaultHeader)
                .build();
    }

    private String basicEncode(){
        return Base64.getEncoder().encodeToString(String.format("%s:%s",username,password).getBytes());
    }
}
