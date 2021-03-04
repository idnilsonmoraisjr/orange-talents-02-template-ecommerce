package com.desafio.mercadolivre.product;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.desafio.mercadolivre.category.Category;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ProductsControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private EntityManager entityManager;
	
	private List<ProductAttributeRequest> productAttributes = createListOfProductAttributes();
	
	@Test
	@WithUserDetails("user6@email.com")
	@DisplayName("Should create a product succesfully and return status 200")
	void shouldCreateANewProduct() throws JsonProcessingException, Exception {
		
		Category productCategory = new Category("TEST");
		entityManager.persist(productCategory);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/products")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(new NewProductPostRequest(
						"Headphone", new BigDecimal("150"), 15, productAttributes, "A simple description",
						1L)))
				).andExpect(
						status().isOk()
				);
	}
	
	@Test
	@DisplayName("Should fail at create a product when user is unauthorized return status 403")
	void shouldFailCreateANewProduct_WhenUserIsUnauthorized() throws JsonProcessingException, Exception {
		
		Category productCategory = new Category("TEST");
		entityManager.persist(productCategory);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/products")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(new NewProductPostRequest(
						"Headphone", new BigDecimal("150"), 15, productAttributes, "A simple description",
						 1L)))
				).andExpect(
						status().isForbidden()
				);
	}

	private List<ProductAttributeRequest> createListOfProductAttributes() {
		List<ProductAttributeRequest> productAttributes = new ArrayList<ProductAttributeRequest>();
		productAttributes.add(new ProductAttributeRequest("Brand" , "JBL"));
		productAttributes.add(new ProductAttributeRequest("Model" , "Overhead"));
		productAttributes.add(new ProductAttributeRequest("QUANTITY PER PACKAGE" , "1"));
		
		return productAttributes;
	}
}
