package com.example.demo.exception;

public class UsuarioNoEstudianteException extends RuntimeException{
  public UsuarioNoEstudianteException(String mensaje){
    super(mensaje);
  }
}

