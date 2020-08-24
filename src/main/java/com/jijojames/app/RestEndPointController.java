package com.jijojames.app;


import com.jijojames.app.Controller.FirestoreController;
import com.jijojames.app.Exception.DocumentNotFoundException;
import com.jijojames.app.Exception.EmptyWebsiteException;
import com.jijojames.app.Model.ContactForm;
import com.jijojames.app.Model.Website;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.ExecutionException;


@RestController
public class RestEndPointController {

    @Autowired
    private FirestoreController firestoreController;

    @PostMapping("/")
    public String index(@RequestBody ContactForm contactForm) throws InterruptedException, ExecutionException, EmptyWebsiteException, DocumentNotFoundException, IOException {
        Website website = firestoreController.getWebsite(contactForm.getSite());
        return website.getEmail();
    }
}