package com.ydp.etl.services;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestService {
    @Autowired
    private RestTemplate template;

    @HystrixCommand(fallbackMethod="fallBack")
    public String getJob(){
        String url = "http://KETTLE-SERVICE/kettle/job/getStatus?id=1";
        return   template.getForObject(url,String.class);
    }

    public String fallBack(){
        return "service unavailable now";
    }
}
