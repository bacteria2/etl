package com.ydp.etl.common;

/**
 * @author IonCannon
 * @date 2018/4/29
 * @decription : content
 */
public enum Message {
    DEFAULT_SUCCESS_MESSAGE("success"),
    DEFAULT_FAIL_MESSAGE("fail"),
    NO_RESULT_MESSAGE("no result"),

    SUCCESS("SUCCESS"),
    ERROR("ERROR"),
    DB_ERROR_MESSAGE("Database Error"),
    SERVER_ERROR_MESSAGE("Server Error") ;


    Message(String msg){

        this.message=msg;
    }

    private String message;



    @Override
    public String toString() {
        return message;
    }
}
