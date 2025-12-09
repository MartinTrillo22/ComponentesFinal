package com.example.demo.prestamos;

import com.example.demo.exception.*;
import com.example.demo.libro.Libro;
import com.example.demo.libro.LibroService;
import com.example.demo.prestamos.dto.PrestamoResponseDto;
import com.example.demo.usuarios.model.EstadoUsuario;
import com.example.demo.usuarios.model.Usuario;
import com.example.demo.usuarios.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PrestamoServiceImpl implements PrestamoService{

  private final PrestamoRepo prestamoRepo;
  private final LibroService libroService;
  private final UsuarioRepository usuarioRepo;

  @Override
  @Transactional
  @PreAuthorize("hasAnyRole('ADMIN','BIBLIOTECARIO','ESTUDIANTE')")
  public PrestamoResponseDto prestarLibro(Long libroId, Long usuarioId) {
    Libro libro=libroService.findById(libroId);
    Usuario usuario=usuarioRepo.findById(usuarioId).orElseThrow(()->
      new RecursoNoEncontradoException("Usuario no encontrado con ID: "+usuarioId)
    );

    // Verificar que el usuario tenga el rol ESTUDIANTE
    boolean esEstudiante = usuario.getRoles().stream()
      .anyMatch(rol -> rol.getNombre().equalsIgnoreCase("ESTUDIANTE"));

    if(!esEstudiante){
      throw new UsuarioNoEstudianteException("Solo los usuarios con rol ESTUDIANTE pueden realizar préstamos. Usuario ID: "+usuarioId);
    }

    if(usuario.getEstado() == EstadoUsuario.SUSPENDIDO){
      throw new UsuarioSuspendidoException("El usuario con ID: "+usuarioId+" está suspendido y no puede realizar préstamos.");
    }

    boolean prestamoNodevuelto= usuario.getPrestamo().stream().anyMatch(prestamo -> {
      String estado= prestamo.getEstado().name();
      return estado.equalsIgnoreCase("ACTIVO") || estado.equalsIgnoreCase("RENOVADO")|| estado.equalsIgnoreCase("VENCIDO");
    });

    if(prestamoNodevuelto){
      throw new PrestamoNoDevueltoException("El usuario con ID: "+usuarioId+" tiene un préstamo no devuelto y no puede realizar un nuevo préstamo.");
    }

    libro.disminuirStock();

    Prestamo prestamo=new Prestamo();
    prestamo.setLibro(libro);
    prestamo.setUsuario(usuario);
    prestamo.setEstado(PedidoEstado.ACTIVO);
    prestamo.setFechaDevolucionEsperada(LocalDateTime.now().plusDays(7));

    prestamoRepo.save(prestamo);

    return toDTO(prestamo);
  }

  @Override
  @Transactional
  @PreAuthorize( "hasAnyRole('ADMIN','BIBLIOTECARIO')" )
  public List<PrestamoResponseDto> obtenerPrestamosPorUsuario(Long usuarioId) {
    Usuario usuario=usuarioRepo.findById(usuarioId).orElseThrow(()->
      new RecursoNoEncontradoException("Usuario no encontrado con ID: "+usuarioId)
    );
    return  usuario.getPrestamo().stream().map(this::toDTO).toList();
  }

  @Override
  @Transactional
  @PreAuthorize( "hasAnyRole('ADMIN','BIBLIOTECARIO')" )
  public PrestamoResponseDto renovarPrestamo(Long prestamoId) {
    Prestamo prestamo=prestamoRepo.findById(prestamoId).orElseThrow(()->
            new RecursoNoEncontradoException("Préstamo no encontrado con ID: "+prestamoId)
    );
    if(PedidoEstado.DEVUELTO.equals(prestamo.getEstado())){
      throw new PedidoEstadoDevueltoException("El préstamo con ID: "+prestamoId+" ya ha sido devuelto y no puede ser renovado.");
    }
    prestamo.setFechaDevolucionEsperada(prestamo.getFechaDevolucionEsperada().plusDays(7));
    prestamo.setEstado(PedidoEstado.RENOVADO);
    prestamoRepo.save(prestamo);
    return toDTO(prestamo) ;
  }

  @Override
  @Transactional
  @PreAuthorize( "hasAnyRole('ADMIN','BIBLIOTECARIO')" )
  public void devolverLibro(Long prestamoId) {
    Prestamo prestamo=prestamoRepo.findById(prestamoId).orElseThrow(()->
            new RecursoNoEncontradoException("Préstamo no encontrado con ID: "+prestamoId)
    );
        if(PedidoEstado.DEVUELTO.equals(prestamo.getEstado())){
      throw new PedidoEstadoDevueltoException("El préstamo con ID: "+prestamoId+" ya ha sido devuelto.");
    }
    Libro libro=prestamo.getLibro();
        libro.setStock(libro.getStock()+1);
    libroService.save(libro);
    prestamo.setEstado(PedidoEstado.DEVUELTO);
    prestamo.setFechaDevolucionReal(LocalDateTime.now());
    prestamoRepo.save(prestamo);
  }

  @Override
  @PreAuthorize( "hasAnyRole('ADMIN','BIBLIOTECARIO')" )
  public PrestamoResponseDto obtenerPrestamoPorId(Long prestamoId) {
    Prestamo prestamo= prestamoRepo.findById(prestamoId).orElseThrow(()->
            new RecursoNoEncontradoException("Préstamo no encontrado con ID: "+prestamoId)
    );
    return toDTO(prestamo);
  }

  @Override
  @PreAuthorize( "hasAnyRole('ADMIN','BIBLIOTECARIO')" )
  public List<PrestamoResponseDto> obtenerTodosLosPrestamos() {
    List<Prestamo> prestamos=prestamoRepo.findAll();
    return prestamos.stream().map(this::toDTO).toList();
  }

  private PrestamoResponseDto toDTO(Prestamo prestamo) {
    return new PrestamoResponseDto(
            prestamo.getId(),
            prestamo.getLibro().getId(),
            prestamo.getUsuario().getId(),
            prestamo.getFechaPrestamo().toString(),
            prestamo.getFechaDevolucionEsperada().toString(),
            prestamo.getEstado().name()
            );
  }
}
