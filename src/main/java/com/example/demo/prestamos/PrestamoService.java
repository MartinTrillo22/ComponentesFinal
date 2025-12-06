package com.example.demo.prestamos;

import java.util.List;

public interface PrestamoService {
  Prestamo prestarLibro(Long libroId, Long usuarioId);
  List<Prestamo> obtenerPrestamosPorUsuario(Long usuarioId);
  Prestamo renovarPrestamo(Long prestamoId);
  void devolverLibro(Long prestamoId);
  Prestamo obtenerPrestamoPorId(Long prestamoId);
  List<Prestamo> obtenerTodosLosPrestamos();
}
