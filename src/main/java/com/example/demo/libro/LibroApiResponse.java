package com.example.demo.libro;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibroApiResponse <T>{
    private LocalDateTime timestamp;
    private int status;
    private String message;
    private T data;

    public LibroApiResponse(int status, String message, T data) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static <T> LibroApiResponse<T> success(int status, String message, T data) {
        return new LibroApiResponse<>(status, message, data);
    }

    public static <T> LibroApiResponse<T> created(String message, T data) {
        return new LibroApiResponse<>(201, message, data);
    }

    public static <T> LibroApiResponse<T> ok(String message, T data) {
        return new LibroApiResponse<>(200, message, data);
    }
}
