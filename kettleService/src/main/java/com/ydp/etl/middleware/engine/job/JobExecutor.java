package com.ydp.etl.middleware.engine.job;

import com.google.common.base.Preconditions;
import com.ydp.etl.middleware.engine.AbstractExecutor;
import com.ydp.etl.middleware.engine.AbstractKettleTask;
import com.ydp.etl.middleware.engine.HTTPApi;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class JobExecutor  extends AbstractExecutor{





    public JobExecutor() {
    }

    public String pausTrans(AbstractKettleTask jobs) {
        HttpGet httpRequest=new HttpGet(HTTPApi.PauseTrans.getUrl());
        return doExecutor(httpRequest);
    }

    public String startExec(){
        return null;
    }

    public String status(){
        HttpGet httpRequest=new HttpGet(HTTPApi.PauseTrans.getUrl());
        return null;
    }

    public String cleanupTrans(){
        return null;
    }

    public String registryTrans(AbstractKettleTask jobs) throws UnsupportedEncodingException {
        HttpPost httpRequest=new HttpPost(HTTPApi.RegistryTrans.getUrl());
        httpRequest.addHeader("Content-Type", "application/xml");

        HttpEntity body=new StringEntity(jobs.getXmlBody());
        httpRequest.setEntity(body);

        return doExecutor(httpRequest);
    }


    public void writeTrans(){

    }
}
