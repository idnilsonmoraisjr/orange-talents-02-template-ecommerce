package com.desafio.mercadolivre.product.controller;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.desafio.mercadolivre.product.Product;
import com.desafio.mercadolivre.product.ProductImpression;
import com.desafio.mercadolivre.product.request.NewProductImpressionRequest;
import com.desafio.mercadolivre.user.User;

@RestController
@RequestMapping("/products/{id}/impressions")
public class ProductImpressionsController {

	@PersistenceContext
	private EntityManager entityManager;
	
	@PostMapping
	@Transactional
	public ResponseEntity<?> addImpression(@PathVariable("id") Long id, 
			@RequestBody @Valid NewProductImpressionRequest request,  @AuthenticationPrincipal User user) {
	
		Optional<Product> optionalProduct = Optional.ofNullable(entityManager.find(Product.class, id));
		
		if(optionalProduct.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		optionalProduct.get().belongsToTheUser(user);
	
		ProductImpression impression = request.toModel(user, optionalProduct);
		entityManager.persist(impression);
		return ResponseEntity.ok().build();
	}
}
