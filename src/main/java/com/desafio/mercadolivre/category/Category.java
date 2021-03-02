package com.desafio.mercadolivre.category;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Entity
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private @NotBlank String name;
	@ManyToOne
	private Category parentCategory;

	@Deprecated
	public Category() {
	}

	public Category(@NotBlank String name) {
		Assert.isTrue(StringUtils.hasLength(name), "Name must not be blank!");
		this.name = name;
	}
	
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Category getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(Category parentCategory) {
		this.parentCategory = parentCategory;
	}

	
}
