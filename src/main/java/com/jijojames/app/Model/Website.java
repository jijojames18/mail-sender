package com.jijojames.app.Model;

public class Website {
    private String id;

    private String mailTemplate;

    private String email;

    public Website(String id, String mailTemplate, String email) {
        this.id = id;
        this.mailTemplate = mailTemplate;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
