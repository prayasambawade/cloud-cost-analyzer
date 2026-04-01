package com.rage.cloud_cost_analyzer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecommendationDTO {

    private String service;
    private double amount;
    private String percentage;
    private String priority;
    private String suggestion;
}