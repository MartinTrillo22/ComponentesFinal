package com.example.demo.auth;

import com.example.demo.usuarios.model.Usuario;
import com.example.demo.usuarios.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

  private final UsuarioRepository usuarioRepo;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Usuario usuario=usuarioRepo.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("Usuario o contraseña incorrectos"));

    return new CustomUserDetails(usuario);
  }
}
