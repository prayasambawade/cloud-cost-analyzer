package com.rage.cloud_cost_analyzer.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.Map;

@Service
public class AIService {

    public String getRecommendation(String prompt){

        String url = "http://localhost:11434/api/generate";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = Map.of(
                "model", "tinyllama",
                "prompt", prompt,
                "stream", false
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

        return response.getBody().get("response").toString();
    }
}