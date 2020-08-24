package com.jijojames.app.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jijojames.app.Config.ApplicationConfig;
import com.jijojames.app.Model.ContactForm;
import com.jijojames.app.Model.Recaptcha;
import com.jijojames.app.Model.Website;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Component
public class RecaptchaController {
    @Autowired
    private ApplicationConfig applicationConfig;

    public Recaptcha verifyCaptcha(ContactForm contactForm, Website website) throws URISyntaxException, IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpUriRequest uriRequest = RequestBuilder.post()
                .setUri(new URI("https://www.google.com/recaptcha/api/siteverify"))
                .addParameter("secret", System.getenv(applicationConfig.getRecaptchaSiteKeyEnv()))
                .addParameter("response", contactForm.getCaptcha())
                .build();
        CloseableHttpResponse response = httpClient.execute(uriRequest);

        return new ObjectMapper().readValue(EntityUtils.toString(response.getEntity()), Recaptcha.class);
    }
}
