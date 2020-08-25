package com.jijojames.app.Controller;

import com.jijojames.app.Model.ContactForm;
import com.jijojames.app.Service.GoogleMail;
import org.springframework.beans.factory.annotation.Autowired;


public class GmailController {

    @Autowired
    private GoogleMail googleMail;

    public void sendEmail(ContactForm contactForm) {

    }
}
