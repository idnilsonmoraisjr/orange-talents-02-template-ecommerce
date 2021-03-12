package com.desafio.mercadolivre.purchase.controller;


import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import com.desafio.mercadolivre.product.Product;
import com.desafio.mercadolivre.product.mail.Mail;
import com.desafio.mercadolivre.purchase.Purchase;
import com.desafio.mercadolivre.purchase.request.NewPurchaseRequest;
import com.desafio.mercadolivre.user.User;

@RestController
@RequestMapping("/purchases")
public class PurchasesController {

	@Autowired
	private Mail mail;
	
	@PersistenceContext
	EntityManager entityManager;
	
	@PostMapping("/product/{id}")
	@Transactional
	public ResponseEntity<?> makePurchase(@PathVariable("id") Long id,
			@RequestBody @Valid NewPurchaseRequest request, @AuthenticationPrincipal User user, UriComponentsBuilder uriComponentsBuilder) {
		Optional<Product> optionalProduct = Optional.ofNullable(entityManager.find(Product.class, id));
		
		if(optionalProduct.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Purchase purchase = request.toModel(user, optionalProduct.get()); 
		int quantity = purchase.getQuantity(); 
		boolean decreasedStock = optionalProduct.get().descreasesStock(quantity);
		
		if(decreasedStock) {
			entityManager.persist(purchase);
			mail.notifyOwnerPurchase(purchase);
			
			String redirectToPaymentUrl = purchase.generatedRedirectUrl(uriComponentsBuilder);

			return ResponseEntity.ok(redirectToPaymentUrl);
		}
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient quantity in stock for this product");
	}
}
