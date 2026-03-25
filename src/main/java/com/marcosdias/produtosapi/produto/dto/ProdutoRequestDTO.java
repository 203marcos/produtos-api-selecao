package com.marcosdias.produtosapi.produto.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ProdutoRequestDTO {
	/*
	 * Bom, escolhi utilizar uma classe DTO tradicional em vez de record, pois o
	 * projeto utiliza Spring Boot 2 e Java 17, e optei por priorizar abordagem mais
	 * comum em projetos reais, garantindo maior clareza e previsibilidade.
	 * 
	 * Além disso, evitei usar o Lombok, pra seguir a risca ao que foi pedido no
	 * teste, e tambem para deixar o código mais explicito e facil de entender.
	 */

	@NotBlank(message = "Nome do produto e obrigatorio")
	private String nome;

	@NotNull(message = "Preco do produto e obrigatorio")
	@DecimalMin(value = "0.0", inclusive = false, message = "Preco deve ser maior que zero")
	private BigDecimal preco;

	@NotBlank(message = "Descricao do produto e obrigatoria")
	private String descricao;

	@NotBlank(message = "Categoria do produto e obrigatoria")
	private String categoria;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
}
