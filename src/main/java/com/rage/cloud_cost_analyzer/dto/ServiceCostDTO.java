package com.rage.cloud_cost_analyzer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceCostDTO {

    @Id
    private String service;
    private double total;
}
