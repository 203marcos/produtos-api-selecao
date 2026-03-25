package com.marcosdias.produtosapi.auth.dto;

public class AuthLoginResponseDTO {

	private String token;
	private String tipo;

	public AuthLoginResponseDTO(String token) {
		this.token = token;
		this.tipo = "Bearer";
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}

