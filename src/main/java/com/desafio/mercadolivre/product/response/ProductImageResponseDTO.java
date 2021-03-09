package com.desafio.mercadolivre.product.response;

import com.desafio.mercadolivre.product.ProductImage;

public class ProductImageResponseDTO {

	private String link;

	public ProductImageResponseDTO(ProductImage productImage) {
		this.link = productImage.getLink();
	}
}
