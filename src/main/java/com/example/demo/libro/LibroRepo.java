package com.example.demo.libro;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LibroRepo extends JpaRepository<Libro, Long> {
    boolean existsByIsbn(String isbn);
    @Query( "SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END FROM Libro l WHERE l.isbn = isbn AND l.id != id")
    boolean existsByIsbnAndIdNot(String isbn, Long id);

    boolean existsByTitulo(String titulo);
    @Query( "SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END FROM Libro l WHERE l.titulo = titulo AND l.id != id")
    boolean existsByTituloAndIdNot(String titulo, Long id);

}
