package com.desafio.mercadolivre.product.response;

import com.desafio.mercadolivre.product.ProductImpression;

public class ProductImpressionResponseDTO {

	private Double grade;
	private String title;
	private String description;
	
	public ProductImpressionResponseDTO(ProductImpression productImpression) {
		this.grade = productImpression.getGrade();
		this.title = productImpression.getTitle();
		this.description = productImpression.getDescription();
	}
	
	
}
