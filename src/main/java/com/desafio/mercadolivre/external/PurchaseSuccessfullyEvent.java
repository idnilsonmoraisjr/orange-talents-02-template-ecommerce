package com.desafio.mercadolivre.external;

import org.apache.commons.mail.EmailException;

import com.desafio.mercadolivre.purchase.Purchase;

public interface PurchaseSuccessfullyEvent {
	
	void process(Purchase purchase) throws EmailException;
}
