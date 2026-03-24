package com.marcosdias.produtosapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome do produto e obrigatorio")
    @Column(nullable = false, length = 120)
    private String nome;

    @NotNull(message = "Preco do produto e obrigatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "Preco deve ser maior que zero")
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal preco;

    @NotBlank(message = "Descricao do produto e obrigatoria")
    @Column(nullable = false, length = 500)
    private String descricao;

    @NotBlank(message = "Categoria do produto e obrigatoria")
    @Column(nullable = false, length = 80)
    private String categoria;

    public Produto() {
    }

    public Produto(Long id, String nome, BigDecimal preco, String descricao, String categoria) {
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

