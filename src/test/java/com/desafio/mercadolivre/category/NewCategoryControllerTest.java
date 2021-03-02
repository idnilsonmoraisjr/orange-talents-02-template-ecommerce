package com.desafio.mercadolivre.category;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@Transactional
@ActiveProfiles("test")
public class NewCategoryControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper mapper;
	
	@Autowired
	EntityManager entityManager;

	@Test
	@DisplayName("Should create a category succesfully whithout parent category and return 200 ")
	public void shouldCreateCategorySuccessfully_WhenIdParentCategoryIsEmpty() throws Exception {
		NewCategoryPostRequest request = new NewCategoryPostRequest();
		request.setName("Test");
		
		mockMvc.perform(post("/categories")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(request)))
		.andExpect(status().isOk());
		
		List<Category> categories =  entityManager.createQuery("select c from Category c", Category.class).getResultList();
		
		assertTrue(categories.size() == 1);
		Category category = categories.get(0);
		
		assertEquals(request.getName(),category.getName());
	}
	
	@Test
	@DisplayName("Should create a category succesfully whit parent category and return 200")
	public void shouldCreateCategorySuccessfully_WhenIdParentCategoryIsPresent() throws Exception {
		NewCategoryPostRequest requestParent = new NewCategoryPostRequest();
		requestParent.setName("Parent");
		
		mockMvc.perform(post("/categories")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(requestParent)))
		.andExpect(status().isOk());
		
		NewCategoryPostRequest request = new NewCategoryPostRequest();
		request.setName("Test");
		request.setIdParentCategory(1L);
		
		mockMvc.perform(post("/categories")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(request)))
		.andExpect(status().isOk());
		
		List<Category> categories =  entityManager.createQuery("select c from Category c", Category.class).getResultList();
		
		assertTrue(categories.size() == 2);
		Category category = categories.get(1);
		
		assertEquals(request.getName(),category.getName());
		assertEquals(request.getCategoryParent().longValue(), 1L);
	}
	
	@Test
	@DisplayName("Should fail to create a category when category's name already exists and return 400")
	public void shouldFailCreateCategory_WhenNameAlreadyExists() throws Exception {
		NewCategoryPostRequest request = new NewCategoryPostRequest();
		request.setName("Test");
		
		Category category = request.toModel(entityManager);
		entityManager.persist(category);
		
		List<Category> categories =  entityManager.createQuery("select c from Category c", Category.class).getResultList();
		assertTrue(categories.size() == 1);
		assertEquals(request.getName(),category.getName());
		
		mockMvc.perform(post("/categories")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(request)))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	@DisplayName("Should fail to create a category when parent's id not exists")
	public void shouldFailCreateCategory_WhenParentNotExists() throws Exception {
		NewCategoryPostRequest requestParent = new NewCategoryPostRequest();
		requestParent.setName("Test");
		
		Category category = requestParent.toModel(entityManager);
		entityManager.persist(category);
		
		NewCategoryPostRequest request = new NewCategoryPostRequest();
		request.setName("Test");
		request.setIdParentCategory(2L);
	
		mockMvc.perform(post("/categories")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(request)))
		.andExpect(status().isBadRequest());
		
		assertEquals(requestParent.getName(),category.getName());
	}
	
	private String toJson(NewCategoryPostRequest request) throws JsonProcessingException {
		return mapper.writeValueAsString(request);
	}
}
