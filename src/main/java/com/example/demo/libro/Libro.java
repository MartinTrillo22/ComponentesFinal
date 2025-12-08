package com.example.demo.libro;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Setter @Getter
@NoArgsConstructor
@Table( name = "libros")
public class Libro {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( nullable = false)
    @NotBlank( message = "El título no puede estar vacío")
    private String titulo;

    @Column( nullable = false)
    @NotBlank( message = "El autor no puede estar vacío")
    private String autor;

    @Column( nullable = false, unique = true)
    @NotBlank( message = "El ISBN no puede estar vacío")
    private String isbn;

    @Column( nullable = false)
    @NotNull( message = "El stock no puede estar vacío")
    private Integer stock;

    @Temporal(TemporalType.TIMESTAMP)
    @Column( nullable = false)
    @NotNull( message = "La fecha de publicación no puede estar vacía")
    private Date fechaPublicacion;

}
