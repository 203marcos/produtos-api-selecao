package com.marcosdias.produtosapi.exception.payload;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrorResponse {
	/*
	 * Escolhi criar uma classe dedicada para respostas de erro em vez de usar um
	 * Map genérico. Isso garante:
	 * 
	 * 1. Consistência: todos os erros seguem a mesma estrutura (timestamp, status,
	 *    erro, mensagem, path, campos)
	 * 2. Type Safety: IDE e compilador detectam erros em tempo de desenvolvimento
	 * 3. Documentação: Swagger/OpenAPI mapeia automaticamente os campos
	 * 4. Manutenção: alterações futuras são mais seguras e rastreáveis
	 * 
	 * @JsonInclude(NON_NULL) garante que campos vazios não apareçam no JSON,
	 * mantendo a resposta limpa e sem ruído.
	 */

	private LocalDateTime timestamp;
	private int status;
	private String erro;
	private String mensagem;
	private String path;
	private Map<String, String> campos;

	public ApiErrorResponse() {
		this.timestamp = LocalDateTime.now();
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getErro() {
		return erro;
	}

	public void setErro(String erro) {
		this.erro = erro;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Map<String, String> getCampos() {
		return campos;
	}

	public void setCampos(Map<String, String> campos) {
		this.campos = campos;
	}
}
