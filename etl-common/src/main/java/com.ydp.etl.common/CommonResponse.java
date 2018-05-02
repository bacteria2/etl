package com.ydp.etl.common;

/**
 * @author IonCannon
 * @date 2018/4/29
 * @decription : content
 */
public class CommonResponse {
    private int code;

    private String message;

    private Object data;

    public int getCode() {
        return code;
    }

    public CommonResponse setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public CommonResponse setMessage(String message) {
        this.message = message;
        return this;
    }
    public CommonResponse setMessage(Object message) {
        this.message = message.toString();
        return this;
    }

    public Object getData() {
        return data;
    }

    public CommonResponse setData(Object data) {
        this.data = data;
        return this;
    }
}
