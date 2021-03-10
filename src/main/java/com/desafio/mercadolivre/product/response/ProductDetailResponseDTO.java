package com.desafio.mercadolivre.product.response;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import com.desafio.mercadolivre.product.Product;

public class ProductDetailResponseDTO {
	
	private Set<ProductImageResponseDTO> images;
	private String name;
	private BigDecimal price;
	private Set<ProductAttributeResponseDTO> productAttributes;
	private String description;
	private List<ProductImpressionResponseDTO> productImpressions;
	private Double gradeAverage;
	private int totalGrades;
	private List<ProductQuestionsResponseDTO> productQuestions;
	
	public ProductDetailResponseDTO(Product product) {
		this.images = product.imagesOf(ProductImageResponseDTO::new);
		this.name = product.getName();
		this.price = product.getPrice();
		this.productAttributes = product.attributesOf(ProductAttributeResponseDTO::new);
		this.description = product.getDescription();
		this.productImpressions = product.impressionsOf(ProductImpressionResponseDTO::new);
		this.totalGrades = productImpressions.size();
		this.gradeAverage = product.gradeAverageImpressions();
		this.productQuestions = product.questionsOf(ProductQuestionsResponseDTO::new);
	}

	public Set<ProductImageResponseDTO> getImages() {
		return images;
	}

	public String getName() {
		return name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public Set<ProductAttributeResponseDTO> getProductAttributes() {
		return productAttributes;
	}

	public String getDescription() {
		return description;
	}
	
	public Double getGradeAverage() {
		return gradeAverage;
	}

	public int getTotalGrades() {
		return totalGrades;
	}

	public List<ProductQuestionsResponseDTO> getProductQuestions() {
		return productQuestions;
	}

	public List<ProductImpressionResponseDTO> getProductImpressions() {
		return productImpressions;
	}
}
