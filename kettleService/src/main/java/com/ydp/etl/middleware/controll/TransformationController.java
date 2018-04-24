package com.ydp.etl.middleware.controll;

import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class TransformationController {
    private Logger logger= LoggerFactory.getLogger(TransformationController.class);

    @PostMapping(value="/kettle/trans/submit")
    public Map transSubmit(@RequestParam String id){
        logger.info("trans submit 123123 %s",id);
        return ImmutableMap.of("key","response");
    }

    @GetMapping(value ="/kettle/trans/getStatus")
    public String getStatusById(@RequestParam String id){
        return String.format("{\"msg\":trans123123%s}",id);
    }
}
