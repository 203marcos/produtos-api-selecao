package com.marcosdias.produtosapi.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ProdutoRequestDTO {

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

