package com.marcosdias.produtosapi.dto;

import java.math.BigDecimal;

public class ProdutoResponseDTO {
    /*
        Bom, escolhi utilizar uma classe DTO tradicional em vez de record,
        pois o projeto utiliza Spring Boot 2 e Java 17, e optei por priorizar abordagem mais comum em projetos reais,
        garantindo maior clareza e previsibilidade.

        Além disso, evitei usar o Lombok, pra seguir a risca ao que foi pedido no teste,
        e tambem para deixar o código mais explicito e facil de entender.
     */

    private Long id;
    private String nome;
    private BigDecimal preco;
    private String descricao;
    private String categoria;

    public ProdutoResponseDTO() {
    }

    public ProdutoResponseDTO(Long id, String nome, BigDecimal preco, String descricao, String categoria) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
        this.categoria = categoria;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

