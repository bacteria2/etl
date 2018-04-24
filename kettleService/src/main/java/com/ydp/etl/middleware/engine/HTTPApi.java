package com.ydp.etl.middleware.engine;


import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestFactory;
import org.apache.http.MethodNotSupportedException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.DefaultHttpRequestFactory;

import java.rmi.registry.Registry;

public enum HTTPApi {

    //trans
    RegistryTrans("/kettle/registerTrans", "POST"),
    RunTrans("/kettle/runTrans/?xml=Y", "GET"),
    PrepareExec("/kettle/prepareExec/?xml=Y", "GET"),
    StartExec("/kettle/startExec/?xml=Y", "GET"),
    ExecuteTrans("/kettle/executeTrans/?xml=Y", "GET"),
    StartTrans("/kettle/startTrans/?xml=Y", "GET"),
    PauseTrans("/kettle/pauseTrans/?name=%s", "GET"),
    StopTrans("/kettle/stopTrans/?xml=Y", "GET"),
    TransStatus("/kettle/transStatus/?xml=Y", "GET"),
    RemoveTrans("/kettle/removeTrans/?xml=Y", "GET"),
    CleanupTrans("/kettle/cleanupTrans/?xml=Y", "GET"),
    ServerStatus("/kettle/status/?xml=Y", "GET"),

    //server
    AllocateSocket("/kettle/allocateSocket/?xml=Y", ""),
    ListSocket("/kettle/listSocket/?xml=Y", ""),
    NextSequence("/kettle/nextSequence/?xml=Y", ""),
    RegistrySlave("/kettle/registerSlave/?xml=Y", ""),
    GetSlaves("/kettle/getSlaves/?xml=Y", ""),
    Status("/kettle/status/?xml=Y", ""),
    Stop("/kettle/stopCarte/?xml=Y", ""),

    //Jobs
    ExecuteJob("/kettle/executeJob/?xml=Y", "GET"),
    StartJob("/kettle/startJob/?xml=Y&name=%s&id=%s&from=0", "GET"),
    StopJob("/kettle/stopJob/?xml=Y&name=%s&id=%s&from=0", "GET"),
    RemoveJob("/kettle/removeJob/?xml=Y&name=%s&id=%s", "GET"),
    RegisterJob("/kettle/registerJob/?xml=Y", "POST"),
    JobStatus("/kettle/jobStatus/?xml=Y&name=%s&id=%s", "GET");


    private String url;
    private String method;

    HTTPApi(String url, String method) {
        this.url = url;
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }
}
