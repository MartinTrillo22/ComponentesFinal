package com.example.demo.prestamos;

import com.example.demo.prestamos.dto.PrestamoRequest;
import com.example.demo.shared.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/prestamos")
public class PrestamoController {

  private final PrestamoService prestamoService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Prestamo>>> obtenerTodosLosPrestamos() {
      List<Prestamo> prestamos = prestamoService.obtenerTodosLosPrestamos();
      ApiResponse<List<Prestamo>> response = ApiResponse.of("Prestamos obtenidos exitosamente", prestamos);
      return ResponseEntity.ok(response);
  }

    @PostMapping
    public ResponseEntity<ApiResponse<Prestamo>> crearPrestamo(@RequestBody PrestamoRequest prestamo) {
        Prestamo nuevoPrestamo = prestamoService.prestarLibro(prestamo.libroId(), prestamo.clienteId());
        ApiResponse<Prestamo> response = ApiResponse.of("Prestamo creado exitosamente", nuevoPrestamo);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Prestamo>> obtenerPrestamoPorId(@PathVariable Long id) {
        Prestamo prestamo = prestamoService.obtenerPrestamoPorId(id);
        ApiResponse<Prestamo> response = ApiResponse.of("Prestamo obtenido exitosamente", prestamo);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/devolver")
    public ResponseEntity<ApiResponse<Void>> devolverLibro(@PathVariable Long id){
       prestamoService.devolverLibro(id);
       ApiResponse<Void> apiResponse=ApiResponse.of("Libro devuelto con exito",null);
       return ResponseEntity.ok().body(apiResponse);
    }

}
