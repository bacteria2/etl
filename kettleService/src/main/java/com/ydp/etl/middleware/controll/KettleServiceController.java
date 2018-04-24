package com.ydp.etl.middleware.controll;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KettleServiceController {
    @PostMapping
    public String submitTransFile(){
        return  null;
    }
}
