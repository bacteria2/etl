package com.ydp.etl.middleware.controll;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableMap;
import com.ydp.etl.common.CommonResponse;
import com.ydp.etl.common.utils.ResponseHelper;
import com.ydp.etl.middleware.engine.job.JobInfo;
import com.ydp.etl.middleware.services.JobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/job")
public class JobsController {
    private Logger logger = LoggerFactory.getLogger(JobsController.class);

    @Autowired
    private  JobService jobService;

    @PostMapping(value = "/submit")
    public CommonResponse jobSubmit(@RequestBody String body) {
        JSONObject jsonObject = JSON.parseObject(body);
        try {
            jobService.writeJob(jsonObject.getString("text"), jsonObject.getString("name"));
        } catch (IOException e) {
            ResponseHelper.generateServerErrorResponse("写入失败");
        }
        return ResponseHelper.generateResponse("submit");
    }

    @DeleteMapping("/delete")
    public CommonResponse jobDelete(@RequestParam String name) {
        jobService.removeJob(name);
        return ResponseHelper.generateResponse("delete ok");
    }

    @DeleteMapping("/clean")
    public CommonResponse jobClean(@RequestParam String name) {
        jobService.cleanJob(name);
        return ResponseHelper.generateResponse("delete ok");
    }

    @GetMapping(value = "/getStatus")
    public JobInfo getStatusById(@RequestParam String  name,@RequestParam String id) {
        return jobService.getJobStatus(id,name);
    }

    @PostMapping("/run")
    public CommonResponse jobExecution(@RequestParam String name) {
        try {
            JobInfo jobInfo = jobService.runJob(name);
            return new CommonResponse(200, "run job success",jobInfo );
        } catch (Exception e) {
            return ResponseHelper.generateServerErrorResponse(e.getMessage());
        }
    }


}
