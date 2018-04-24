package com.ydp.etl.test;

import com.ydp.etl.services.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class TestController {
    @Autowired KettleService service;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    LoadBalancerClient loadBalancerClient;

    @Autowired
    RestService restService;

    @GetMapping("/test")
    public String testFeign(){
       return service.getStatusByJobId("t11233t");
    }

    @GetMapping("/test2")
    public String testRestTemplate(){

        String url = "http://KETTLE-SERVICE/kettle/job/getStatus?id=1";
        return   restTemplate.getForObject(url,String.class);
    }

    @GetMapping("/test3")
    public String testHystrix(){
        return restService.getJob();
    }
}
