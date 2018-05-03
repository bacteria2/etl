package com.ydp.etl.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.ydp.etl.common.CommonResponse;
import com.ydp.etl.common.Message;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author IonCannon
 * @date 2018/4/29
 * @decription : content
 */
public class ResponseHelper {

    private static final Logger logger = LoggerFactory.getLogger(ResponseHelper.class);

    /**
     * return response with default success or error message by status
     *
     * @param resultStatus
     * @return
     */
    public static CommonResponse generateResponse(boolean resultStatus) {
        CommonResponse commonResponse = new CommonResponse();
        if (resultStatus) {
            commonResponse
                    .setCode(HttpStatus.SC_OK)
                    .setMessage(Message.DEFAULT_SUCCESS_MESSAGE);
        } else {
            commonResponse
                    .setCode(HttpStatus.SC_BAD_REQUEST)
                    .setMessage(Message.DEFAULT_FAIL_MESSAGE);
        }
        return commonResponse;
    }

    /**
     * return response with custom message by status
     *
     * @param message
     * @param resultStatus
     * @return
     */
    public static CommonResponse generateResponse(String message, boolean resultStatus) {
        CommonResponse commonResponse = new CommonResponse();
        if (resultStatus) {
            commonResponse
                    .setCode(HttpStatus.SC_OK)
                    .setMessage(message);
        } else {
            commonResponse
                    .setCode(HttpStatus.SC_BAD_REQUEST)
                    .setMessage(message);
        }
        return commonResponse;
    }



    /**
     * return response with data,if data is null,return no data message,or return data
     *
     * @param data
     * @return
     */
    public static CommonResponse generateResponse(Object data) {
        CommonResponse commonResponse = new CommonResponse();
        if (data != null) {
            commonResponse
                    .setCode(HttpStatus.SC_OK)
                    .setMessage(Message.DEFAULT_SUCCESS_MESSAGE)
                    .setData(data);
        } else {
            commonResponse
                    .setCode(HttpStatus.SC_OK)
                    .setMessage(Message.NO_RESULT_MESSAGE);

        }
        return commonResponse;
    }

    public static CommonResponse generateServerErrorResponse(String msg) {
        CommonResponse commonResponse = new CommonResponse();
        commonResponse
                .setCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .setMessage(msg);
        return commonResponse;
    }

    /**
     * Handler response information
     *
     * @param response
     * @param object
     * @return
     */
    public static HttpServletResponse handlerResponse(HttpServletResponse response, Object object) {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setStatus(200);
        try {
            response.getWriter().write(JSONObject.toJSONString(object));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return response;
    }

}
