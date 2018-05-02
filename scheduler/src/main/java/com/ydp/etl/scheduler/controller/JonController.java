package com.ydp.etl.scheduler.controller;

import com.ydp.etl.common.CommonResponse;
import com.ydp.etl.common.utils.ResponseHelper;
import com.ydp.etl.scheduler.model.ScheduleJob;
import com.ydp.etl.scheduler.service.JobService;
import com.ydp.etl.scheduler.utils.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author IonCannon
 * @date 2018/4/29
 * @decription : content
 */
@RestController
@RequestMapping("/job")
public class JonController {
    @Autowired
    private JobService jobService;

    @GetMapping
    public CommonResponse getAllJob() {
        return ResponseHelper.generateResponse(jobService.getAllJob());
    }

    @GetMapping("/{id}")
    public CommonResponse getJob(@PathVariable("id") Long jobId) throws ServiceException {
        return ResponseHelper.generateResponse(jobService.select(jobId));
    }

    @PutMapping("/update/{id}")
    public CommonResponse updateJob(@PathVariable("id") Long jobId, @RequestBody ScheduleJob newScheduleJob) throws ServiceException {
        return ResponseHelper.generateResponse(jobService.update(jobId, newScheduleJob));
    }

    @DeleteMapping("/delete/{id}")
    public CommonResponse deleteJob(@PathVariable("id") Long jobId) throws ServiceException {
        return ResponseHelper.generateResponse(jobService.delete(jobId));
    }

    @PostMapping("/save")
    public CommonResponse saveJob(@RequestBody ScheduleJob newScheduleJob) throws ServiceException {
        return ResponseHelper.generateResponse(jobService.add(newScheduleJob));
    }


    @GetMapping("/run/{id}")
    public CommonResponse runJob(@PathVariable("id") Long jobId) throws ServiceException {
        return ResponseHelper.generateResponse(jobService.run(jobId));
    }


    @GetMapping("/pause/{id}")
    public CommonResponse pauseJob(@PathVariable("id") Long jobId) throws ServiceException {
        return ResponseHelper.generateResponse(jobService.pause(jobId));
    }

    @GetMapping("/resume/{id}")
    public CommonResponse resumeJob(@PathVariable("id") Long jobId) throws ServiceException {
        return ResponseHelper.generateResponse(jobService.resume(jobId));
    }
}
