package com.ydp.etl.scheduler.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.QuartzJobBean;


/**
 * @author IonCannon
 * @date 2018/4/28
 * @decription : content
 */

public class HelloWorldTask extends QuartzJobBean {
    private Logger log = LoggerFactory.getLogger(HelloWorldTask.class);

    @Value("${spring.profiles.active}")
    private String profile;

    @Value("${spring.quartz.job-store-type}")
    private String testString;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Integer i = jobExecutionContext.getMergedJobDataMap().getIntegerFromString("test");
        System.out.println("hello world");
        System.out.println("test number is "+i);
        log.info("active profile is {}", profile);
        log.info("test string {}", testString);
    }
}
