package com.example.demo.usuarios.service;

import com.example.demo.exception.EmailDuplicadoException;
import com.example.demo.exception.RolNoEncontradoException;
import com.example.demo.usuarios.model.Rol;
import com.example.demo.usuarios.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RolServiceImpl implements RolService {

    @Autowired
    private RolRepository rolRepository;

    @Override
    public Rol crearRol(String nombre, String descripcion) {
        if (rolRepository.existsByNombre(nombre)) {
            throw new EmailDuplicadoException("El rol ya existe: " + nombre);
        }

        Rol rol = new Rol(nombre, descripcion);
        return rolRepository.save(rol);
    }

    @Override
    public Rol obtenerRolPorId(Long id) {
        return rolRepository.findById(id)
                .orElseThrow(() -> new RolNoEncontradoException("Rol no encontrado con id: " + id));
    }

    @Override
    public Rol obtenerRolPorNombre(String nombre) {
        return rolRepository.findByNombre(nombre)
                .orElseThrow(() -> new RolNoEncontradoException("Rol no encontrado con nombre: " + nombre));
    }

    @Override
    public List<Rol> obtenerTodosLosRoles() {
        return rolRepository.findAll();
    }

    @Override
    public Rol actualizarRol(Long id, String nombre, String descripcion) {
        Rol rol = obtenerRolPorId(id);

        if (nombre != null && !nombre.equals(rol.getNombre())) {
            if (rolRepository.existsByNombre(nombre)) {
                throw new EmailDuplicadoException("El rol ya existe: " + nombre);
            }
            rol.setNombre(nombre);
        }

        if (descripcion != null) {
            rol.setDescripcion(descripcion);
        }

        return rolRepository.save(rol);
    }

    @Override
    public void eliminarRol(Long id) {
        if (!rolRepository.existsById(id)) {
            throw new RolNoEncontradoException("Rol no encontrado con id: " + id);
        }
        rolRepository.deleteById(id);
    }
}