package com.example.demo.usuarios.repository;

import com.example.demo.usuarios.model.EstadoUsuario;
import com.example.demo.usuarios.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);

    List<Usuario> findByEstado(EstadoUsuario estado);

    List<Usuario> findByRoles_Nombre(String nombreRol);
}

