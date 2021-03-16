package com.desafio.mercadolivre.purchase.controller;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import org.apache.commons.mail.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.desafio.mercadolivre.purchase.Purchase;
import com.desafio.mercadolivre.purchase.request.PagSeguroRequest;
import com.desafio.mercadolivre.purchase.request.PaypalRequest;
import com.desafio.mercadolivre.purchase.response.PaymentMethodResponse;
import com.desafio.mercadolivre.purchase.response.PaymentResponseDTO;
import com.desafio.mercadolivre.purchase.service.NewPurchaseEvents;

@RestController
public class ClosePurchasesController {

	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	private NewPurchaseEvents newPurchaseEvents;
	
	@PostMapping(value = "/return-pagseguro/{id}")
	@Transactional
	public ResponseEntity<?> pagSeguroProcessing(@PathVariable("id") Long purchaseId, @Valid PagSeguroRequest request) throws EmailException {
		return process(purchaseId, request);
	}

	@PostMapping(value = "/return-paypal/{id}")
	@Transactional
	public ResponseEntity<?> paypalProcessing(@PathVariable("id") Long purchaseId, @Valid PaypalRequest request) throws EmailException {
		return process(purchaseId, request);
	}

	private ResponseEntity<PaymentResponseDTO> process(Long purchaseId, PaymentMethodResponse request) throws EmailException {
		Optional<Purchase> purchase = Optional.ofNullable(entityManager.find(Purchase.class, purchaseId));
		if (purchase.isPresent()) {
			purchase.get().addPayment(request);
			entityManager.merge(purchase.get());
			newPurchaseEvents.process(purchase.get());
			
			return ResponseEntity.ok(new PaymentResponseDTO(purchase.get()));
		}
		return ResponseEntity.badRequest().build();
	}
}
