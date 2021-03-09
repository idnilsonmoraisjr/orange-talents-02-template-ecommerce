package com.desafio.mercadolivre.user;


import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@Transactional
@ActiveProfiles("test")
class UsersControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper mapper;
	
	@Autowired
	EntityManager entityManager;
	
	@Test
	@DisplayName("Should create a user succesfully and return status 200")
	public void shouldCreateUserSuccessfully() throws Exception {
		NewUserRequest request = new NewUserRequest("login@email.com", "123456");
		
		mockMvc.perform(post("/users")
						.contentType(MediaType.APPLICATION_JSON)
						.content(toJson(request)))
		.andExpect(status().isOk());
		
		List<User> users =  entityManager.createQuery("select u from User u", User.class).getResultList();
		
		for(User user : users) {
			System.out.println(user);
		}
		
		User user = users.get(1);
		
		assertEquals(request.getLogin(),user.getLogin());
	}
	
	@Test
	@DisplayName("Shouldn't create a user when email already exists and return status 400")
	public void shouldntCreateUser_WhenEmailAlreadyExists() throws Exception {
		NewUserRequest request = new NewUserRequest("login@email.com", "123456");
		
		User user = request.toModel();
		entityManager.persist(user);
		
		mockMvc.perform(post("/users")
						.contentType(MediaType.APPLICATION_JSON)
						.content(toJson(request)))
		.andExpect(status().isBadRequest());
	}
	
	private String toJson(NewUserRequest request) throws JsonProcessingException {
		return mapper.writeValueAsString(request);
	}
}
