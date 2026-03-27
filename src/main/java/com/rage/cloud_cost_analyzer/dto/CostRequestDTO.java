package com.rage.cloud_cost_analyzer.dto;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.NonNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CostRequestDTO {

    @NonNull
    private String serviceName;
    @Positive
    private double  amount;

    private String userId;
}
