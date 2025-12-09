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

  @ExceptionHandler(PrestamoNoDevueltoException.class)
  public ResponseEntity<ApiResponse<Void>> handlePrestamoNoDevuelto(PrestamoNoDevueltoException ex){
    ApiResponse<Void> response=ApiResponse.of(ex.getMessage(),null);
    return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
  }

  @ExceptionHandler(PedidoEstadoDevueltoException.class)
  public ResponseEntity<ApiResponse<Void>> handlePedidoEstadoDevuelto(PedidoEstadoDevueltoException ex){
    ApiResponse<Void> response=ApiResponse.of(ex.getMessage(),null);
    return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
  }

  @ExceptionHandler(IsbnDuplicadoException.class)
  public ResponseEntity<ApiResponse<Void>> handleIsbnDuplicado(IsbnDuplicadoException ex){
    ApiResponse<Void> response=ApiResponse.of(ex.getMessage(),null);
    return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
  }

  @ExceptionHandler(EmailDuplicadoException.class)
  public ResponseEntity<ApiResponse<Void>> handleEmailDuplicado(EmailDuplicadoException ex){
    ApiResponse<Void> response=ApiResponse.of(ex.getMessage(),null);
    return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
  }

  @ExceptionHandler(UsuarioNoEncontradoException.class)
  public ResponseEntity<ApiResponse<String>> handleUsuarioNoEncontrado(UsuarioNoEncontradoException ex){
    ApiResponse<String> response=ApiResponse.of(ex.getMessage(),null);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
  }

  @ExceptionHandler(RolNoEncontradoException.class)
  public ResponseEntity<ApiResponse<String>> handleRolNoEncontrado(RolNoEncontradoException ex){
    ApiResponse<String> response=ApiResponse.of(ex.getMessage(),null);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
  }

  @ExceptionHandler(EstadoUsuarioInvalidoException.class)
  public ResponseEntity<ApiResponse<Void>> handleEstadoUsuarioInvalido(EstadoUsuarioInvalidoException ex){
    ApiResponse<Void> response=ApiResponse.of(ex.getMessage(),null);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ExceptionHandler(DatosInvalidosException.class)
  public ResponseEntity<ApiResponse<Void>> handleDatosInvalidos(DatosInvalidosException ex){
    ApiResponse<Void> response=ApiResponse.of(ex.getMessage(),null);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ExceptionHandler(UsuarioNoEstudianteException.class)
  public ResponseEntity<ApiResponse<Void>> handleUsuarioNoEstudiante(UsuarioNoEstudianteException ex){
    ApiResponse<Void> response=ApiResponse.of(ex.getMessage(),null);
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
  }

}
