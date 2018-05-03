package com.ydp.etl.middleware.services;


import com.ydp.etl.middleware.engine.repository.RepositoryExecutor;
import com.ydp.etl.middleware.engine.trans.TransformationExecutor;
import org.dom4j.DocumentException;
import org.pentaho.di.trans.steps.transexecutor.TransExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TransService {
    private Logger log= LoggerFactory.getLogger(TransService.class);
    @Autowired
    RepositoryExecutor repositoryExecutor;

    @Autowired
    TransformationExecutor transformationExecutor;

    public String runTrans(String fileName){

        return null;
    }

    public void writeTrans(String xmlBody,String fileName) throws IOException {
        log.info("write trans file:{}",fileName);
        repositoryExecutor.writeFile(xmlBody,fileName+".ktr");
    }

    public void removeTrans(String filename){
        repositoryExecutor.removeFile(filename+".ktr");
        log.info("remove file:{}",filename);
    }

    public void cleanup(String filename) throws DocumentException {
        transformationExecutor.cleanup(filename);
    }


}
