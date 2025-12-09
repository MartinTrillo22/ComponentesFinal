package com.example.demo.exception;

public class PrestamoNoDevueltoException extends RuntimeException {
  public PrestamoNoDevueltoException(String message) {
    super(message);
  }
}
