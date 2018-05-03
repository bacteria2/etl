package com.ydp.etl.middleware.services;

import com.alibaba.fastjson.JSON;
import com.ydp.etl.middleware.engine.ClusterInfo;
import com.ydp.etl.middleware.engine.repository.RepositoryExecutor;
import com.ydp.etl.middleware.engine.server.ServerInfo;
import com.ydp.etl.middleware.engine.server.ServerInfoExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.jws.Oneway;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ServerService {
    private Logger log= LoggerFactory.getLogger(ServerService.class);

    @Autowired
    ServerInfoExecutor serverInfoExecutor;

    @Autowired
    RepositoryExecutor repositoryExecutor;



    public ServerInfo getServerStatus() {
        return serverInfoExecutor.getServerInfo();
    }

    public List getSlaveServer(){
        return serverInfoExecutor.slaveInfo();
    }

    @PostConstruct
    private void writeKcs(){
        try{
            repositoryExecutor.writeClusterInfoToRepo();
        } catch (IOException e) {
            log.error("write kcs file error",e);
        }
    }

    public void cleanAllTask(){
        serverInfoExecutor.cleanAllTransAndJob();
    }


}
