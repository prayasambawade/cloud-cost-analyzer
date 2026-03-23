package com.rage.cloud_cost_analyzer.service;

import com.rage.cloud_cost_analyzer.model.User;
import com.rage.cloud_cost_analyzer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void setBudget(String userId, double budget){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        user.setBudget(budget);
        userRepository.save(user);

    }
}
