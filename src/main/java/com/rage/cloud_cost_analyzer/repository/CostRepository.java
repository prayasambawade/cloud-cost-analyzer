package com.rage.cloud_cost_analyzer.repository;

import com.rage.cloud_cost_analyzer.model.Cost;
import com.rage.cloud_cost_analyzer.dto.ServiceCostDTO;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CostRepository extends MongoRepository<Cost, String> {


    List<Cost> findByUserId(String userId);


    @Aggregation(pipeline = {
            "{ $match: { userId: ?0 } }",
            "{ $group: { _id: '$service', total: { $sum: '$amount' } } }"
    })
    List<ServiceCostDTO> getCostByService(String userId);
}