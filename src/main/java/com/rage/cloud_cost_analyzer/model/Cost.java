package com.rage.cloud_cost_analyzer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "costs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cost {

    @Id
    private String id;

    private String serviceName;
    private String provider;
    private double amount;
    private String date;

    private String userId;

}
