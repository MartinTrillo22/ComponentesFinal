package com.example.demo.usuarios.service;

import com.example.demo.auth.dto.RegistroDto;
import com.example.demo.usuarios.dto.UsuarioRequestDTO;
import com.example.demo.usuarios.dto.UsuarioResponseDTO;
import com.example.demo.usuarios.dto.UsuarioUpdateDTO;
import com.example.demo.usuarios.model.EstadoUsuario;

import java.util.List;

public interface UsuarioService {

    UsuarioResponseDTO crearUsuario(UsuarioRequestDTO request);

    UsuarioResponseDTO obtenerUsuarioPorId(Long id);

    UsuarioResponseDTO obtenerUsuarioPorEmail(String email);

    List<UsuarioResponseDTO> obtenerTodosLosUsuarios();

    List<UsuarioResponseDTO> obtenerUsuariosPorEstado(EstadoUsuario estado);

    UsuarioResponseDTO actualizarUsuario(Long id, UsuarioUpdateDTO update);

    void eliminarUsuario(Long id);

    void cambiarEstadoUsuario(Long id, EstadoUsuario nuevoEstado);

    void suspenderUsuario(Long id);

    void activarUsuario(Long id);

    List<UsuarioResponseDTO> obtenerUsuariosPorRol(String nombreRol);

    UsuarioResponseDTO registroExterno(RegistroDto registroDto);
}