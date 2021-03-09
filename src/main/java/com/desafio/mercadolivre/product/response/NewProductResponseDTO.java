package com.desafio.mercadolivre.product.response;

import java.math.BigDecimal;
import java.util.Set;

import com.desafio.mercadolivre.category.CategoryResponseDTO;
import com.desafio.mercadolivre.product.Product;
import com.desafio.mercadolivre.product.ProductAttribute;

public class NewProductResponseDTO {

	private static final String FORMAT = "dd/MM/yyyy HH:mm:ss";
	
	private Long id;
	private String name;
	private BigDecimal price;
	private int amount;
	private Set<ProductAttribute> productAttributes;
	private String description;
	private CategoryResponseDTO productCategory;
	private String creationMoment;
	
	public NewProductResponseDTO(Product product) {
		this.id = product.getId();
		this.name = product.getName();
		this.price = product.getPrice();
		this.amount = product.getAmount();
		this.productAttributes = product.getProductAttributes();
		this.description = product.getDescription();
		this.productCategory = new CategoryResponseDTO(product.getProductCategory());
		this.creationMoment = product.formatCreationMoment(FORMAT);
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

	public int getAmount() {
		return amount;
	}

	public Set<ProductAttribute> getProductAttributes() {
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

	@Override
	public String toString() {
		return "NewProductResponseDTO [id=" + id + ", name=" + name + ", price=" + price + ", amount=" + amount
				+ ", productAttributes=" + productAttributes + ", description=" + description + ", productCategory="
				+ productCategory + ", creationMoment=" + creationMoment + "]";
	}
}
