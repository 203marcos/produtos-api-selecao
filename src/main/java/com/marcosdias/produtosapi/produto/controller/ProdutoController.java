package com.marcosdias.produtosapi.produto.controller;

import com.marcosdias.produtosapi.produto.dto.ProdutoRequestDTO;
import com.marcosdias.produtosapi.produto.dto.ProdutoResponseDTO;
import com.marcosdias.produtosapi.produto.service.ProdutoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/produtos")
@Validated
@PreAuthorize("isAuthenticated()")
public class ProdutoController {
	/*
	 * Utilizei @PreAuthorize("isAuthenticated()") na classe para proteger TODOS os
	 * endpoints dessa classe. Qualquer requisição sem um token JWT válido retornará
	 * 401 (Unauthorized).
	 * 
	 * Dessa forma, a segurança é garantida de forma declarativa, sem necessidade de
	 * adicionar verificações manuais em cada método.
	 *
	 * Exemplos rapidos (curl):
	 * - Criar:   curl -X POST http://localhost:8080/api/produtos -H "Authorization: Bearer SEU_TOKEN" -H "Content-Type: application/json" -d "{\"nome\":\"Mouse\",\"preco\":99.9,\"descricao\":\"Mouse gamer\",\"categoria\":\"Perifericos\"}"
	 * - Listar:  curl -X GET http://localhost:8080/api/produtos -H "Authorization: Bearer SEU_TOKEN"
	 * - Buscar:  curl -X GET http://localhost:8080/api/produtos/1 -H "Authorization: Bearer SEU_TOKEN"
	 * - Atualizar: curl -X PUT http://localhost:8080/api/produtos/1 -H "Authorization: Bearer SEU_TOKEN" -H "Content-Type: application/json" -d "{\"nome\":\"Mouse Pro\",\"preco\":129.9,\"descricao\":\"Mouse gamer RGB\",\"categoria\":\"Perifericos\"}"
	 * - Deletar: curl -X DELETE http://localhost:8080/api/produtos/1 -H "Authorization: Bearer SEU_TOKEN"
	 */

	private final ProdutoService produtoService;

	public ProdutoController(ProdutoService produtoService) {
		this.produtoService = produtoService;
	}

	@PostMapping
	public ResponseEntity<ProdutoResponseDTO> criar(@Valid @RequestBody ProdutoRequestDTO requestDTO) {
		ProdutoResponseDTO criado = produtoService.criar(requestDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(criado);
	}

	@GetMapping
	public ResponseEntity<List<ProdutoResponseDTO>> listarTodos() {
		return ResponseEntity.ok(produtoService.listarTodos());
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProdutoResponseDTO> buscarPorId(@PathVariable Long id) {
		return ResponseEntity.ok(produtoService.buscarPorId(id));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProdutoResponseDTO> atualizar(@PathVariable Long id,
			@Valid @RequestBody ProdutoRequestDTO requestDTO) {
		return ResponseEntity.ok(produtoService.atualizar(id, requestDTO));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		produtoService.deletar(id);
		return ResponseEntity.noContent().build();
	}
}
