package com.desafio.mercadolivre.product.controller;

import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.desafio.mercadolivre.product.Product;
import com.desafio.mercadolivre.product.Uploader;
import com.desafio.mercadolivre.product.request.NewProductImageRequest;
import com.desafio.mercadolivre.user.User;

@RestController
@RequestMapping("/products/{id}/images")
public class ProductImagesController {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private Uploader uploaderTest;
	
	@PostMapping
	@Transactional
	public ResponseEntity<?> addImage(@PathVariable("id") Long id, @Valid NewProductImageRequest request,  @AuthenticationPrincipal User user) {
		
		Set<String> links = uploaderTest.send(request.getImages());
		Optional<Product> optionalProduct = Optional.ofNullable(entityManager.find(Product.class, id));
		if(optionalProduct.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		optionalProduct.get().belongsToTheUser(user);
		optionalProduct.get().checkImages(links);
		
		entityManager.merge(optionalProduct.get());
		
		return ResponseEntity.ok().build();
	}
}
