package com.example.demo.libro;


import com.example.demo.exception.IsbnDuplicadoException;
import com.example.demo.exception.RecursoNoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class LibroServiceImpl implements LibroService{

    private final LibroRepo libroRepo;


    @Override
    @PreAuthorize( "hasAnyRole('ADMIN','BIBLIOTECARIO','GESTOR_INVENTARIO')" )
    public Libro save(Libro libro) {
        if( libroRepo.existsByIsbn(libro.getIsbn())){
            throw new IsbnDuplicadoException("Ya existe un libro con el ISBN: " + libro.getIsbn());
        }
        return libroRepo.save(libro);
    }

    @Override
    @PreAuthorize( "hasAnyRole('ADMIN','BIBLIOTECARIO','GESTOR_INVENTARIO','ESTUDIANTE')" )
    public Libro findById(Long id) {
        return libroRepo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Libro no encontrado con id: " + id));

    }

    @Override
    @PreAuthorize( "hasAnyRole('ADMIN','BIBLIOTECARIO','GESTOR_INVENTARIO','ESTUDIANTE')" )
    public List<Libro> findAll() {
        return libroRepo.findAll();
    }

    @Override
    @PreAuthorize( "hasAnyRole('ADMIN','BIBLIOTECARIO','GESTOR_INVENTARIO')" )
    public Libro update(Long id, Libro libro) {
        Libro libroExistente = findById(id);

        if( libroRepo.existsByIsbnAndIdNot(libro.getIsbn(), id)){
            throw new IsbnDuplicadoException("Ya existe un libro con el ISBN: " + libro.getIsbn());
        }


        libroExistente.setTitulo(libro.getTitulo());
        libroExistente.setIsbn(libro.getIsbn());
        libroExistente.setAutor(libro.getAutor());
        libroExistente.setFechaPublicacion(libro.getFechaPublicacion());
        libroExistente.setStock( libro.getStock());

        return libroRepo.save(libroExistente);
    }

    @Override
    @PreAuthorize( "hasAnyRole('ADMIN','BIBLIOTECARIO','GESTOR_INVENTARIO')" )
    public void delete(Long id) {
        libroRepo.deleteById(id);

    }
}
