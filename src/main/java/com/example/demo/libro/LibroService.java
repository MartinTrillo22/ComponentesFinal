package com.example.demo.libro;

import java.util.List;

public interface LibroService {
    Libro save(Libro libro);
    Libro findById(Long id);
    List<Libro> findAll();
    Libro update(Long id,Libro libro);
    void delete(Long id);

}
