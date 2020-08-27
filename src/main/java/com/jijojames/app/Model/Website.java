package com.jijojames.app.Model;

public class Website {
    private final String id;

    private final String mailTemplate;

    private final String email;

    public Website(String id, String mailTemplate, String email) {
        this.id = id;
        this.mailTemplate = mailTemplate;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getMailTemplate() {
        return mailTemplate;
    }
}