package com.rage.cloud_cost_analyzer.controller;


import com.rage.cloud_cost_analyzer.dto.RecommendationDTO;
import com.rage.cloud_cost_analyzer.model.Cost;
import com.rage.cloud_cost_analyzer.repository.CostRepository;
import com.rage.cloud_cost_analyzer.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/recommend")
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;

    @Autowired
    private CostRepository costRepository;




    @GetMapping("/{userId}")
    public ResponseEntity<?> getRecommendation(@PathVariable String userId){

        List<Cost> costs = costRepository.findByUserId(userId);

        List<RecommendationDTO> recommendation =
                recommendationService.generateRecommendation(costs);

        return ResponseEntity.ok(recommendation);
    }
}