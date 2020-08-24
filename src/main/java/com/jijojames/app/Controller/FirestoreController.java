package com.jijojames.app.Controller;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.jijojames.app.Exception.DocumentNotFoundException;
import com.jijojames.app.Exception.EmptyWebsiteException;
import com.jijojames.app.Model.Website;
import com.jijojames.app.Service.Firebase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Component
public class FirestoreController {

    @Autowired
    private Firebase firebase;

    public Website getWebsite(String url) throws IOException, ExecutionException, InterruptedException, DocumentNotFoundException, EmptyWebsiteException {
        if (url == null) {
            throw new EmptyWebsiteException();
        }

        Website website;
        Firestore firestore = firebase.getFireStore();
        DocumentReference docRef = firestore.collection("websites").document(url);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {
            String mailTemplate = (String) document.get("mail-template");
            String email = (String) document.get("email");
            website = new Website(url, mailTemplate, email);
        } else {
            throw new DocumentNotFoundException("Record " + url + " not found");
        }
        return website;
    }
}
