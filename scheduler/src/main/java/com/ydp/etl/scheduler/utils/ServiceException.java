package com.ydp.etl.scheduler.utils;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author IonCannon
 * @date 2018/4/29
 * @decription : content
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ServiceException extends Exception {

    public ServiceException(String msg, Exception e) {
        super(msg + "\n" + e.getMessage());
    }

    public ServiceException(String msg) {
        super(msg);
    }
}
