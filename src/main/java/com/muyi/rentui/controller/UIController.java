package com.muyi.rentui.controller;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UIController {

    @GetMapping("/")
    public String loadUI(){
        return "home";
    }

    @GetMapping("/secure")
    public String loadSecureUI(){
        return "secure";
    }
}
