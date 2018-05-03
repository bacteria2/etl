package com.ydp.etl.middleware.engine;

import org.dom4j.DocumentFactory;

abstract public class AbstractKettleTask {
    protected String xmlBody;
    protected String name;
    protected String id;



    public AbstractKettleTask() {
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




}
