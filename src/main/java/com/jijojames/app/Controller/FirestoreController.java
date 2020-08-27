package com.jijojames.app.Controller;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.jijojames.app.Exception.DocumentNotFoundException;
import com.jijojames.app.Model.Website;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.concurrent.ExecutionException;

@Controller
public class FirestoreController {
    private static final Logger logger = LoggerFactory.getLogger(FirestoreController.class);
    private static final String COLLECTION_NAME = "websites";
    private static final String MAIL_TEMPLATE = "mail-template";
    private static final String EMAIL = "email";

    @Autowired
    private Firestore firestore;

    public Website getWebsite(String url) throws ExecutionException, InterruptedException, DocumentNotFoundException {
        DocumentReference docRef = firestore.collection(COLLECTION_NAME).document(url);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (!document.exists()) {
            logger.error("Website {} not found in firestore", url);
            throw new DocumentNotFoundException();
        }

        String mailTemplate = (String) document.get(MAIL_TEMPLATE);
        String email = (String) document.get(EMAIL);
        return new Website(url, mailTemplate, email);
    }
}
