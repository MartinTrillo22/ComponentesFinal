package com.example.demo.prestamos;

import com.example.demo.libro.Libro;
import com.example.demo.usuarios.model.Usuario;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Prestamo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(name = "estado", nullable = false, length = 20)
  private PedidoEstado estado;

  @CreationTimestamp
  @Column(name = "fecha_prestamo", updatable = false,nullable = false)
  private LocalDateTime fechaPrestamo;

  @Column(name = "fecha_devolucion", nullable = false)
  private LocalDateTime fechaDevolucionEsperada;

  @Column(name = "fecha_devolucion_real")
  private LocalDateTime fechaDevolucionReal;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "usuario_id", nullable = false)
  @JsonBackReference("usuario-prestamos")
  private Usuario usuario;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "libro_id", nullable = false)
  @JsonBackReference("libro-prestamo")
  private Libro libro;
}
