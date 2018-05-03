package com.ydp.etl.middleware.engine;

public class ClusterInfo {
    private String host;
    private String port;
    private String username;
    private String password;

    private String name;
    private String basePort;
    private String socketsBufferSize;
    private String socketsFlushInterval;
    private boolean socketsCompressed;
    private boolean dynamic = true;
    private String slaveServersName;

    public ClusterInfo() {
    }

    public ClusterInfo(String host, String port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBasePort() {
        return basePort;
    }

    public void setBasePort(String basePort) {
        this.basePort = basePort;
    }

    public String getSocketsBufferSize() {
        return socketsBufferSize;
    }

    public void setSocketsBufferSize(String socketsBufferSize) {
        this.socketsBufferSize = socketsBufferSize;
    }

    public String getSocketsFlushInterval() {
        return socketsFlushInterval;
    }

    public void setSocketsFlushInterval(String socketsFlushInterval) {
        this.socketsFlushInterval = socketsFlushInterval;
    }

    public boolean isSocketsCompressed() {
        return socketsCompressed;
    }

    public void setSocketsCompressed(boolean socketsCompressed) {
        this.socketsCompressed = socketsCompressed;
    }

    public boolean isDynamic() {
        return dynamic;
    }

    public void setDynamic(boolean dynamic) {
        this.dynamic = dynamic;
    }

    public String getSlaveServersName() {
        return slaveServersName;
    }

    public void setSlaveServersName(String slaveServersName) {
        this.slaveServersName = slaveServersName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
