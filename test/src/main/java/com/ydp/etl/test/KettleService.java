package com.ydp.etl.test;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "kettle-service",fallback = KettleServiceFallBack.class)
public interface KettleService {

    @RequestMapping(value="/kettle/trans/submit")
    default String submitTrans(@RequestParam(value = "body") String xmlBody){
        return "default fallbackMessage";
    };

    @RequestMapping(value="/kettle/trans/getStatus")
    default  String getStatusByTransId(@RequestParam(value = "id")  String id){
        return "default fallbackMessage";
    };

    @RequestMapping(value="/kettle/job/submit")
    default String submitJob(@RequestParam(value = "body")  String xmlBody){
        return "default fallbackMessage";
    };

    @RequestMapping(value="/kettle/job/getStatus")
    default String getStatusByJobId(@RequestParam(value = "id")   String id){
        return "default fallbackMessage";
    }

}

