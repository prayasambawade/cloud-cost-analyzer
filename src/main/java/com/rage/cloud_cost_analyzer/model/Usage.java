package com.rage.cloud_cost_analyzer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "usage")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Usage {

    @Id
    private String id;

    private String serviceName;
    private Double usageAmount;
    private Double costPerUnit;
    private Double totalCost;
    private String date;

}
