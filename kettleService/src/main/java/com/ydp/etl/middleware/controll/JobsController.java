package com.ydp.etl.middleware.controll;

import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class JobsController {
    private Logger logger= LoggerFactory.getLogger(JobsController.class);

    @PostMapping(value="/kettle/job/submit")
    public Map transSubmit(@RequestParam String body){
        logger.info("job submit 123123 %s",body);
        return ImmutableMap.of("key","response");
    }

    @GetMapping(value ="/kettle/job/getStatus")
    public String getStatusById(@RequestParam(required = false,defaultValue = "123") String id){
        return String.format("{\"msg\":job123123%s}",id);
    }
}
