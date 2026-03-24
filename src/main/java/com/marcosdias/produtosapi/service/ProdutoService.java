package com.marcosdias.produtosapi.service;

import com.marcosdias.produtosapi.dto.ProdutoRequestDTO;
import com.marcosdias.produtosapi.dto.ProdutoResponseDTO;
import com.marcosdias.produtosapi.exception.ResourceNotFoundException;
import com.marcosdias.produtosapi.mapper.ProdutoMapper;
import com.marcosdias.produtosapi.model.Produto;
import com.marcosdias.produtosapi.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ProdutoMapper produtoMapper;

    public ProdutoService(ProdutoRepository produtoRepository, ProdutoMapper produtoMapper) {
        this.produtoRepository = produtoRepository;
        this.produtoMapper = produtoMapper;
    }

    public ProdutoResponseDTO criar(ProdutoRequestDTO requestDTO) {
        Produto produto = produtoMapper.toEntity(requestDTO);
        produto.setId(null);
        Produto salvo = produtoRepository.save(produto);
        return produtoMapper.toResponse(salvo);
    }

    public List<ProdutoResponseDTO> listarTodos() {
        return produtoRepository.findAll()
                .stream()
                .map(produtoMapper::toResponse)
                .collect(Collectors.toList());
    }

    public ProdutoResponseDTO buscarPorId(Long id) {
        Produto produto = buscarEntidadePorId(id);
        return produtoMapper.toResponse(produto);
    }

    public ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO requestDTO) {
        Produto existente = buscarEntidadePorId(id);
        produtoMapper.updateEntity(requestDTO, existente);
        Produto atualizado = produtoRepository.save(existente);
        return produtoMapper.toResponse(atualizado);
    }

    public void deletar(Long id) {
        Produto produto = buscarEntidadePorId(id);
        produtoRepository.delete(produto);
    }

    private Produto buscarEntidadePorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto nao encontrado com id: " + id));
    }
}
