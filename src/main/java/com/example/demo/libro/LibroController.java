package com.example.demo.libro;



import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/libros")
@RequiredArgsConstructor
public class LibroController {

    private final LibroService libroService;

    @PostMapping
    public ResponseEntity<LibroApiResponse<Libro>> saveLibro(@Valid @RequestBody Libro libro){
        Libro newLibro = libroService.save(libro);
        LibroApiResponse<Libro> response = LibroApiResponse.created(
                "Libro creado exitosamente",
                newLibro
        );
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping
    public ResponseEntity<List <Libro>> getAllLibros() {
        List<Libro> allLibros = libroService.findAll();
        return ResponseEntity.ok(allLibros);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Libro> getLibro(@PathVariable Long id) {
        Libro libro = libroService.findById(id);
        return ResponseEntity.ok(libro);
    }

    @PutMapping( "/{id}" )
    public ResponseEntity< LibroApiResponse<Libro>> updateLibro(@PathVariable Long id, @RequestBody Libro libro) {
        Libro newLibro = libroService.update(id, libro);
        LibroApiResponse<Libro> response = LibroApiResponse.ok(
                "Libro actualizado exitosamente",
                newLibro
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLibro(@PathVariable Long id) {
        libroService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
