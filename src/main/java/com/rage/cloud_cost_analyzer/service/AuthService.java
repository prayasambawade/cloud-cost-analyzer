package com.rage.cloud_cost_analyzer.service;

import com.rage.cloud_cost_analyzer.model.Usage;
import com.rage.cloud_cost_analyzer.model.User;
import com.rage.cloud_cost_analyzer.repository.UsageRepository;
import com.rage.cloud_cost_analyzer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class AuthService {

    @Autowired
    private UserRepository repository;

    public User register(User user){
        Optional<User> existing  = repository.findByEmail(user.getEmail());

        if (existing.isPresent()){
            throw new RuntimeException("User Already Present");
        }
        return repository.save(user);
    }
    public User login(String email, String password){
        User user =repository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("User not Found"));

        if (!user.getPassword().equals(password)){
            throw new RuntimeException("Invalid Password");
        }
        return user;
    }
}
