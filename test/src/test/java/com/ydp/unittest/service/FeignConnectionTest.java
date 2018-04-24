package com.ydp.unittest.service;


//import com.ydp.etl.test.KettleService;

import com.ydp.unittest.BaseTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.Base64;

//import org.springframework.cloud.openfeign.EnableFeignClients;


public class FeignConnectionTest extends BaseTest {
    //  @Autowired
    // KettleService service;
    //  @Test
    // public void transSubmitTest() {
    //    System.out.println(service.getStatusByJobId("ttt1231232"));
    //  }
    private  int[] a=new int[10];
    private long b=1000;
    private long c;

    @Test
    public void testRest() {
        RestTemplate restTemplate = new RestTemplate();
        String body = restTemplate.getForObject("http://www.baidu.com", String.class);

        System.out.println(body);
    }

    @Test
    public void testBase64(){
        System.out.println(Base64.getEncoder().encodeToString(String.format("%s:%s","cluster","cluster").getBytes()));
    }
    @Test
    public void testArray(){
        System.out.println(a[4]);
        System.out.println(b);
        System.out.println(c);
    }

    private static String Stringformat(String host,String... args ){
        return MessageFormat.format("{0}host1,{1},%s",host,args);
    }
}
