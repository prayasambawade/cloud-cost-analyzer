package com.rage.cloud_cost_analyzer.repository;

import com.rage.cloud_cost_analyzer.model.Cost;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CostRepository extends MongoRepository<Cost,String> {
}
