package com.jijojames.app.Controller;

import com.google.api.client.util.Base64;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import com.jijojames.app.Config.ApplicationConfig;
import com.jijojames.app.Model.ContactForm;
import com.jijojames.app.Model.Website;
import com.jijojames.app.Service.GoogleMail;
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
import java.security.GeneralSecurityException;
import java.util.Map;
import java.util.Properties;

@Controller
public class GmailController {
    private static final Logger logger = LoggerFactory.getLogger(GmailController.class);

    @Autowired
    private GoogleMail googleMail;

    @Autowired
    private ApplicationConfig applicationConfig;

    public Message sendEmail(ContactForm contactForm, Website website) throws MessagingException, IOException, GeneralSecurityException {
        String toAddress = website.getEmail();
        String templateString = website.getMailTemplate();
        Map formData = contactForm.getFormData();
        StringSubstitutor substitutor = new StringSubstitutor(contactForm.getFormData());
        String messageString = substitutor.replace(templateString);
        String subject = formData.containsKey("subject") ? (String) formData.get("subject") : applicationConfig.getEmailSubject();
        String fromAddress = formData.containsKey("email") ? (String) formData.get("email") : System.getenv(applicationConfig.getEmailFromAddressEnv());

        MimeMessage emailContent = createEmail(toAddress, fromAddress, subject, messageString);
        Message message = createMessageWithEmail(emailContent);
        Gmail service = googleMail.getGmailService();
        message = service.users().messages().send("me", message).execute();

        logger.debug("Message id: " + message.getId());
        logger.debug(message.toPrettyString());
        return message;
    }

    public MimeMessage createEmail(String to, String from,  String subject, String bodyText) throws MessagingException {
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
