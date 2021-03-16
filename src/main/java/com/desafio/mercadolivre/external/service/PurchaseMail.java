package com.desafio.mercadolivre.external.service;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.desafio.mercadolivre.external.PurchaseSuccessfullyEvent;
import com.desafio.mercadolivre.purchase.Purchase;

@Service
public class PurchaseMail implements PurchaseSuccessfullyEvent {

	@Override
	public void process(Purchase purchase) throws EmailException {
		Assert.isTrue(purchase.successfullyProcessed(), "This purchase has not been processed!");

		String productOwnerLogin = purchase.getProductOwner().getLogin();
		String productOwnerPassword = purchase.getProductOwner().getPassword();
		String productBuyerLogin = purchase.getBuyer().getLogin();

		SimpleEmail email = new SimpleEmail();
		email.setHostName("smtp.gmail.com");
		email.setSmtpPort(465);
		email.setAuthenticator(new DefaultAuthenticator(productOwnerLogin, productOwnerPassword));
		email.setSSLOnConnect(true);

		if (!purchase.successfullyProcessed()) {

			email.setFrom(productOwnerLogin);
			email.setSubject("[PURCHASE CONFIRMATION]");
			email.setMsg("Unfortunately, an error occurred while completing the purchase of your product."
					+ " Try again later!\n" + purchase.getProduct().getName() + "\n"
					+ purchase.getProduct().getDescription());
			email.addTo(productBuyerLogin);
			email.send();
			System.out.println("Email successfully sent!");
		}

		email.setFrom(productOwnerLogin);
		email.setSubject("[PURCHASE CONFIRMATION]");
		email.setMsg("Great news! Your purchase has been successfully confirmed!\n"
				+ "Soon you will be able to enjoy your product " + purchase.getProduct().getName() + "\n"
				+ purchase.getProduct().getDescription());
		email.addTo(productBuyerLogin);
		email.send();
		System.out.println("Email successfully sent!");
	}
}
