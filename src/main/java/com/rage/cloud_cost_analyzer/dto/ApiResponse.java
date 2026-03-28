package com.rage.cloud_cost_analyzer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T>{
    private String status;
    private T data;
    private LocalDateTime timestamp;

    public ApiResponse(String status, T data){
        this.status = status;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }
}
