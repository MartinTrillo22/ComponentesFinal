package com.example.demo.prestamos.dto;

public record PrestamoRequest(
        Long libroId,
        Long clienteId
) {
}
