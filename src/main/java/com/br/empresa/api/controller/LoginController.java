package com.br.empresa.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @GetMapping("/api/login")
    public String login() {
        return login();
    }

    @PostMapping("/api/login")
    public String loginSubmit() {
        return "redirect:home";
    }
}
