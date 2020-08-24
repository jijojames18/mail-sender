package com.jijojames.app.Exception;

public class CaptchaException extends Exception {
    public CaptchaException() {
        super("Captcha verification failed");
    }
}
