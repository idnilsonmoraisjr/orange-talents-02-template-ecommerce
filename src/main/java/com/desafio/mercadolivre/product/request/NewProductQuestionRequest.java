package com.desafio.mercadolivre.product.request;

import javax.validation.constraints.NotBlank;

import com.desafio.mercadolivre.product.Product;
import com.desafio.mercadolivre.product.ProductQuestion;
import com.desafio.mercadolivre.user.User;

public class NewProductQuestionRequest {

	@NotBlank
	private String title;

	public NewProductQuestionRequest() {}

	public void setTitle(String title) {
		this.title = title;
	}

	public ProductQuestion toModel(User user, Product product) {
		
		return new ProductQuestion(this.title, user, product);
	}
	
	
}
