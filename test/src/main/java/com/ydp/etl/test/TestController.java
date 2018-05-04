package com.ydp.etl.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.ydp.etl.common.CommonResponse;
import com.ydp.etl.services.RestService;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TestController {
    @Autowired KettleService service;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    LoadBalancerClient loadBalancerClient;

    @Autowired
    RestService restService;

    @Value("${test.string}")
    private String test;

    @GetMapping("/test")
    public String testFeign(){
       return service.getStatusByJobId("t11233t");
    }

    @GetMapping("/test2")
    public String testRestTemplate(){
        String url = "http://kettle-service/job/getStatus?id=1&name=er";
        return   restTemplate.getForObject(url,String.class);
    }

    @GetMapping("/test4")
    public String test4() throws UnsupportedEncodingException {

        MultiValueMap<String, Object> map=new LinkedMultiValueMap<>();
        map.add("name","testjos");

        HttpEntity<Map> mapHttpEntity=new HttpEntity<>(map);
        ResponseEntity<CommonResponse> response = restTemplate.postForEntity(
                String.format("http://kettle-service%s","/job/run"),
                mapHttpEntity,
                CommonResponse.class);
        return response.getBody().getData().toString();
    }

    @GetMapping("/test3")
    public String testHystrix(){
        return restService.getJob();
    }

    @GetMapping("/test5")
    public String test5(){
        return test;
    }
}
