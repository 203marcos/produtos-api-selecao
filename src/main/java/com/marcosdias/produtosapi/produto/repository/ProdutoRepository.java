package com.marcosdias.produtosapi.produto.repository;

import com.marcosdias.produtosapi.produto.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
