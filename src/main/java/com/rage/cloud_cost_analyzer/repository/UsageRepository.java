package com.rage.cloud_cost_analyzer.repository;

import com.rage.cloud_cost_analyzer.model.Usage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsageRepository extends MongoRepository<Usage,String> {


}
