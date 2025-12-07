package com.example.demo.exception;

import com.example.demo.shared.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(RecursoNoEncontradoException.class)
  public ResponseEntity<ApiResponse<String>> handleRecursoNoEncontrado(RecursoNoEncontradoException ex){
    ApiResponse<String> response= ApiResponse.of(ex.getMessage(), null);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
  }

  @ExceptionHandler(StockInsuficienteException.class)
  public ResponseEntity<ApiResponse<Void>> handleStockInsuficiente(StockInsuficienteException ex){
    ApiResponse<Void> response= ApiResponse.of(ex.getMessage(),null);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ExceptionHandler(UsuarioSuspendidoException.class)
  public ResponseEntity<ApiResponse<Void>> handleUsuarioSuspendido(UsuarioSuspendidoException ex){
    ApiResponse<Void> response=ApiResponse.of(ex.getMessage(),null);
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
  }

  @ExceptionHandler(PedidoEstadoDevueltoException.class)
  public ResponseEntity<ApiResponse<Void>> handlePedidoEstadoDevuelto(PedidoEstadoDevueltoException ex){
    ApiResponse<Void> response=ApiResponse.of(ex.getMessage(),null);
    return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
  }

}
