package com.desafio.mercadolivre.product.response;

import com.desafio.mercadolivre.product.ProductAttribute;

public class ProductAttributeResponseDTO {

	private String name;
	private String description;
	
	public ProductAttributeResponseDTO(ProductAttribute productAttribute) {
		this.name = productAttribute.getName();
		this.description = productAttribute.getDescription();
	}
}
