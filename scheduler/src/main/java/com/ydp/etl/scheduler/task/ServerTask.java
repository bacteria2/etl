package com.ydp.etl.scheduler.task;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.client.RestTemplate;

public class ServerTask extends QuartzJobBean {
    @Autowired
    RestTemplate restTemplate;





    //清理trans和job
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

    }
}
