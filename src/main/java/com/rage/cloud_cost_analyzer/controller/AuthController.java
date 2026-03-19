package com.rage.cloud_cost_analyzer.controller;

import com.rage.cloud_cost_analyzer.model.User;
import com.rage.cloud_cost_analyzer.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthService service;


    @PostMapping("/register")
    public User register(@RequestBody User user){
        return service.register(user);
    }

    @PostMapping("/login")
    public User login(@RequestBody User user){
        return service.login(user.getEmail(), user.getPassword());
    }
}
