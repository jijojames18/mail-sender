package com.jijojames.app.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Configuration
@EnableAutoConfiguration
@ConfigurationProperties
public class ApplicationConfig {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationConfig.class);

    @Autowired
    private Environment env;

    @Value("${spring.application.firebase.auth-credentials-env:#{null}}")
    private String firebaseAuthStorageEnv;

    @Value("${spring.application.gmail.auth-credentials-env:#{null}}")
    private String gmailAuthStorageEnv;

    @Value("${spring.application.recaptcha.site-key-env}")
    private String recaptchaSiteKeyEnv;

    @Value("${spring.application.gmail.application-name}")
    private String gmailApplicationName;

    @Value("${spring.application.gmail.token-path-env}")
    private String gmailTokenPathEnv;

    @Value("${spring.application.email.from-address-env}")
    private String emailFromAddressEnv;

    @Value("${spring.application.email.subject}")
    private String emailSubject;

    @Value("${spring.application.gmail.stored-credential-env}")
    private String gmailStoredCredentialEnv;

    public String getFirebaseAuthStorageEnv() {
        return firebaseAuthStorageEnv;
    }

    public String getGmailAuthStorageEnv() {
        return gmailAuthStorageEnv;
    }

    public String getRecaptchaSiteKeyEnv() {
        return recaptchaSiteKeyEnv;
    }

    public String getGmailTokenPathEnv() {
        return gmailTokenPathEnv;
    }

    public String getGmailApplicationName() {
        return gmailApplicationName;
    }

    public String getEmailFromAddressEnv() {
        return emailFromAddressEnv;
    }

    public String getGmailStoredCredentialEnv() {
        return gmailStoredCredentialEnv;
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loaded() {
        logger.info("Application loaded with properties :" + "\n" +
                "spring.application.firebase.auth-credentials-env : " + getFirebaseAuthStorageEnv() + "\n" +
                "spring.application.gmail.auth-credentials-env : " + getGmailAuthStorageEnv() + "\n" +
                "spring.application.gmail.token-path-env : " + getGmailTokenPathEnv() + "\n" +
                "spring.application.gmail.application-name: " + getGmailApplicationName() + "\n" +
                "spring.application.email.from-address-env : " + getEmailFromAddressEnv() + "\n" +
                "spring.application.gmail.stored-credential-env : " + getGmailStoredCredentialEnv() + "\n" +
                "spring.application.email.subject : " + getEmailSubject() + "\n" +
                "spring.application.recaptcha.site-key : " + getRecaptchaSiteKeyEnv() + "\n"
        );
    }

    public InputStream getServiceAccountStream() {
        return new ByteArrayInputStream(env.getProperty(getFirebaseAuthStorageEnv()).getBytes(StandardCharsets.UTF_8));
    }

    public InputStream getCredentialsStream() {
        return new ByteArrayInputStream(env.getProperty(getGmailAuthStorageEnv()).getBytes(StandardCharsets.UTF_8));
    }

    public String getRecaptchaSiteKey() {
        return System.getenv(getRecaptchaSiteKeyEnv());
    }

    public String getGmailTokenPath() {
        return env.getProperty(getGmailTokenPathEnv());
    }

    public String getGmailStoredCredential() {
        return env.getProperty(getGmailStoredCredentialEnv());
    }

    public String getEmailFromAddress() {
        return System.getenv(getEmailFromAddressEnv());
    }
}
