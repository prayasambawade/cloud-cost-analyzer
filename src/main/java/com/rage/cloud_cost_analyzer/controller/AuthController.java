package com.rage.cloud_cost_analyzer.controller;

import com.rage.cloud_cost_analyzer.model.User;
import com.rage.cloud_cost_analyzer.service.AuthService;
import com.rage.cloud_cost_analyzer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthService service;

    @Autowired
    private UserService userService;




    @PostMapping("/register")
    public User register(@RequestBody User user){
        return service.register(user);
    }

    @PostMapping("/login")
    public User login(@RequestBody User user){
        return service.login(user.getEmail(), user.getPassword());
    }
    @PutMapping("/set-budget/{userId}")
    public String setBudget(@PathVariable String userId,
                            @RequestParam double budget) {

        userService.setBudget(userId, budget);
        return "Budget updated successfully";
    }
}
