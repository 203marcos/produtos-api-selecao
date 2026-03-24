package com.marcosdias.produtosapi.mapper;

import com.marcosdias.produtosapi.dto.ProdutoRequestDTO;
import com.marcosdias.produtosapi.dto.ProdutoResponseDTO;
import com.marcosdias.produtosapi.model.Produto;
import org.springframework.stereotype.Component;

@Component
public class ProdutoMapper {

    public Produto toEntity(ProdutoRequestDTO requestDTO) {
        Produto produto = new Produto();
        produto.setNome(requestDTO.getNome());
        produto.setPreco(requestDTO.getPreco());
        produto.setDescricao(requestDTO.getDescricao());
        produto.setCategoria(requestDTO.getCategoria());
        return produto;
    }

    public ProdutoResponseDTO toResponse(Produto produto) {
        return new ProdutoResponseDTO(
                produto.getId(),
                produto.getNome(),
                produto.getPreco(),
                produto.getDescricao(),
                produto.getCategoria()
        );
    }

    public void updateEntity(ProdutoRequestDTO requestDTO, Produto produto) {
        produto.setNome(requestDTO.getNome());
        produto.setPreco(requestDTO.getPreco());
        produto.setDescricao(requestDTO.getDescricao());
        produto.setCategoria(requestDTO.getCategoria());
    }
}

