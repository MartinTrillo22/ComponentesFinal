package com.example.demo.auth.service;

import com.example.demo.usuarios.model.EstadoUsuario;
import com.example.demo.usuarios.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

  private final Usuario usuario;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return usuario.getRoles().stream().map(rol -> new SimpleGrantedAuthority("ROLE_"+rol.getNombre())).collect(Collectors.toList());
  }

  @Override
  public String getPassword() {
    return usuario.getPassword();
  }

  @Override
  public String getUsername() {
    return usuario.getEmail();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return usuario.getEstado().equals(EstadoUsuario.ACTIVO);
  }

  public Usuario getUsuario(){
    return usuario;
  }
}
