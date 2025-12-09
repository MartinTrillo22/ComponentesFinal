package com.example.demo.prestamos;

import com.example.demo.prestamos.dto.PrestamoResponseDto;

import java.util.List;

public interface PrestamoService {
  PrestamoResponseDto prestarLibro(Long libroId, Long usuarioId);
  List<PrestamoResponseDto> obtenerPrestamosPorUsuario(Long usuarioId);
  PrestamoResponseDto renovarPrestamo(Long prestamoId);
  void devolverLibro(Long prestamoId);
  PrestamoResponseDto obtenerPrestamoPorId(Long prestamoId);
  List<PrestamoResponseDto> obtenerTodosLosPrestamos();
}
