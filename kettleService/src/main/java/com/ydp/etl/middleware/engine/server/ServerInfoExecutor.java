package com.ydp.etl.middleware.engine.server;


import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.ydp.etl.middleware.engine.ClusterInfo;
import com.ydp.etl.middleware.engine.Executor;
import com.ydp.etl.middleware.engine.HTTPApi;
import org.apache.http.client.methods.HttpGet;
import org.dom4j.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ServerInfoExecutor extends Executor {

    private Logger log = LoggerFactory.getLogger(ServerInfoExecutor.class);

    public ServerInfoExecutor(String host, String port, String username, String password) {
        super(host, port, username, password);
    }

    public ServerInfoExecutor(ClusterInfo clusterInfo) {
        super(clusterInfo);
    }

    /**
     * 获取子服务器运行状态
     */
    public ServerInfo getServerInfo() {
        Preconditions.checkNotNull(this.host);
        Preconditions.checkNotNull(this.httpClient);
        //基本信息获取
        HttpGet getRequest = getRequestGen(HTTPApi.ServerStatus.getUrl());
        String xmlBody = doExecutor(getRequest);
        ServerInfo serverInfo = xmlBodyToServerInfo(xmlBody);
        //子节点信息获取
        HttpGet slaveServerInfoRequest = getRequestGen(HTTPApi.GetSlaves.getUrl(), null);
        String slaveServerInfoBody = doExecutor(slaveServerInfoRequest);
        serverInfo.setSlaves( getSlaveServerInfoList(slaveServerInfoBody));

        return serverInfo;
    }

    /**
     * 获取从服务器信息
     * */
    public List<Map> slaveInfo(){
        Preconditions.checkNotNull(this.host);
        Preconditions.checkNotNull(this.httpClient);
        //子节点信息获取
        HttpGet slaveServerInfoRequest = getRequestGen(HTTPApi.GetSlaves.getUrl());
        String slaveServerInfoBody = doExecutor(slaveServerInfoRequest);

        return getSlaveServerInfoList(slaveServerInfoBody);
    }

    public void cleanAllTransAndJob(){
        ServerInfo serverInfo= getServerInfo();
        List<Map> hosts= serverInfo.getSlaves();
        //添加master地址到hosts
        Map hostMap=new HashMap();
        hostMap.put("hostname",getHost());
        hostMap.put("port",getPort());
        hosts.add(hostMap);

        //循环列表，清楚所有的trans和job
        hosts.forEach(map -> {
            String host=(String)map.get("hostname");
            String port=(String)map.get("port");
            cleanHostTask(host,port);
        });

    }
    private void cleanHostTask(String hostname,String port){
        //获取status信息
        HttpGet slaveServerInfoRequest = getRequestGen(HTTPApi.ServerStatus.getUrl());
        String serverInfoBody = doExecutor(slaveServerInfoRequest,hostname,port);

        //得到serverInfo后删除每一个job
        ServerInfo serverInfo= xmlBodyToServerInfo(serverInfoBody);

        Preconditions.checkNotNull(serverInfo);
        //循环删除trans
        serverInfo.getTransList().forEach(map -> {
            String transName=(String)map.get("transName");
            String id=(String)map.get("id");

            String transCleanUrl = String.format(HTTPApi.RemoveTransWithId.getUrl(), transName,id);
            doExecutor(getRequestGen(transCleanUrl),hostname,port);
        });

        //循环删除job
        serverInfo.getJobList().forEach(map -> {
            String transName=(String)map.get("jobname");
            String id=(String)map.get("id");

            String transCleanUrl = String.format(HTTPApi.RemoveJobWithId.getUrl(), transName,id);
            doExecutor(getRequestGen(transCleanUrl),hostname,port);
        });
    }


    /**
     * 解析status xml文件
     * */
    private ServerInfo xmlBodyToServerInfo(String xmlBody) {
        ServerInfo serverInfo = new ServerInfo();
        serverInfo.setHost(this.host);
        try {
            Document document = DocumentHelper.parseText(xmlBody);
            serverInfo.setStatusDesc(getStringTextByXpath(document.getRootElement(), "/serverstatus/statusdesc"));

            List<Element> jobElementList=document.getRootElement().element("jobstatuslist").elements("jobstatus");

            List<Map> jobList = jobElementList.stream().map(ele ->
                    ImmutableMap.of(
                            "name", getStringTextByXpath(ele, "jobname"),
                            "id", getStringTextByXpath(ele, "id"),
                            "statusDesc", getStringTextByXpath(ele, "status_desc")
                    )).collect(Collectors.toList());
            serverInfo.setJobList(jobList);

            List<Element> transElementsList = document.getRootElement().element("transstatuslist").elements("transstatus");

            List<Map> transList = transElementsList.stream().map(ele ->
                    ImmutableMap.of(
                            "name", getStringTextByXpath(ele, "transname"),
                            "id", getStringTextByXpath(ele, "id"),
                            "statusDesc", getStringTextByXpath(ele, "status_desc")
                    )).collect(Collectors.toList());
            serverInfo.setTransList(transList);

        } catch (DocumentException e) {
            log.error("server info parse error", e);
        }
        return serverInfo;
    }


    private List<Map> getSlaveServerInfoList(String slaveInfoBody) {
        try {
            Document document = DocumentHelper.parseText(slaveInfoBody);
            List<Element> slaveElementList=document.getRootElement().elements("SlaveServerDetection");

            return  slaveElementList .stream()
                    .map(ele -> {
                        Map<String, String> map = new HashMap<>();
                        map.put("name", getStringTextByXpath(ele, "slaveserver/name"));
                        map.put("hostname", getStringTextByXpath(ele, "slaveserver/hostname"));
                        map.put("username", getStringTextByXpath(ele, "slaveserver/username"));
                        map.put("password", decode(getStringTextByXpath(ele, "slaveserver/password"), true));
                        map.put("port", getStringTextByXpath(ele, "slaveserver/port"));
                        map.put("master", getStringTextByXpath(ele, "slaveserver/master"));
                        map.put("active", getStringTextByXpath(ele, "active"));
                        return map;
                    })
                    .collect(Collectors.toList());

        } catch (DocumentException e) {
            log.error("slave server info parse error", e);
        }
        return null;
    }

}
