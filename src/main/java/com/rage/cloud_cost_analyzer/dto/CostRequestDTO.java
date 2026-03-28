package com.rage.cloud_cost_analyzer.dto;

import com.mongodb.lang.NonNull;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;

@Valid

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CostRequestDTO {



    @NotBlank
    private String serviceName;

   @NonNull
    @Positive
    private double  amount;

    private String userId;
}
