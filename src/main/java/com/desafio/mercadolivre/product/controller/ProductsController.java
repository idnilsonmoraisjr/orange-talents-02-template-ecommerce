package com.desafio.mercadolivre.product.controller;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.desafio.mercadolivre.product.Product;
import com.desafio.mercadolivre.product.request.NewProductRequest;
import com.desafio.mercadolivre.product.response.NewProductResponseDTO;
import com.desafio.mercadolivre.shared.UniqueProductAttributeValidator;
import com.desafio.mercadolivre.user.User;

@RestController
@RequestMapping("/products")
public class ProductsController {

	@PersistenceContext
	private EntityManager entityManager;

	@InitBinder(value = "newProductRequest")
	public void init(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(new UniqueProductAttributeValidator());
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<NewProductResponseDTO> create(@RequestBody @Valid NewProductRequest request,
			@AuthenticationPrincipal User user) {
		Product newProduct = request.toModel(entityManager, user);
		entityManager.persist(newProduct);
		return ResponseEntity.ok(new NewProductResponseDTO(newProduct));
	}
}
