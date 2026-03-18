package com.rage.cloud_cost_analyzer.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "cost")
public class Usage {

    @Id
    private String id;

    private String serviceName;

    private Double usageAmount;
    private Double costPerUnit;
    private Double totalCost;
}