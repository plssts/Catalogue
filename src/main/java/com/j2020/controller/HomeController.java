package com.j2020.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController implements ErrorController {
    @GetMapping("/")
    public String index(){
        return "Currently supports /revolut[/accountId] and /deutsche[?iban=iban] endpoints";
    }

    @GetMapping("/error")
    public String error(){
        return "error here";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
