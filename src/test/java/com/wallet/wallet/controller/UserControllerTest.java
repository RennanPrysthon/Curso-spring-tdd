package com.wallet.wallet.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wallet.wallet.dto.UserDTO;
import com.wallet.wallet.entity.User;
import com.wallet.wallet.services.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class UserControllerTest {
	
	private static final Long ID = 1L;
	private static final String EMAIL = "email@teste.com";
	private static final String NAME = "User_tes";
	private static final String PASSWORD = "1234567";
	private static final String URL = "/user";
	
	@MockBean
	UserService service;
	
	@Autowired
	MockMvc mvc;
	
	@Test
	public void testSave() throws Exception {
		
		BDDMockito.given(service.save(Mockito.any(User.class))).willReturn(getMockUser());
		
		mvc.perform(MockMvcRequestBuilders.post(URL).content(getJsonPayload(ID, EMAIL, NAME, PASSWORD))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isCreated())
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(ID))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.email").value(EMAIL))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value(NAME))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.password").value(PASSWORD));
	}

	@Test
	public void testSaveInvalidUser() throws JsonProcessingException, Exception {
		mvc.perform(MockMvcRequestBuilders.post(URL).content(getJsonPayload(ID, "email", NAME, PASSWORD))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andExpect(MockMvcResultMatchers.jsonPath("$.erros[0]").value("Email invalido"));
	}
	
	
	public User getMockUser() {
		User u = new User();
		u.setId(ID);
		u.setName(NAME);
		u.setEmail(EMAIL);
		u.setPassword(PASSWORD);
		
		return u;
	}
	
	public String getJsonPayload(Long id, String email, String nome, String password) throws JsonProcessingException {
		
		UserDTO dto = new UserDTO();
		dto.setId(id);
		dto.setName(nome);
		dto.setEmail(email);
		dto.setPassword(password);
		
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(dto);
	}
}
