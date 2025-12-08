package com.example.demo.usuarios.service;

import com.example.demo.usuarios.model.Rol;

import java.util.List;

public interface RolService {

    Rol crearRol(String nombre, String descripcion);

    Rol obtenerRolPorId(Long id);

    Rol obtenerRolPorNombre(String nombre);

    List<Rol> obtenerTodosLosRoles();

    Rol actualizarRol(Long id, String nombre, String descripcion);

    void eliminarRol(Long id);
}