package com.example.demo.auth;

import com.example.demo.auth.dto.LoginDto;
import com.example.demo.auth.dto.RegistroDto;
import com.example.demo.auth.jwt.JwtService;
import com.example.demo.shared.ApiResponse;
import com.example.demo.usuarios.dto.UsuarioResponseDTO;
import com.example.demo.usuarios.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
public class AuthController {

  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final UsuarioService usuarioService;

  @PostMapping("/login")
  public ResponseEntity<ApiResponse<String>> login(@RequestBody LoginDto request) {

    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    request.email(),
                    request.password()
            )
    );
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();

    String jwtToken = jwtService.generateToken(userDetails);

    return ResponseEntity.ok(
            ApiResponse.of("Autenticación exitosa. Token generado.", jwtToken)
    );

  }
  @PostMapping("/registro")
  public ResponseEntity<ApiResponse<UsuarioResponseDTO>> registro(RegistroDto request) {

    UsuarioResponseDTO responseDTO=usuarioService.registroExterno(request);

    return ResponseEntity.ok(
            ApiResponse.of("Registro exitoso.", responseDTO)
    );
  }

}
