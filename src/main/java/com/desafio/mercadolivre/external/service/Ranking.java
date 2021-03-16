package com.desafio.mercadolivre.external.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import com.desafio.mercadolivre.external.PurchaseSuccessfullyEvent;
import com.desafio.mercadolivre.purchase.Purchase;

@Service
public class Ranking implements PurchaseSuccessfullyEvent {

	@Override
	public void process(Purchase purchase) {
		Assert.isTrue(purchase.successfullyProcessed(), "This purchase has not been processed!");
		
		RestTemplate restTemplate = new RestTemplate();
		Map<String, Object> request = Map.of("purchaseId", purchase.getId(),
				"productOwnerId", purchase.getProductOwner().getId());
	
		restTemplate.postForEntity("http://localhost:8080/rankings", request, String.class);
	}
}


