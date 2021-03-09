package com.desafio.mercadolivre.product.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.desafio.mercadolivre.product.Product;
import com.desafio.mercadolivre.product.ProductQuestion;
import com.desafio.mercadolivre.product.mail.Mail;
import com.desafio.mercadolivre.product.request.NewProductQuestionRequest;
import com.desafio.mercadolivre.product.response.ProductQuestionsResponseDTO;
import com.desafio.mercadolivre.user.User;

@RestController
@RequestMapping("/products/{id}/questions")
public class ProductQuestionsController {
	
	@Autowired
	private Mail mail;
	@PersistenceContext
	EntityManager entityManager;
	
	@PostMapping
	@Transactional
	public ResponseEntity<?> askQuestion(@RequestBody @Valid NewProductQuestionRequest request,
			@PathVariable("id") Long id, @AuthenticationPrincipal User user) {

		Optional<Product> product = Optional.ofNullable(entityManager.find(Product.class, id));
		Assert.state(product.isPresent(), "Product not found!");
		
		ProductQuestion question = request.toModel(user, product.get());
		entityManager.persist(question);
		
		mail.notifyUser(question);
		return new ResponseEntity<>(thisProductQuestions(product), HttpStatus.OK);
	}
	
	public List<ProductQuestionsResponseDTO> thisProductQuestions (Optional<Product> product) {
		 List<ProductQuestionsResponseDTO> thisProductQuestions = product.get()
				.getQuestions()
				.stream()
				.map(q -> new ProductQuestionsResponseDTO(q))
				.collect(Collectors.toList());
		 
		 return thisProductQuestions;
	}
}
