package com.example.demo.prestamos.dto;

public record PrestamoResponseDto(
        Long id,
        Long libroId,
        Long usuarioId,
        String fechaPrestamo,
        String fechaDevolucion,
        String estado
) {
}
