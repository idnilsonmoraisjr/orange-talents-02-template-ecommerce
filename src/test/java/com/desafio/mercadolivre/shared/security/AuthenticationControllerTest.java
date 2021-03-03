package com.desafio.mercadolivre.shared.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.desafio.mercadolivre.user.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@Transactional
@ActiveProfiles("test")
public class AuthenticationControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper mapper;
	
	@Autowired
	EntityManager entityManager;
	
	@Autowired
	AuthenticationManager authManager;
	
	@Test
	@DisplayName("Should authenticate a user and return status 200")
	public void shouldAuthenticateSucessfully_WhenCredentialsAreValid() throws Exception {
		URI uri = new URI("/auth");
		LoginForm request = new LoginForm();
		request.setLogin("login@email.com");
		request.setPassword("123456");
		
		mockMvc.perform(post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(request)))
		.andExpect(status().isOk());
		
		List<User> users =  entityManager.createQuery("select u from User u", User.class).getResultList();
		
		assertTrue(users.size() == 1);
		User user = users.get(0);
		assertEquals(request.getLogin(),user.getLogin());
		
		mockMvc.perform(MockMvcRequestBuilders
					.post(uri)
					.content(toJson(request))
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers
					.status().isOk());
	}
	@Test
	@DisplayName("Should fail at authenticate a user and return status 400")
	public void shouldFailAuthenticateUser_WhenCredentialsAreInvalid() throws Exception {
		URI uri = new URI("/auth");
		LoginForm request = new LoginForm();
		request.setLogin("unknow@email.com");
		request.setPassword("123456");
		
		mockMvc.perform(MockMvcRequestBuilders
					.post(uri)
					.content(toJson(request))
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers
					.status().isBadRequest());
	}
	
	private String toJson(LoginForm request) throws JsonProcessingException {
		return mapper.writeValueAsString(request);
	}
}
