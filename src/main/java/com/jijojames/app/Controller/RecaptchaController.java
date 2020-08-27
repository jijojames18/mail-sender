package com.jijojames.app.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jijojames.app.Config.ApplicationConfig;
import com.jijojames.app.Model.Recaptcha;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Controller
public class RecaptchaController {

    private static final String VERIFY_ENDPOINT = "https://www.google.com/recaptcha/api/siteverify";
    private static final String SECRET_KEY = "secret";
    private static final String RESPONSE_KEY = "response";

    @Autowired
    private ApplicationConfig applicationConfig;

    public Recaptcha verifyCaptcha(String captcha) throws URISyntaxException, IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpUriRequest uriRequest = RequestBuilder.post()
                .setUri(new URI(VERIFY_ENDPOINT))
                .addParameter(SECRET_KEY, applicationConfig.getRecaptchaSiteKey())
                .addParameter(RESPONSE_KEY, captcha)
                .build();
        CloseableHttpResponse response = httpClient.execute(uriRequest);
        return new ObjectMapper().readValue(EntityUtils.toString(response.getEntity()), Recaptcha.class);
    }
}
