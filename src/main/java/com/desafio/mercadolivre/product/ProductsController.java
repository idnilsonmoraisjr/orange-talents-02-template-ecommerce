package com.desafio.mercadolivre.product;

import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.desafio.mercadolivre.shared.UniqueProductAttributeValidator;
import com.desafio.mercadolivre.user.User;

@RestController
@RequestMapping("/products")
public class ProductsController {

	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private Uploader uploaderTest;
	
	@InitBinder(value = "newProductRequest")
	public void init(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(new UniqueProductAttributeValidator());
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<NewProductResponseDTO> create(@RequestBody @Valid NewProductPostRequest request) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Product newProduct = request.toModel(entityManager, user);
		entityManager.persist(newProduct);
		return ResponseEntity.ok(new NewProductResponseDTO(newProduct));
	}
	
	@PostMapping("/{id}/images")
	@Transactional
	public ResponseEntity<?> addImage(@PathVariable("id") Long id, @Valid NewProductImageRequest request) {
		
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 
		Set<String> links = uploaderTest.send(request.getImages());
		Optional<Product> product = Optional.ofNullable(entityManager.find(Product.class, id));
		Assert.state(product.isPresent(), "Product not found!");
		product.get().checkProductOwner(user);
		product.get().checkImages(links);
		
		entityManager.merge(product.get());
		
		return ResponseEntity.ok().build();
	}
}
