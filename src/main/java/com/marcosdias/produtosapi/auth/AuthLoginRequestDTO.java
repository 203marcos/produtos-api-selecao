package com.marcosdias.produtosapi.auth;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class AuthLoginRequestDTO {

	@NotBlank(message = "Email e obrigatorio")
	@Email(message = "Email invalido")
	private String email;

	@NotBlank(message = "Senha e obrigatoria")
	private String senha;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}
