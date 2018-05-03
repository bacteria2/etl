package com.ydp.etl.middleware.engine.server;

import java.util.List;
import java.util.Map;

public class ServerInfo {

    //master地址
    private String host;
    //从机地址
    private List<Map> slaves;
    //是否在运行任务
    private String statusDesc;
    //job列表
    private List<Map> jobList;
    //转换列表
    private List<Map> transList;


    public ServerInfo() {

    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public List<Map> getSlaves() {
        return slaves;
    }

    public void setSlaves(List<Map> slaves) {
        this.slaves = slaves;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public List<Map> getJobList() {
        return jobList;
    }

    public void setJobList(List<Map> jobList) {
        this.jobList = jobList;
    }

    public List<Map> getTransList() {
        return transList;
    }

    public void setTransList(List<Map> transList) {
        this.transList = transList;
    }
}
