package com.example.demo.shared;

public record ApiError(int responseCode, String responseMessage) {}