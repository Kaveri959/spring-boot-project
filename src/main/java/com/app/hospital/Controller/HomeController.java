package com.app.hospital.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping
    public String home() {
        return "Welcome to Hospital Management API";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "Login successful";
    }
}
