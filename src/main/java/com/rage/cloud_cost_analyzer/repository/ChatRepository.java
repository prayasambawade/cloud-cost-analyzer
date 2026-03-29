package com.rage.cloud_cost_analyzer.repository;

import com.rage.cloud_cost_analyzer.model.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatRepository extends MongoRepository<ChatMessage,String> {

    List<ChatMessage> findByUserId(String userId);
}
