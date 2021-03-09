package com.desafio.mercadolivre.product.response;

import com.desafio.mercadolivre.product.ProductQuestion;

public class ProductQuestionsResponseDTO {

	private static final String FORMAT = "dd/MM/yyyy HH:mm:ss";
	
	private String title;
	private String creationMoment;
	private String usernameWhoAsked;
	private String questionProduct;
		
	public ProductQuestionsResponseDTO(ProductQuestion question) {
		this.title = question.getTitle();
		this.creationMoment = question.formatCreationMoment(FORMAT);
		this.usernameWhoAsked = question.getUser().getUsername();
		this.questionProduct = question.getProduct().getName();
	}

	public String getTitle() {
		return title;
	}

	public String getCreationMoment() {
		return creationMoment;
	}

	public String getUserWhoAsked() {
		return usernameWhoAsked;
	}

	public String getQuestionProduct() {
		return questionProduct;
	}
	
	
}
