package com.jijojames.app;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class RestEndPointController {

    @RequestMapping("/")
    public String index() {
        return "Hello world!!!";
    }
}