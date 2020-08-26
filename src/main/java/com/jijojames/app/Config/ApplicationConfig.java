package com.jijojames.app.Config;

import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

@Component
@EnableAutoConfiguration
@ConfigurationProperties
public class ApplicationConfig {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationConfig.class);

    @Autowired
    private Environment env;

    @Value("${spring.application.firebase.auth-credentials-storage}")
    private String firebaseAuthStorageLocation;

    @Value("${spring.application.firebase.auth-credentials-file:#{null}}")
    private String firebaseAuthStorageFile;

    @Value("${spring.application.firebase.auth-credentials-env:#{null}}")
    private String firebaseAuthStorageEnv;

    @Value("${spring.application.gmail.auth-credentials-file:#{null}}")
    private String gmailAuthStorageFile;

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

    public String getFirebaseAuthStorageLocation() {
        return firebaseAuthStorageLocation;
    }

    public String getFirebaseAuthStorageFile() {
        return firebaseAuthStorageFile;
    }

    public String getFirebaseAuthStorageEnv() {
        return firebaseAuthStorageEnv;
    }

    public String getGmailAuthStorageFile() {
        return gmailAuthStorageFile;
    }

    public String getGmailAuthStorageEnv() {
        return gmailAuthStorageEnv;
    }

    public String getRecaptchaSiteKeyEnv() {
        return recaptchaSiteKeyEnv;
    }

    public String getGmailTokenPathEnv() { return gmailTokenPathEnv; }

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
                "spring.application.firebase.auth-credentials-storage : " + getFirebaseAuthStorageLocation() + "\n" +
                "spring.application.firebase.auth-credentials-file : " + getFirebaseAuthStorageFile() + "\n" +
                "spring.application.firebase.auth-credentials-env : " + getFirebaseAuthStorageEnv() + "\n" +
                "spring.application.gmail.auth-credentials-file : " + getGmailAuthStorageFile() + "\n" +
                "spring.application.gmail.auth-credentials-env : " + getGmailAuthStorageEnv() + "\n" +
                "spring.application.gmail.token-path-env : " + getGmailTokenPathEnv() + "\n" +
                "spring.application.gmail.application-name: " + getGmailApplicationName() + "\n" +
                "spring.application.email.from-address-env : " + getEmailFromAddressEnv() + "\n" +
                "spring.application.gmail.stored-credential-env : " + getGmailStoredCredentialEnv() + "\n" +
                "spring.application.email.subject : " + getEmailSubject() + "\n" +
                "spring.application.recaptcha.site-key : " + getRecaptchaSiteKeyEnv() + "\n"
        );
    }

    public Boolean isConfigInEnv() {
        return firebaseAuthStorageLocation.equals("env");
    }

    @Bean
    public JsonFactory jsonFactory(){
        return JacksonFactory.getDefaultInstance();
    }

    @Bean
    public void environmentVariableClientSecrets() throws Exception {
        restoreEnvironmentVariableToFile(getGmailStoredCredentialEnv(), env.getProperty(getGmailTokenPathEnv()));
    }

    private void restoreEnvironmentVariableToFile(String environmentVariableName, String pathName) throws IOException {
        Files.createDirectories(Paths.get(pathName));
        Files.write(Paths.get(pathName +
                        File.separator + "StoredCredential"),
                Base64.getDecoder().decode(env.getProperty(environmentVariableName)));
    }
}
