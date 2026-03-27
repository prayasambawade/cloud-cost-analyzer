package com.rage.cloud_cost_analyzer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CostResponse {
    private String category;
    private double total;
    private String id;
    private String serviceName;
    private double amount;
    private String userId;
}
