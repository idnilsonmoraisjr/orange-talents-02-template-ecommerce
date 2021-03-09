package com.desafio.mercadolivre.product.mail;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desafio.mercadolivre.product.ProductQuestion;

@Service
public class Mail {

	@Autowired
	private Mailer mailer;
	
	public void notifyUser(@NotNull @Valid ProductQuestion question) {
		 mailer.send("You have a new question for your product:",
				 question.getTitle(), question.getUser().getUsername(),
				question.getUser().getLogin(),
				question.getProductOwner().toString());
	}

}
