package com.j2020.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController implements ErrorController {
    @GetMapping("/")
    public String index(){
        return "";
    }

    @GetMapping("/error")
    public String error(){
        return "Unforeseen action has been performed. Refer to index at '/' for help.";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
