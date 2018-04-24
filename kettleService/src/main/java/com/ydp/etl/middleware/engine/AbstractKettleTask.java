package com.ydp.etl.middleware.engine;

abstract public class AbstractKettleTask {
    private String xmlBody;
    private String name;
    private String id;
    private RunningProperties runningProperties;

    public AbstractKettleTask() {
    }

    public RunningProperties getRunningProperties() {
        return runningProperties;
    }

    public void setRunningProperties(RunningProperties runningProperties) {
        this.runningProperties = runningProperties;
    }

    public String getXmlBody() {
        return xmlBody;
    }

    public void setXmlBody(String xmlBody) {
        this.xmlBody = xmlBody;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    abstract public  String getSubmitTask();
}
