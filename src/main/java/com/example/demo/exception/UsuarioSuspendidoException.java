package com.example.demo.exception;

public class UsuarioSuspendidoException extends RuntimeException{
  public UsuarioSuspendidoException(String message){
    super(message);
  }
}
