package com.marcosdias.produtosapi.exception.handler;

import com.marcosdias.produtosapi.exception.payload.ApiErrorResponse;
import com.marcosdias.produtosapi.produto.exception.ResourceNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
	/*
	 * Utilizei @RestControllerAdvice para centralizar o tratamento de exceções em
	 * um único lugar. Benefícios:
	 * 
	 * 1. DRY (Don't Repeat Yourself): cada tipo de erro é tratado uma única vez
	 * 2. Consistência: todos os erros retornam no mesmo formato (ApiErrorResponse)
	 * 3. Manutenção: alterações no tratamento de erros afetam a API inteira
	 * 4. Separação de responsabilidades: lógica de erro fica fora dos controllers
	 * 
	 * Cada @ExceptionHandler mapeia um tipo de exceção para uma resposta HTTP
	 * apropriada com status e mensagem em PT-BR.
	 */

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiErrorResponse> handleResourceNotFound(ResourceNotFoundException ex,
			HttpServletRequest request) {
		ApiErrorResponse error = baseError(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiErrorResponse> handleValidation(MethodArgumentNotValidException ex,
			HttpServletRequest request) {
		ApiErrorResponse error = baseError(HttpStatus.BAD_REQUEST, "Erro de validacao", request.getRequestURI());

		Map<String, String> fields = new java.util.HashMap<>();
		for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
			fields.put(fieldError.getField(), fieldError.getDefaultMessage());
		}
		error.setCampos(fields);

		return ResponseEntity.badRequest().body(error);
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ApiErrorResponse> handleBadCredentials(BadCredentialsException ex,
			HttpServletRequest request) {
		ApiErrorResponse error = baseError(HttpStatus.UNAUTHORIZED, ex.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiErrorResponse> handleGeneric(Exception ex, HttpServletRequest request) {
		String message = ex.getMessage() == null ? "Erro interno no servidor" : ex.getMessage();
		ApiErrorResponse error = baseError(HttpStatus.INTERNAL_SERVER_ERROR, message, request.getRequestURI());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}

	private ApiErrorResponse baseError(HttpStatus status, String message, String path) {
		ApiErrorResponse error = new ApiErrorResponse();
		error.setStatus(status.value());
		error.setErro(status.getReasonPhrase());
		error.setMensagem(message);
		error.setPath(path);
		return error;
	}
}
