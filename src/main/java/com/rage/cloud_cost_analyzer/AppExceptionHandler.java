package com.rage.cloud_cost_analyzer;

import com.rage.cloud_cost_analyzer.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice

public class AppExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e){

        return ResponseEntity.status(500).body(
                new ApiResponse<>("error", e.getMessage())
        );
    }
}
