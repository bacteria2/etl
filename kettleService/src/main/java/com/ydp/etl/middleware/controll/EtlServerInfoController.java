package com.ydp.etl.middleware.controll;

import com.ydp.etl.common.CommonResponse;
import com.ydp.etl.common.utils.ResponseHelper;
import com.ydp.etl.middleware.engine.server.ServerInfo;
import com.ydp.etl.middleware.services.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/etl/server")
public class EtlServerInfoController {

    @Autowired
    ServerService serverService;

    @GetMapping("/status")
    public ServerInfo status(){
        return  serverService.getServerStatus();
    }

    @GetMapping("/slaves")
    public List slaves(){
        return serverService.getSlaveServer();
    }

    @DeleteMapping("/cleanAll")
    public CommonResponse cleanAll(){
        serverService.cleanAllTask();
        return ResponseHelper.generateResponse(true);
    }
}
