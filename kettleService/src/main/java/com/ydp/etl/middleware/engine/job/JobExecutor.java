package com.ydp.etl.middleware.engine.job;

import com.ydp.etl.middleware.engine.*;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.pentaho.di.cluster.SlaveServer;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.job.Job;
import org.pentaho.di.job.JobExecutionConfiguration;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.repository.filerep.KettleFileRepository;
import org.pentaho.di.repository.filerep.KettleFileRepositoryMeta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

public class JobExecutor extends Executor {
    private Logger log = LoggerFactory.getLogger(JobExecutor.class);

    private ClusterInfo clusterInfo;

    private RunningProperties runningProperties;

    public JobExecutor(String host, String port, String username, String password) {
        super(host, port, username, password);
    }

    public JobExecutor(ClusterInfo clusterInfo, RunningProperties runningProperties) {
        super(clusterInfo);
        this.clusterInfo = clusterInfo;
        this.runningProperties = runningProperties;
    }

    public String pause(AbstractKettleTask jobs) {
        HttpGet httpRequest = new HttpGet(HTTPApi.PauseTrans.getUrl());
        return doExecutor(httpRequest);
    }

    public JobInfo status(String id, String name) throws DocumentException {
        String statusUrl = String.format(HTTPApi.JobStatus.getUrl(), name, id);
        HttpGet request = getRequestGen(statusUrl);
        String xmlBody = doExecutor(request);


        return xmlBodyToJobInfo(xmlBody);
    }

    @Override
    public boolean cleanup(String name) throws DocumentException {
        String statusUrl = String.format(HTTPApi.RemoveJob.getUrl(), name);
        return super.cleanup(statusUrl);
    }

    public JobInfo executeJob(String jobName) throws Exception {
        JobInfo jobInfo = new JobInfo();
        jobInfo.setName(jobName);

        log.info("execute job:{}",jobInfo.getName());
        try {
            KettleEnvironment.init();

            SlaveServer slaveServer = new SlaveServer(clusterInfo.getName(), clusterInfo.getHost()
                    , clusterInfo.getPort(), clusterInfo.getUsername(), clusterInfo.getPassword());


            KettleFileRepository repository = new KettleFileRepository();
            repository.setRepositoryMeta(
                    new KettleFileRepositoryMeta(
                            "KettleFileRepository",
                            "filerepository",
                            "File repository",
                            runningProperties.getRepositoryPath()));

            JobMeta jobMeta = repository.loadJob(
                    jobName,
                    repository.findDirectory("/"), null, null);

            JobExecutionConfiguration jobExecutionConfiguration = new JobExecutionConfiguration();
            jobExecutionConfiguration.setRemoteServer(slaveServer);
            jobExecutionConfiguration.setRepository(repository);
            jobExecutionConfiguration.setExpandingRemoteJob(true);

            log.info("finish job init");
            String resultId = Job.sendToSlaveServer(jobMeta, jobExecutionConfiguration, repository, null);

            log.info("job:{},submit", resultId);

            jobInfo.setId(resultId);
            return jobInfo;
        } catch (KettleException e) {
            log.error("submit job error:", e);
            throw e;
        }


    }


    private JobInfo xmlBodyToJobInfo(String xmlBody) throws DocumentException {
        Document document = DocumentHelper.parseText(xmlBody);
        Element status = document.getRootElement();
        JobInfo jobInfo = new JobInfo();

        jobInfo.setName(getStringTextByXpath(status, "/jobstatus/jobname"));
        jobInfo.setId(getStringTextByXpath(status, "/jobstatus/id"));

        String textStatus = getStringTextByXpath(status, "/jobstatus/status_desc");
        switch (textStatus.toUpperCase()) {
            case "FINISHED":
                jobInfo.setStatus(JobInfo.JobStatus.FINISHED);
                break;
            case "WAITING":
                jobInfo.setStatus(JobInfo.JobStatus.WAITTING);
                break;
            case "RUNNING":
                jobInfo.setStatus(JobInfo.JobStatus.RUNNING);
                break;
            case "PAUSED":
                jobInfo.setStatus(JobInfo.JobStatus.PAUSED);
                break;
            default:
                jobInfo.setStatus(JobInfo.JobStatus.FAILD);
        }
        return jobInfo;
    }
}
