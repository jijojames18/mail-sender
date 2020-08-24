package com.jijojames.app.Model;

import java.util.Map;

public class ContactForm {
    private String site;

    private String captcha;

    private Map formData;

    public String getSite() {
        return site;
    }

    public String getCaptcha() {
        return captcha;
    }

    public Map getFormData() {
        return formData;
    }
}
