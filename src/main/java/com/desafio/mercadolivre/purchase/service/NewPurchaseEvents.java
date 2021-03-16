package com.desafio.mercadolivre.purchase.service;

import java.util.Set;

import org.apache.commons.mail.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desafio.mercadolivre.external.PurchaseSuccessfullyEvent;
import com.desafio.mercadolivre.purchase.Purchase;

@Service
public class NewPurchaseEvents {

	@Autowired
	private Set<PurchaseSuccessfullyEvent> purchaseEvents;
	
	public void process(Purchase purchase) throws EmailException{
		if(purchase.successfullyProcessed()) {
			purchaseEvents.forEach(event -> {
				try {
					event.process(purchase);
				} catch (EmailException e) {
					e.printStackTrace();
				}
			});
		}
	}
}
