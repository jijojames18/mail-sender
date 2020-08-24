package com.jijojames.app.Exception;

public class EmptyWebsiteException extends Exception {
    public EmptyWebsiteException() {
        super("Empty site value in request");
    }
}