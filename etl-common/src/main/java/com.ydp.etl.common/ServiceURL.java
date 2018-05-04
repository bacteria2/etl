package com.ydp.etl.common;

public enum ServiceURL {

    Etl("http://kettle-service/%s"),
    Scheduler("http://scheduler-service/%s"),
    Gateway("http://gateway-service/%s");

    ServiceURL(String url) {
        this.url = url;
    }

    private String url;

    public String getUrl(String uri) {
        return String.format(url,uri);
    }


}
