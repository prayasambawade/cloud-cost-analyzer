package com.rage.cloud_cost_analyzer.service;

import com.rage.cloud_cost_analyzer.model.ChatMessage;
import com.rage.cloud_cost_analyzer.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.HashMap;
import java.util.Map;

@Service
public class AIService {

    @Autowired
    private ChatRepository chatRepository;

    private final String OLLAMA_URL = "http://localhost:11434/api/generate";

    // 🔥 MAIN METHOD (UPDATED)
    public String askAI(String prompt, String userId) {

        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> request = new HashMap<>();
        request.put("model", "tinyllama");
        request.put("prompt", prompt);
        request.put("stream", false);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(
                OLLAMA_URL,
                entity,
                Map.class
        );

        String aiResponse = response.getBody().get("response").toString();

        // 🔥 SAVE CHAT TO MONGODB
        ChatMessage chat = new ChatMessage(userId, prompt, aiResponse);
        chatRepository.save(chat);

        return aiResponse;
    }

    // 🔥 OPTIONAL (Cost Recommendation)
    public String getRecommendation(String costData, String userId) {

        String prompt = "Analyze this cloud cost data and suggest optimization: " + costData;

        return askAI(prompt, userId);   // reuse updated method
    }
}