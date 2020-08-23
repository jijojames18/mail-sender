package com.jijojames.app.Services;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.jijojames.app.Config.ApplicationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class Firebase {

    @Autowired
    private ApplicationConfig applicationConfig;

    private Firestore firestore;

    public Firestore getFireStore() throws IOException {
        if (firestore == null) {
            FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder()
                    .setCredentials(GoogleCredentials.fromStream(getServiceAccountStream()))
                    .build();
            firestore = firestoreOptions.getService();
        }
        return firestore;
    }

    private InputStream getServiceAccountStream() throws IOException {
        String serviceAccount;
        if (applicationConfig.isConfigInEnv()) {
            serviceAccount = System.getenv(applicationConfig.getFirebaseAuthStorageEnv());
        } else {
            serviceAccount = new String(Files.readAllBytes(Paths.get(applicationConfig.getFirebaseAuthStorageFile())));
        }
        return (InputStream) new ByteArrayInputStream(serviceAccount.getBytes(StandardCharsets.UTF_8));
    }
}
