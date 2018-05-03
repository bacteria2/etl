package com.ydp.etl.middleware.engine.trans;

import com.ydp.etl.middleware.engine.ClusterInfo;
import com.ydp.etl.middleware.engine.Executor;
import com.ydp.etl.middleware.engine.HTTPApi;
import com.ydp.etl.middleware.engine.job.JobExecutor;
import org.apache.http.client.methods.HttpGet;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransformationExecutor extends Executor {

    private Logger log = LoggerFactory.getLogger(TransformationExecutor.class);

    protected TransformationExecutor(String host, String port, String username, String password) {
        super(host, port, username, password);
    }

    public TransformationExecutor(ClusterInfo clusterInfo) {
        super(clusterInfo);
    }

    @Override
    public boolean cleanup(String name) throws DocumentException {
        String statusUrl = String.format(HTTPApi.RemoveTrans.getUrl(), name);
        return super.cleanup(statusUrl);
    }

}
