package com.muyi.rentui.controller;

import com.muyi.rentcloud.commons.model.Customer;
import com.muyi.rentui.config.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Controller
public class UIController {

    //@Autowired
    RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/")
    public String loadUI(){
        return "home";
    }

    @GetMapping("/secure")
    public String loadSecureUI(){
        return "secure";
    }

    @GetMapping("/customers")
    public String getCustomers(Model model){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", AccessToken.getAccessToken());
        System.out.println("Access token: "+AccessToken.getAccessToken());
        HttpEntity<Customer> httpEntity = new HttpEntity<>(httpHeaders);
        try {
            ResponseEntity<Customer[]> responseEntity = restTemplate.exchange("http://localhost:8181/services/customers",
                    HttpMethod.GET, httpEntity, Customer[].class);
            model.addAttribute("customers", responseEntity.getBody());
            //Arrays.asList(responseEntity.getBody()).forEach(customer -> System.out.println(customer.getFirstName()));
        }catch (HttpStatusCodeException ee){
            ResponseEntity responseEntity = ResponseEntity.status(ee.getRawStatusCode()).headers(ee.getResponseHeaders()).body(ee.getResponseBodyAsString());
            model.addAttribute("errors",responseEntity);
        }
        return "secure";
    }
}
