package com.jijojames.app;

import com.jijojames.app.Controller.FirestoreController;
import com.jijojames.app.Controller.GmailController;
import com.jijojames.app.Controller.RecaptchaController;
import com.jijojames.app.Exception.CaptchaException;
import com.jijojames.app.Exception.DocumentNotFoundException;
import com.jijojames.app.Exception.EmptyWebsiteException;
import com.jijojames.app.Model.ContactForm;
import com.jijojames.app.Model.Recaptcha;
import com.jijojames.app.Model.Website;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

@RestController
public class RestEndPointController {
    private static final Logger logger = LoggerFactory.getLogger(RestEndPointController.class);

    @Autowired
    private FirestoreController firestoreController;

    @Autowired
    private RecaptchaController recaptchaController;

    @Autowired
    private GmailController gmailController;

    @PostMapping("/")
    @CrossOrigin
    @ResponseBody
    public ResponseEntity<Object> index(@RequestBody ContactForm contactForm)
            throws InterruptedException, ExecutionException, EmptyWebsiteException, DocumentNotFoundException, IOException, URISyntaxException, CaptchaException, MessagingException {
        logger.debug("[START] Received new request");
        String site = contactForm.getSite();
        if (site == null) {
            logger.error("Empty website url obtained in request");
            throw new EmptyWebsiteException();
        }
        Recaptcha recaptcha = recaptchaController.verifyCaptcha(contactForm.getCaptcha());
        if (!recaptcha.isSuccess()) {
            logger.error("Captcha verification for request from website {} failed", site);
            throw new CaptchaException();
        }
        Website website = firestoreController.getWebsite(site);
        gmailController.sendEmail(contactForm, website);
        logger.debug("[END] Request processed");
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}