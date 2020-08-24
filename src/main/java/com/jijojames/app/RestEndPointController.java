package com.jijojames.app;


import com.jijojames.app.Controller.FirestoreController;
import com.jijojames.app.Controller.RecaptchaController;
import com.jijojames.app.Exception.CaptchaException;
import com.jijojames.app.Exception.DocumentNotFoundException;
import com.jijojames.app.Exception.EmptyWebsiteException;
import com.jijojames.app.Model.ContactForm;
import com.jijojames.app.Model.Recaptcha;
import com.jijojames.app.Model.Website;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;


@RestController
public class RestEndPointController {

    @Autowired
    private FirestoreController firestoreController;

    @Autowired
    private RecaptchaController recaptchaController;

    @PostMapping("/")
    @CrossOrigin
    public String index(@RequestBody ContactForm contactForm) throws InterruptedException, ExecutionException, EmptyWebsiteException, DocumentNotFoundException, IOException, URISyntaxException, CaptchaException {
        Website website = firestoreController.getWebsite(contactForm.getSite());
        Recaptcha recaptcha = recaptchaController.verifyCaptcha(contactForm, website);
        if (!recaptcha.isSuccess()) {
            throw new CaptchaException();
        }
        return website.getEmail();
    }
}