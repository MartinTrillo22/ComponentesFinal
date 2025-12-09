package com.example.demo.usuarios.controller;

import com.example.demo.usuarios.dto.RolRequestDTO;
import com.example.demo.usuarios.model.Rol;
import com.example.demo.usuarios.service.RolService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/roles")
@CrossOrigin(origins = "*")
public class RolController {

    @Autowired
    private RolService rolService;

    @PostMapping
    public ResponseEntity<Rol> crearRol(@Valid @RequestBody RolRequestDTO request) {
        Rol rol = rolService.crearRol(request.getNombre(), request.getDescripcion());
        return ResponseEntity.status(HttpStatus.CREATED).body(rol);
    }

    @GetMapping
    public ResponseEntity<List<Rol>> obtenerTodosLosRoles() {
        List<Rol> roles = rolService.obtenerTodosLosRoles();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rol> obtenerRolPorId(@PathVariable Long id) {
        Rol rol = rolService.obtenerRolPorId(id);
        return ResponseEntity.ok(rol);
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Rol> obtenerRolPorNombre(@PathVariable String nombre) {
        Rol rol = rolService.obtenerRolPorNombre(nombre);
        return ResponseEntity.ok(rol);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rol> actualizarRol(@PathVariable Long id, @Valid @RequestBody RolRequestDTO request) {
        Rol rol = rolService.actualizarRol(id, request.getNombre(), request.getDescripcion());
        return ResponseEntity.ok(rol);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRol(@PathVariable Long id) {
        rolService.eliminarRol(id);
        return ResponseEntity.noContent().build();
    }
}