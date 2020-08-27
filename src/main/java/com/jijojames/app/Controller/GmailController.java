package com.jijojames.app.Controller;

import com.google.api.client.util.Base64;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import com.jijojames.app.Config.ApplicationConfig;
import com.jijojames.app.Model.ContactForm;
import com.jijojames.app.Model.Website;
import org.apache.commons.text.StringSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

@Controller
public class GmailController {
    private static final Logger logger = LoggerFactory.getLogger(GmailController.class);
    private static final String SUBJECT_KEY = "subject";
    private static final String EMAIL_KEY = "email";

    @Autowired
    private Gmail gmail;

    @Autowired
    private ApplicationConfig applicationConfig;

    public Message sendEmail(ContactForm contactForm, Website website) throws MessagingException, IOException {
        String toAddress = website.getEmail();
        String templateString = website.getMailTemplate();
        Map formData = contactForm.getFormData();
        StringSubstitutor substitutor = new StringSubstitutor(contactForm.getFormData());
        String messageString = substitutor.replace(templateString);
        String subject = formData.containsKey(SUBJECT_KEY) ? (String) formData.get(SUBJECT_KEY) : applicationConfig.getEmailSubject();
        String fromAddress = formData.containsKey(EMAIL_KEY) ? (String) formData.get(EMAIL_KEY) : applicationConfig.getEmailFromAddress();

        logger.debug("Sending message {} from {} with subject {}", messageString, fromAddress, subject);

        MimeMessage emailContent = createEmail(toAddress, fromAddress, subject, messageString);
        Message message = createMessageWithEmail(emailContent);
        message = gmail.users().messages().send("me", message).execute();

        logger.debug("Message id: " + message.getId());
        logger.debug(message.toPrettyString());
        return message;
    }

    public MimeMessage createEmail(String to, String from, String subject, String bodyText) throws MessagingException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        MimeMessage email = new MimeMessage(session);
        email.setFrom(new InternetAddress(from));
        email.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
        email.setSubject(subject);
        email.setText(bodyText);
        return email;
    }

    public Message createMessageWithEmail(MimeMessage emailContent) throws MessagingException, IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        emailContent.writeTo(buffer);
        byte[] bytes = buffer.toByteArray();
        String encodedEmail = Base64.encodeBase64URLSafeString(bytes);
        Message message = new Message();
        message.setRaw(encodedEmail);
        return message;
    }
}
