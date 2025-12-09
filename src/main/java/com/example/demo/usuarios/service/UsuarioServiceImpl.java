package com.example.demo.usuarios.service;

import com.example.demo.auth.dto.RegistroDto;
import com.example.demo.exception.EmailDuplicadoException;
import com.example.demo.exception.RolNoEncontradoException;
import com.example.demo.exception.UsuarioNoEncontradoException;
import com.example.demo.usuarios.dto.UsuarioRequestDTO;
import com.example.demo.usuarios.dto.UsuarioResponseDTO;
import com.example.demo.usuarios.dto.UsuarioUpdateDTO;
import com.example.demo.usuarios.model.EstadoUsuario;
import com.example.demo.usuarios.model.Rol;
import com.example.demo.usuarios.model.Usuario;
import com.example.demo.usuarios.repository.RolRepository;
import com.example.demo.usuarios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @PreAuthorize( "hasRole('ADMIN')" )
    public UsuarioResponseDTO crearUsuario(UsuarioRequestDTO request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new EmailDuplicadoException("El email ya está registrado: " + request.getEmail());
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setTelefono(request.getTelefono());
        usuario.setDireccion(request.getDireccion());
        usuario.setFechaRegistro(LocalDate.now());
        usuario.setEstado(EstadoUsuario.ACTIVO);

        Set<Rol> roles = new HashSet<>();
        for (Long rolId : request.getRolesIds()) {
            Rol rol = rolRepository.findById(rolId)
                    .orElseThrow(() -> new RolNoEncontradoException("Rol no encontrado con id: " + rolId));
            roles.add(rol);
        }
        usuario.setRoles(roles);

        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        return convertirADTO(usuarioGuardado);
    }

    @Override
    @PreAuthorize( "hasRole('ADMIN')" )
    public UsuarioResponseDTO obtenerUsuarioPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario no encontrado con id: " + id));
        return convertirADTO(usuario);
    }

    @Override
    @PreAuthorize( "hasRole('ADMIN')" )
    public UsuarioResponseDTO obtenerUsuarioPorEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario no encontrado con email: " + email));
        return convertirADTO(usuario);
    }

    @Override
    @PreAuthorize( "hasRole('ADMIN')" )
    public List<UsuarioResponseDTO> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize( "hasRole('ADMIN')" )
    public List<UsuarioResponseDTO> obtenerUsuariosPorEstado(EstadoUsuario estado) {
        return usuarioRepository.findByEstado(estado).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize( "hasRole('ADMIN')" )
    public UsuarioResponseDTO actualizarUsuario(Long id, UsuarioUpdateDTO update) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario no encontrado con id: " + id));

        if (update.getNombre() != null) {
            usuario.setNombre(update.getNombre());
        }
        if (update.getTelefono() != null) {
            usuario.setTelefono(update.getTelefono());
        }
        if (update.getDireccion() != null) {
            usuario.setDireccion(update.getDireccion());
        }
        if (update.getEstado() != null) {
            usuario.setEstado(update.getEstado());
        }
        if (update.getRolesIds() != null && !update.getRolesIds().isEmpty()) {
            Set<Rol> roles = new HashSet<>();
            for (Long rolId : update.getRolesIds()) {
                Rol rol = rolRepository.findById(rolId)
                        .orElseThrow(() -> new RolNoEncontradoException("Rol no encontrado con id: " + rolId));
                roles.add(rol);
            }
            usuario.setRoles(roles);
        }

        Usuario usuarioActualizado = usuarioRepository.save(usuario);
        return convertirADTO(usuarioActualizado);
    }

    @Override
    @PreAuthorize( "hasRole('ADMIN')" )
    public void eliminarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new UsuarioNoEncontradoException("Usuario no encontrado con id: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    @Override
    @PreAuthorize( "hasRole('ADMIN')" )
    public void cambiarEstadoUsuario(Long id, EstadoUsuario nuevoEstado) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario no encontrado con id: " + id));
        usuario.setEstado(nuevoEstado);
        usuarioRepository.save(usuario);
    }

    @Override
    @PreAuthorize( "hasRole('ADMIN')" )
    public void suspenderUsuario(Long id) {
        cambiarEstadoUsuario(id, EstadoUsuario.SUSPENDIDO);
    }

    @Override
    @PreAuthorize( "hasRole('ADMIN')" )
    public void activarUsuario(Long id) {
        cambiarEstadoUsuario(id, EstadoUsuario.ACTIVO);
    }

    @Override
    @PreAuthorize( "hasRole('ADMIN')" )
    public List<UsuarioResponseDTO> obtenerUsuariosPorRol(String nombreRol) {
        return usuarioRepository.findByRoles_Nombre(nombreRol).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioResponseDTO registroExterno(RegistroDto registroDto){
      Long rolId=rolRepository.findByNombre("ESTUDIANTE")
              .orElseThrow(() -> new RolNoEncontradoException("Rol no encontrado: ESTUDIANTE")).getId();

      UsuarioRequestDTO usuarioRequestDTO=new UsuarioRequestDTO(
              registroDto.nombre(),
              registroDto.email(),
              registroDto.password(),
              registroDto.telefono(),
              null,
              List.of(rolId)
      );
      return crearUsuario(usuarioRequestDTO);
    }

    private UsuarioResponseDTO convertirADTO(Usuario usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setEmail(usuario.getEmail());
        dto.setTelefono(usuario.getTelefono());
        dto.setDireccion(usuario.getDireccion());
        dto.setFechaRegistro(usuario.getFechaRegistro());
        dto.setEstado(usuario.getEstado().name());
        dto.setRoles(usuario.getRoles().stream()
                .map(Rol::getNombre)
                .collect(Collectors.toList()));
        return dto;
    }
}