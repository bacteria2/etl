package com.ydp.etl.scheduler.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ydp.etl.common.CommonResponse;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.function.Supplier;


public class JobTask extends QuartzJobBean {
    private final String etlServiceUrl = "http://kettle-service%s";
    private Logger log = LoggerFactory.getLogger(JobTask.class);

    @Autowired
    RestTemplate restTemplate;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap jobName=jobExecutionContext.getMergedJobDataMap();

        MultiValueMap<String,Object> requestMap=new LinkedMultiValueMap<>();
        requestMap.add("name",jobName.get("name")) ;

        ResponseEntity<CommonResponse> response = restTemplate.postForEntity(String.format(etlServiceUrl,"/job/run"),requestMap, CommonResponse.class);
        System.out.println(JSON.toJSONString( response.getBody()));

        //log.info("task id:{} is runnning", jsonObject.getString("id"));

    }

    class JobInfo{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
