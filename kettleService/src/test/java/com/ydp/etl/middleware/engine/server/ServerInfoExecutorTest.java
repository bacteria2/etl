package com.ydp.etl.middleware.engine.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServerInfoExecutorTest {

    private ServerInfoExecutor executor=new ServerInfoExecutor("192.168.40.235","8080","cluster","cluster");
    @Test
    void getServerInfo() {
        ServerInfo info= executor.getServerInfo();
        System.out.println(JSON.toJSONString(info, SerializerFeature.PrettyFormat));
    }
}