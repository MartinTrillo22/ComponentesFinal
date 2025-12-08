package com.example.demo.prestamos;

import com.example.demo.exception.PedidoEstadoDevueltoException;
import com.example.demo.exception.RecursoNoEncontradoException;
import com.example.demo.exception.StockInsuficienteException;
import com.example.demo.exception.UsuarioNoEstudianteException;
import com.example.demo.exception.UsuarioSuspendidoException;
import com.example.demo.libro.Libro;
import com.example.demo.libro.LibroService;
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
  public Prestamo prestarLibro(Long libroId, Long usuarioId) {
    Libro libro=libroService.findById(libroId);
    Usuario usuario=usuarioRepo.findById(usuarioId).orElseThrow(()->
      new RecursoNoEncontradoException("Usuario no encontrado con ID: "+usuarioId)
    );

    // Verificar que el usuario tenga el rol ESTUDIANTE
    boolean esEstudiante = usuario.getRoles().stream()
      .anyMatch(rol -> rol.getNombre().equalsIgnoreCase("ROL_ESTUDIANTE") ||
                       rol.getNombre().equalsIgnoreCase("ESTUDIANTE"));

    if(!esEstudiante){
      throw new UsuarioNoEstudianteException("Solo los usuarios con rol ESTUDIANTE pueden realizar préstamos. Usuario ID: "+usuarioId);
    }

    if(libro.getStock()<1){
      throw new StockInsuficienteException("No hay stock disponible para el libro con ID: "+libroId);
    }
    if(usuario.getEstado() == EstadoUsuario.SUSPENDIDO){
      throw new UsuarioSuspendidoException("El usuario con ID: "+usuarioId+" está suspendido y no puede realizar préstamos.");
    }

    libro.setStock(libro.getStock()-1);

    Prestamo prestamo=new Prestamo();
    prestamo.setLibro(libro);
    prestamo.setUsuario(usuario);
    prestamo.setEstado(PedidoEstado.ACTIVO);
    prestamo.setFechaDevolucionEsperada(LocalDateTime.now().plusDays(7));
    prestamoRepo.save(prestamo);

    return prestamo;
  }

  @Override
  @Transactional
  @PreAuthorize( "hasAnyRole('ADMIN','BIBLIOTECARIO')" )
  public List<Prestamo> obtenerPrestamosPorUsuario(Long usuarioId) {
    Usuario usuario=usuarioRepo.findById(usuarioId).orElseThrow(()->
      new RecursoNoEncontradoException("Usuario no encontrado con ID: "+usuarioId)
    );
    return usuario.getPrestamo();
  }

  @Override
  @Transactional
  @PreAuthorize( "hasAnyRole('ADMIN','BIBLIOTECARIO')" )
  public Prestamo renovarPrestamo(Long prestamoId) {
    Prestamo prestamo=obtenerPrestamoPorId(prestamoId);
    if(prestamo.getEstado()==PedidoEstado.DEVUELTO){
      throw new PedidoEstadoDevueltoException("El préstamo con ID: "+prestamoId+" ya ha sido devuelto y no puede ser renovado.");
    }
    prestamo.setFechaDevolucionEsperada(prestamo.getFechaDevolucionEsperada().plusDays(7));
    prestamo.setEstado(PedidoEstado.RENOVADO);
    return prestamo;
  }

  @Override
  @Transactional
  @PreAuthorize( "hasAnyRole('ADMIN','BIBLIOTECARIO')" )
  public void devolverLibro(Long prestamoId) {
    Prestamo prestamo=obtenerPrestamoPorId(prestamoId);
    if(prestamo.getEstado()==PedidoEstado.DEVUELTO){
      throw new PedidoEstadoDevueltoException("El préstamo con ID: "+prestamoId+" ya ha sido devuelto.");
    }
    Libro libro=prestamo.getLibro();
    libro.setStock(libro.getStock()+1);
    prestamo.setEstado(PedidoEstado.DEVUELTO);
    prestamo.setFechaDevolucionReal(LocalDateTime.now());
  }

  @Override
  @PreAuthorize( "hasAnyRole('ADMIN','BIBLIOTECARIO')" )
  public Prestamo obtenerPrestamoPorId(Long prestamoId) {
    return prestamoRepo.findById(prestamoId).orElseThrow(()->
      new RecursoNoEncontradoException("Préstamo no encontrado con ID: "+prestamoId)
    );
  }

  @Override
  @PreAuthorize( "hasAnyRole('ADMIN','BIBLIOTECARIO')" )
  public List<Prestamo> obtenerTodosLosPrestamos() {
    return prestamoRepo.findAll();
  }
}
