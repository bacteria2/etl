package com.ydp.etl;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;


@SpringBootApplication
@EnableFeignClients
@EnableCircuitBreaker
@EnableDiscoveryClient
public class TestRunner {
    public static void main(String[] args) {
        SpringApplication.run(TestRunner.class,args);
    }

//    @Bean
//    @ConditionalOnProperty(name="proxy.enable",havingValue = "true")
//    public BeanPostProcessor httpClientFactory(){
//
//        BeanPostProcessor processor=new BeanPostProcessor(){
//            @Override
//            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
//                return bean;
//            }
//
//            @Override
//            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//                if(bean instanceof SimpleClientHttpRequestFactory){
//                    Proxy proxy = new Proxy(Proxy.Type.HTTP,new InetSocketAddress("192.168.40.236",8888));
//                    ((SimpleClientHttpRequestFactory)bean).setProxy(proxy);
//                }
//                return  bean;
//            }
//        };
//
//        return processor;
//
//    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        RestTemplate restTemplate=  new RestTemplate();
        Proxy proxy = new Proxy(Proxy.Type.HTTP,new InetSocketAddress("192.168.40.236",8888));
        ( (SimpleClientHttpRequestFactory)restTemplate.getRequestFactory()).setProxy(proxy);
        return restTemplate;
    }
}
