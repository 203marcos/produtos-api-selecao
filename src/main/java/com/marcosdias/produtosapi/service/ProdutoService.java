package com.marcosdias.produtosapi.service;

import com.marcosdias.produtosapi.exception.ResourceNotFoundException;
import com.marcosdias.produtosapi.model.Produto;
import com.marcosdias.produtosapi.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Produto criar(Produto produto) {
        produto.setId(null);
        return produtoRepository.save(produto);
    }

    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto nao encontrado com id: " + id));
    }

    public Produto atualizar(Long id, Produto produtoAtualizado) {
        Produto existente = buscarPorId(id);
        existente.setNome(produtoAtualizado.getNome());
        existente.setPreco(produtoAtualizado.getPreco());
        existente.setDescricao(produtoAtualizado.getDescricao());
        existente.setCategoria(produtoAtualizado.getCategoria());
        return produtoRepository.save(existente);
    }

    public void deletar(Long id) {
        Produto produto = buscarPorId(id);
        produtoRepository.delete(produto);
    }
}

