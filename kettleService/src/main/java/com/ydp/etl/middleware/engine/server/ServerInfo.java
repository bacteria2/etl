package com.ydp.etl.middleware.engine.server;

public class ServerInfo {

    //master地址
    private String host;
    //从机地址
    private String[] slaves;
    //是否在运行任务
    private boolean isRunning;
    //job列表
    private String[] jobList;
    //转换列表
    private String[] transList;


    public ServerInfo() {

    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String[] getSlaves() {
        return slaves;
    }

    public void setSlaves(String[] slaves) {
        this.slaves = slaves;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public String[] getJobList() {
        return jobList;
    }

    public void setJobList(String[] jobList) {
        this.jobList = jobList;
    }

    public String[] getTransList() {
        return transList;
    }

    public void setTransList(String[] transList) {
        this.transList = transList;
    }
}
