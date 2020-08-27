package com.jijojames.app.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.jijojames.app.Config.ApplicationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Firebase {
    private static final Logger logger = LoggerFactory.getLogger(Firebase.class);

    @Autowired
    private ApplicationConfig applicationConfig;

    @Bean
    public Firestore getFireStore() throws IOException {
        logger.debug("Loading firestore service as bean");
        FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder()
                .setCredentials(GoogleCredentials.fromStream(applicationConfig.getServiceAccountStream()))
                .build();
        return firestoreOptions.getService();
    }
}
