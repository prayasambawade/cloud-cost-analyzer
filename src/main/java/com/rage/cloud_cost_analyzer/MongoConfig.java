package com.rage.cloud_cost_analyzer;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfig {

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create(
                "mongodb+srv://prayasambawade:Prayas123@cluster0.t1ktek3.mongodb.net/cloud_analyzer_db"
        );
    }
    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), "cloud_analyzer_db");
    }
}
