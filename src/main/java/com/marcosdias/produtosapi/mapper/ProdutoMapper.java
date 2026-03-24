package com.marcosdias.produtosapi.mapper;

import com.marcosdias.produtosapi.dto.ProdutoRequestDTO;
import com.marcosdias.produtosapi.dto.ProdutoResponseDTO;
import com.marcosdias.produtosapi.model.Produto;
import org.springframework.stereotype.Component;

@Component
public class ProdutoMapper {
    /*
         Escolhi também usar um ProdutoMapper para garantir a organização e
         separação de responsabilidades, evitando acoplamento entre as camadas
         da aplicação (Controller, Service e Model).

         Dessa forma, a conversão entre DTO e entidade fica centralizada,
         facilitando a manutenção e possíveis mudanças futuras.
    */

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

