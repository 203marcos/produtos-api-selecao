package com.marcosdias.produtosapi;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcosdias.produtosapi.auth.AuthLoginRequestDTO;
import com.marcosdias.produtosapi.produto.dto.ProdutoRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ProdutoControllerSecurityIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void listarSemTokenRetorna401() throws Exception {
		mockMvc.perform(get("/api/produtos")).andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.status").value(401));
	}

	@Test
	void crudComTokenFunciona() throws Exception {
		String token = obterTokenValido();

		ProdutoRequestDTO criarRequest = new ProdutoRequestDTO();
		criarRequest.setNome("Teclado Mecanico");
		criarRequest.setPreco(new BigDecimal("399.90"));
		criarRequest.setDescricao("Teclado para jogos");
		criarRequest.setCategoria("Perifericos");

		MvcResult createResult = mockMvc
				.perform(post("/api/produtos").header("Authorization", "Bearer " + token)
						.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(criarRequest)))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.id").isNumber())
				.andExpect(jsonPath("$.nome").value("Teclado Mecanico")).andReturn();

		JsonNode createdJson = objectMapper.readTree(createResult.getResponse().getContentAsString());
		long id = createdJson.get("id").asLong();

		mockMvc.perform(get("/api/produtos/{id}", id).header("Authorization", "Bearer " + token))
				.andExpect(status().isOk()).andExpect(jsonPath("$.id").value(id));

		ProdutoRequestDTO atualizarRequest = new ProdutoRequestDTO();
		atualizarRequest.setNome("Teclado Mecanico RGB");
		atualizarRequest.setPreco(new BigDecimal("459.90"));
		atualizarRequest.setDescricao("Teclado para jogos com RGB");
		atualizarRequest.setCategoria("Perifericos");

		mockMvc.perform(put("/api/produtos/{id}", id).header("Authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(atualizarRequest)))
				.andExpect(status().isOk()).andExpect(jsonPath("$.nome").value("Teclado Mecanico RGB"));

		mockMvc.perform(delete("/api/produtos/{id}", id).header("Authorization", "Bearer " + token))
				.andExpect(status().isNoContent());
	}

	private String obterTokenValido() throws Exception {
		AuthLoginRequestDTO request = new AuthLoginRequestDTO();
		request.setEmail("admin@exemplo.com");
		request.setSenha("admin123");

		MvcResult result = mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))).andExpect(status().isOk()).andReturn();

		JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString());
		return json.get("token").asText();
	}
}
