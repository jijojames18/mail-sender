package com.jijojames.app;

import com.jijojames.app.Exception.BaseException;
import com.jijojames.app.Exception.CaptchaException;
import com.jijojames.app.Exception.DocumentNotFoundException;
import com.jijojames.app.Exception.EmptyWebsiteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RestErrorHandler {

    @ExceptionHandler({CaptchaException.class, DocumentNotFoundException.class, EmptyWebsiteException.class})
    @ResponseBody
    public ResponseEntity<Object> processHandledException(BaseException e) {
        return new ResponseEntity<>(e.getErrorResponse(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<Object> processException(Exception e) {
        Map error = new HashMap();
        error.put("error-message", "An internal error occurred");
        error.put("error-code", 500);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}