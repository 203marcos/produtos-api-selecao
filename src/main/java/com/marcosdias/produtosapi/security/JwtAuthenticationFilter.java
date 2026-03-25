package com.marcosdias.produtosapi.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	/*
	 * Implementei um filtro JWT stateless que:
	 * 
	 * 1. Extrai o token do header "Authorization: Bearer <token>"
	 * 2. Valida o token contra a chave secreta
	 * 3. Extrai o email (subject) do token
	 * 4. Cria uma autenticação sem dependência de UserDetailsService
	 * 5. Define a autenticação no SecurityContext para a requisição
	 * 
	 * Arquitetura STATELESS: não há sessão no servidor. Cada token contém todas
	 * as informações necessárias para autenticar a requisição. Isso torna a API
	 * escalável (não precisa compartilhar sessões entre servidores).
	 */

	private final JwtTokenService jwtTokenService;

	public JwtAuthenticationFilter(JwtTokenService jwtTokenService) {
		this.jwtTokenService = jwtTokenService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authHeader = request.getHeader("Authorization");
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		String token = authHeader.substring(7);
		if (!jwtTokenService.tokenValido(token)) {
			filterChain.doFilter(request, response);
			return;
		}

		String email = jwtTokenService.extrairEmail(token);
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, null,
				Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));
		authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		filterChain.doFilter(request, response);
	}
}
