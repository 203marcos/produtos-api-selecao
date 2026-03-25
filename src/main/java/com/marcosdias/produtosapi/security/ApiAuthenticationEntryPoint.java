package com.marcosdias.produtosapi.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcosdias.produtosapi.exception.payload.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ApiAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private final ObjectMapper objectMapper;

	public ApiAuthenticationEntryPoint(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		ApiErrorResponse errorResponse = new ApiErrorResponse();
		errorResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
		errorResponse.setErro(HttpStatus.UNAUTHORIZED.getReasonPhrase());
		errorResponse.setMensagem("Acesso nao autenticado");
		errorResponse.setPath(request.getRequestURI());

		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		objectMapper.writeValue(response.getWriter(), errorResponse);
	}
}
