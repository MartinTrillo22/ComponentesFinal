package com.example.demo.usuarios.controller;

import com.example.demo.usuarios.dto.UsuarioRequestDTO;
import com.example.demo.usuarios.dto.UsuarioResponseDTO;
import com.example.demo.usuarios.dto.UsuarioUpdateDTO;
import com.example.demo.usuarios.model.EstadoUsuario;
import com.example.demo.usuarios.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> crearUsuario(@Valid @RequestBody UsuarioRequestDTO request) {
        UsuarioResponseDTO usuario = usuarioService.crearUsuario(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> obtenerTodosLosUsuarios() {
        List<UsuarioResponseDTO> usuarios = usuarioService.obtenerTodosLosUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> obtenerUsuarioPorId(@PathVariable Long id) {
        UsuarioResponseDTO usuario = usuarioService.obtenerUsuarioPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioResponseDTO> obtenerUsuarioPorEmail(@PathVariable String email) {
        UsuarioResponseDTO usuario = usuarioService.obtenerUsuarioPorEmail(email);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<UsuarioResponseDTO>> obtenerUsuariosPorEstado(@PathVariable EstadoUsuario estado) {
        List<UsuarioResponseDTO> usuarios = usuarioService.obtenerUsuariosPorEstado(estado);
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/rol/{nombreRol}")
    public ResponseEntity<List<UsuarioResponseDTO>> obtenerUsuariosPorRol(@PathVariable String nombreRol) {
        List<UsuarioResponseDTO> usuarios = usuarioService.obtenerUsuariosPorRol(nombreRol);
        return ResponseEntity.ok(usuarios);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizarUsuario(@PathVariable Long id, @Valid @RequestBody UsuarioUpdateDTO update) {
        UsuarioResponseDTO usuario = usuarioService.actualizarUsuario(id, update);
        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Void> cambiarEstadoUsuario(@PathVariable Long id, @RequestBody EstadoUsuarioRequest request) {
        usuarioService.cambiarEstadoUsuario(id, request.getEstado());
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/suspender")
    public ResponseEntity<Void> suspenderUsuario(@PathVariable Long id) {
        usuarioService.suspenderUsuario(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/activar")
    public ResponseEntity<Void> activarUsuario(@PathVariable Long id) {
        usuarioService.activarUsuario(id);
        return ResponseEntity.ok().build();
    }

    public static class EstadoUsuarioRequest {
        private EstadoUsuario estado;

        public EstadoUsuario getEstado() {
            return estado;
        }

        public void setEstado(EstadoUsuario estado) {
            this.estado = estado;
        }
    }
}