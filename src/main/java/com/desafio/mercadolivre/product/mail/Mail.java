package com.desafio.mercadolivre.product.mail;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desafio.mercadolivre.product.ProductQuestion;
import com.desafio.mercadolivre.purchase.Purchase;

@Service
public class Mail {

	@Autowired
	private Mailer mailer;
	
	public void notifyUserQuestion(@NotNull @Valid ProductQuestion question) {
		 mailer.send("You have a new question for your product:",
				 question.getTitle(),
				 question.getUser().getUsername(),
				 question.getUser().getLogin(),
				 question.getProductOwner().toString());
	}

	public void notifyOwnerPurchase(@NotNull @Valid Purchase purchase) {
		 mailer.send("The buyer is interested in your product and wants to make the purchase",
				 purchase.getProduct().getName(),
				 purchase.getBuyer().getUsername(),
				 purchase.getBuyer().getLogin(),
				 purchase.getProduct().getUser().getLogin());
	}
}
