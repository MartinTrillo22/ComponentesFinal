package com.example.demo.libro;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LibroServiceImpl implements LibroService{

    private final LibroRepo libroRepo;


    @Override
    public Libro save(Libro libro) {
        if( libroRepo.existsByTitulo(libro.getTitulo())){
            throw new IllegalArgumentException("El libro ya existe");
        }

        if( libroRepo.existsByIsbn(libro.getIsbn())){
            throw new IllegalArgumentException("El ISBN ya existe");
        }

        if( libroRepo.existsByAutor(libro.getAutor())){
            throw new IllegalArgumentException("El autor ya existe");
        }
        return libroRepo.save(libro);
    }

    @Override
    public Libro findById(Long id) {
        return libroRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Libro no encontrado con id: " + id));

    }

    @Override
    public List<Libro> findAll() {
        return libroRepo.findAll();
    }

    @Override
    public Libro update(Long id, Libro libro) {
        Libro libroExistente = findById(id);

        if( libroRepo.existsByTituloAndIdNot(libro.getTitulo(), id)){
            throw new IllegalArgumentException("El libro ya existe");
        }

        if( libroRepo.existsByIsbnAndIdNot(libro.getIsbn(), id)){
            throw new IllegalArgumentException("El ISBN ya existe");
        }

        if( libroRepo.existsByAutorAndIdNot(libro.getAutor(), id)){
            throw new IllegalArgumentException("El autor ya existe");
        }

        libroExistente.setTitulo(libro.getTitulo());
        libroExistente.setIsbn(libro.getIsbn());
        libroExistente.setAutor(libro.getAutor());
        libroExistente.setFechaPublicacion(libro.getFechaPublicacion());
        libroExistente.setStock( libro.getStock());

        return libroRepo.save(libroExistente);
    }

    @Override
    public void delete(Long id) {
        libroRepo.deleteById(id);

    }
}
