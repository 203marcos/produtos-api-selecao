package com.marcosdias.produtosapi.auth;

import com.marcosdias.produtosapi.auth.dto.AuthLoginRequestDTO;
import com.marcosdias.produtosapi.auth.dto.AuthLoginResponseDTO;
import com.marcosdias.produtosapi.security.JwtTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {

	private final AuthenticationManager authenticationManager;
	private final JwtTokenService jwtTokenService;

	public AuthController(AuthenticationManager authenticationManager, JwtTokenService jwtTokenService) {
		this.authenticationManager = authenticationManager;
		this.jwtTokenService = jwtTokenService;
	}

	@PostMapping("/login")
	public ResponseEntity<AuthLoginResponseDTO> login(@Valid @RequestBody AuthLoginRequestDTO request) {
		try {
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getSenha()));
			String token = jwtTokenService.gerarToken(authentication.getName());
			return ResponseEntity.ok(new AuthLoginResponseDTO(token));
		} catch (BadCredentialsException ex) {
			throw new BadCredentialsException("Email ou senha invalidos");
		}
	}
}
