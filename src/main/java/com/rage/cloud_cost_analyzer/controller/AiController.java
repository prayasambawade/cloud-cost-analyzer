package com.rage.cloud_cost_analyzer.controller;

import com.rage.cloud_cost_analyzer.model.ChatMessage;
import com.rage.cloud_cost_analyzer.repository.ChatRepository;
import com.rage.cloud_cost_analyzer.service.AIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    @Autowired
    private AIService aiService;

    @Autowired
    private ChatRepository chatRepository;

    public AiController (AIService aiService){
        this.aiService = aiService;
    }

    @PostMapping("/ask/{userId}")
    public String askAI(@PathVariable String userId,
                        @RequestBody String prompt) {
        return aiService.askAI(prompt, userId);
    }



    @GetMapping("/history/{userId}")
    public List<ChatMessage> getHistory(@PathVariable String userId) {
        return chatRepository.findByUserId(userId);
    }

}
