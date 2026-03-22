package com.rage.cloud_cost_analyzer.repository;

import com.rage.cloud_cost_analyzer.model.Cost;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CostRepository extends MongoRepository<Cost,String> {

   List<Cost> findByUserId(String userId);
}
