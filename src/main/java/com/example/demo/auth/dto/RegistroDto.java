package com.example.demo.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegistroDto(
    @NotBlank String nombre,
    @NotBlank @Email String email,
    @NotBlank String password,
    String telefono
) {
}
