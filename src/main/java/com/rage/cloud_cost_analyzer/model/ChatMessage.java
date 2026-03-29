package com.rage.cloud_cost_analyzer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor


@Document(collection = "chat_message")
public class ChatMessage {

    @Id
    private String id;
    private String userId;
    private String message;
    private String response;
    private LocalDateTime timestamp;

    public ChatMessage(){}

    public ChatMessage(String userId, String message, String response){
        this.userId = userId;
        this.message = message;
        this.response = response;
        this.timestamp = LocalDateTime.now();
    }



}
