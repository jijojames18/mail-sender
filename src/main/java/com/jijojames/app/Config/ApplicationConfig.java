package com.jijojames.app.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@EnableAutoConfiguration
@ConfigurationProperties
public class ApplicationConfig {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationConfig.class);

    @Value("${spring.application.firebase.auth-credentials-storage}")
    private String firebaseAuthStorageLocation;

    @Value("${spring.application.firebase.auth-credentials-file:#{null}}")
    private String firebaseAuthStorageFile;

    @Value("${spring.application.firebase.auth-credentials-env:#{null}}")
    private String firebaseAuthStorageEnv;

    public String getFirebaseAuthStorageLocation() {
        return firebaseAuthStorageLocation;
    }

    public String getFirebaseAuthStorageFile() {
        return firebaseAuthStorageFile;
    }

    public String getFirebaseAuthStorageEnv() {
        return firebaseAuthStorageEnv;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loaded() {
        logger.info("Application loaded with properties :" + "\n" +
                "spring.application.firebase.auth-credentials-storage : " + getFirebaseAuthStorageLocation() + "\n" +
                "spring.application.firebase.auth-credentials-file : " + getFirebaseAuthStorageFile() + "\n" +
                "spring.application.firebase.auth-credentials-env : " + getFirebaseAuthStorageEnv() + "\n"
        );
    }
}
