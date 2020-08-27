package com.jijojames.app.Exception;

import java.util.HashMap;
import java.util.Map;

public class DocumentNotFoundException extends Exception implements BaseException {
    private static final Integer ERROR_CODE = 102;
    private static final String ERROR_MESSAGE = "Website url is not registered with the service";
    private static final Map<String, Object> ERROR_RESPONSE = new HashMap<String, Object>() {
        {
            put("error-code", ERROR_CODE);
            put("error-message", ERROR_MESSAGE);
        }
    };

    public DocumentNotFoundException() {
        super(ERROR_MESSAGE);
    }

    public Map getErrorResponse() {
        return ERROR_RESPONSE;
    }
}