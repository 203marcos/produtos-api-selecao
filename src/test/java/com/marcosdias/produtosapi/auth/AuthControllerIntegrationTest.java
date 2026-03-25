package com.marcosdias.produtosapi.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcosdias.produtosapi.auth.dto.AuthLoginRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void loginComCredenciaisValidasRetornaToken() throws Exception {
		AuthLoginRequestDTO request = new AuthLoginRequestDTO();
		request.setEmail("admin@exemplo.com");
		request.setSenha("admin123");

		mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))).andExpect(status().isOk())
				.andExpect(jsonPath("$.token").isNotEmpty()).andExpect(jsonPath("$.tipo").value("Bearer"));
	}

	@Test
	void loginComCredenciaisInvalidasRetorna401() throws Exception {
		AuthLoginRequestDTO request = new AuthLoginRequestDTO();
		request.setEmail("admin@exemplo.com");
		request.setSenha("senhaErrada");

		mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))).andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.mensagem").value("Email ou senha invalidos"));
	}
}

