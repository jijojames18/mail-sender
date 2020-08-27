package com.jijojames.app.Model;

import java.util.Map;

public class ContactForm {
    private String site;

    private String captcha;

    private Map formData;

    public String getCaptcha() {
        return captcha;
    }

    public String getSite() {
        return site;
    }

    public Map getFormData() {
        return formData;
    }
}
