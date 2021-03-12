package com.desafio.mercadolivre.product.response;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.desafio.mercadolivre.category.CategoryResponseDTO;
import com.desafio.mercadolivre.product.Product;

public class ProductResponseDTO {

	private static final String FORMAT = "dd/MM/yyyy HH:mm:ss";
	
	private Long id;
	private String name;
	private BigDecimal price;
	private int quantity;
	private Set<ProductAttributeResponseDTO> productAttributes;
	private String description;
	private CategoryResponseDTO productCategory;
	private String creationMoment;
	private Set<ProductImageResponseDTO> images = new HashSet<>();	
	
	public ProductResponseDTO(Product product) {
		this.id = product.getId();
		this.name = product.getName();
		this.price = product.getPrice();
		this.quantity = product.getQuantity();
		this.productAttributes.addAll(product.getProductAttributes()
				.stream()
				.map(attribute -> new ProductAttributeResponseDTO(attribute))
			.collect(Collectors.toSet()));
		this.description = product.getDescription();
		this.productCategory = new CategoryResponseDTO(product.getProductCategory());
		this.creationMoment = product.formatCreationMoment(FORMAT);
		this.images.addAll(product.getImage()
				.stream()
				.map(image -> new ProductImageResponseDTO(image))
			.collect(Collectors.toSet()));
	}


	public Long getId() {
		return id;
	}


	public String getName() {
		return name;
	}


	public BigDecimal getPrice() {
		return price;
	}


	public int getQuantity() {
		return quantity;
	}


	public Set<ProductAttributeResponseDTO> getProductAttributes() {
		return productAttributes;
	}


	public String getDescription() {
		return description;
	}


	public CategoryResponseDTO getProductCategory() {
		return productCategory;
	}


	public String getCreationMoment() {
		return creationMoment;
	}


	public Set<ProductImageResponseDTO> getImages() {
		return images;
	}
}
