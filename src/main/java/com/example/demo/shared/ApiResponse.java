package com.example.demo.shared;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T>{
    private LocalDateTime timestamp;
    private String message;
    private T data;

    public ApiResponse( String message, T data) {
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.data = data;
    }

  public static <T> ApiResponse<T> of(String message, T data) {
    return new ApiResponse<>(message, data);
  }
}
