package com.ydp.etl.middleware.controll;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableMap;
import com.ydp.etl.common.CommonResponse;
import com.ydp.etl.common.utils.ResponseHelper;
import com.ydp.etl.middleware.services.TransService;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/etl/trans")
public class TransformationController {
    private Logger logger= LoggerFactory.getLogger(TransformationController.class);

    @Autowired
    private TransService transService;


    @PostMapping(value="/submit")
    public CommonResponse transSubmit(@RequestBody String body){
        JSONObject jsonObject= JSON.parseObject(body);
        try {
            transService.writeTrans(jsonObject.getString("text"),jsonObject.getString("name"));
        } catch (IOException e) {
            ResponseHelper.generateServerErrorResponse("写入失败");
        }
        return ResponseHelper.generateResponse("submit");
    }

    @DeleteMapping("/delete")
    public CommonResponse transDelete(@RequestParam String name){
        transService.removeTrans(name);
        return ResponseHelper.generateResponse("delete ok");
    }

    @DeleteMapping("/clean")
    public CommonResponse transClean(@RequestParam String name){
        try {
            transService.cleanup(name);
            return ResponseHelper.generateResponse("delete ok");
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return ResponseHelper.generateResponse("delete false");
    }


}
