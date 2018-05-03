package com.ydp.etl.middleware.services;

import com.ydp.etl.middleware.engine.job.JobExecutor;
import com.ydp.etl.middleware.engine.job.JobInfo;
import com.ydp.etl.middleware.engine.repository.RepositoryExecutor;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class JobService {
    private Logger log= LoggerFactory.getLogger(JobService.class);
    @Autowired
    RepositoryExecutor repositoryExecutor;

    @Autowired
    JobExecutor jobExecutor;


    public JobInfo runJob(String jobName) throws Exception {
        return jobExecutor.executeJob(jobName);
    }

    public JobInfo getJobStatus(String id, String name){
        try {
            return jobExecutor.status(id,name);
        } catch (DocumentException e) {
           log.error("request status error",e);
        }
        return new JobInfo();
    }


    public void writeJob(String xmlBody,String fileName) throws IOException {
        repositoryExecutor.writeFile(xmlBody,fileName+".kjb");
    }

    public void removeJob(String fileName){
        cleanJob(fileName);
        repositoryExecutor.removeFile(fileName+".kjb");
    }

    public void cleanJob(String fileName){
        try {
            while (jobExecutor.cleanup(fileName)){
                log.info("remove one job:{}",fileName);
            };
        } catch (DocumentException e) {
            log.error("remove error",e);
        }

    }



}
