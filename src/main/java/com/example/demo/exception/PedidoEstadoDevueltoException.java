package com.example.demo.exception;

public class PedidoEstadoDevueltoException extends RuntimeException{
  public PedidoEstadoDevueltoException(String mensaje){
    super(mensaje);
  }
}
