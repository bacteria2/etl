package com.ydp.etl.middleware.engine.server;

import com.google.common.base.Preconditions;
import com.ydp.etl.middleware.engine.AbstractExecutor;
import com.ydp.etl.middleware.engine.HTTPApi;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;

import java.util.Map;

public class ServerInfoExecutor extends AbstractExecutor {


    public ServerInfoExecutor(String host, CloseableHttpClient httpClient) {
        super(host, httpClient);
    }

    public ServerInfo getServerInfo() {
        Preconditions.checkNotNull(this.host);
        Preconditions.checkNotNull(this.httpClient);
        HttpGet getRequest=getRequestGen(HTTPApi.ServerStatus.getUrl(),null);
        doExecutor(getRequest);
        return new ServerInfo();
    }


}
