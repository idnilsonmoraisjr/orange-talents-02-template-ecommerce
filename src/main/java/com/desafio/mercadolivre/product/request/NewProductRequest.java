package com.desafio.mercadolivre.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.springframework.util.Assert;

import com.desafio.mercadolivre.category.Category;
import com.desafio.mercadolivre.shared.ExistsId;
import com.desafio.mercadolivre.user.User;

public class NewProductPostRequest {

	@NotBlank
	private String name;
	@NotNull
	@Positive
	private BigDecimal price;
	@NotNull
	@Positive
	private int amount;
	@NotNull
	@Size(min = 3)
	@Valid
	private List<ProductAttributeRequest> productAttributes = new ArrayList<>();
	@NotBlank
	@Size(max = 1000)
	private String description;
	@NotNull
	@ExistsId(domainClass = Category.class, fieldName="id")
	private Long categoryId;
	
	public NewProductPostRequest() {}
	
	public NewProductPostRequest(@NotBlank String name, @NotNull @Positive BigDecimal price, @NotNull @Positive int amount,
			@NotNull @Valid List<ProductAttributeRequest> productAttributes, @NotBlank @Size(max = 1000) String description,
			@NotNull Long categoryId) {
		this.name = name;
		this.price = price;
		this.amount = amount;
		Assert.isTrue(productAttributes.size() >= 3, "The product must have at least 3 characteristics");
		this.productAttributes.addAll(productAttributes);
		this.description = description;
		this.categoryId = categoryId;
	}

	public Product toModel(EntityManager entityManager, User user) {
		Optional<Category> optionalCategory = Optional.ofNullable(entityManager.find(Category.class, categoryId));
		Assert.state(optionalCategory.isPresent(), "Category does not exist for the given id!");
		
		return new Product(this.name, this.price, this.amount, this.productAttributes, this.description, optionalCategory.get(), user);
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

	public List<ProductAttributeRequest> getProductAttributes() {
		return productAttributes;
	}

	public String getDescription() {
		return description;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public Set<String> findEqualAttributes() {
		HashSet<String> sameName = new HashSet();
		HashSet<String> results = new HashSet();
		
		for(ProductAttributeRequest attribute : productAttributes) {
			String attributeName = attribute.getName();
			if(!sameName.add(attributeName)) {
				results.add(attributeName);
			}
		}
		return results;
	}
}
