package com.jijojames.app.Exception;

import java.util.HashMap;
import java.util.Map;

public class CaptchaException extends Exception implements BaseException {
    private static final Integer ERROR_CODE = 100;
    private static final String ERROR_MESSAGE = "Captcha Verification failed";
    private static final Map<String, Object> ERROR_RESPONSE = new HashMap<String, Object>() {
        {
            put("error-code", ERROR_CODE);
            put("error-message", ERROR_MESSAGE);
        }
    };

    public CaptchaException() {
        super(ERROR_MESSAGE);
    }

    public Map getErrorResponse() {
        return ERROR_RESPONSE;
    }
}