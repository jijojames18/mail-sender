package com.jijojames.app.Exception;

public class DocumentNotFoundException extends Exception {
    public DocumentNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}