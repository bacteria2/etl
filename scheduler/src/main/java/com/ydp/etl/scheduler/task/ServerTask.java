package com.ydp.etl.scheduler.task;

import com.ydp.etl.common.ServiceURL;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.client.RestTemplate;

public class ServerTask extends QuartzJobBean {
    private Logger log = LoggerFactory.getLogger(ServerTask.class);

    @Autowired
    RestTemplate restTemplate;

    //清理trans和job
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        HttpEntity<String> resp = restTemplate.getForEntity(ServiceURL.Etl.getUrl("/cleanAll"), String.class);
        log.info("clean all execute,{}", resp.getBody());
    }
}
