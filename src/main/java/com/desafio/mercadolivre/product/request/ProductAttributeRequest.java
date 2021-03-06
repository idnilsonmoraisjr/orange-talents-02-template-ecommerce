package com.desafio.mercadolivre.product;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class ProductAttributeRequest {

	@NotBlank
	private String name;
	@NotBlank
	@Size(max = 200)
	private String description;
	 
	public ProductAttributeRequest(@NotBlank String name, @NotBlank @Size(max = 200) String description) {
		Assert.isTrue(StringUtils.hasLength(name), "Name must not be blank!");
		Assert.isTrue(StringUtils.hasLength(description), "Description must not be blank!");
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public ProductAttribute toModel(@NotNull @Valid Product product) {
		return new ProductAttribute(this.name, this.description, product);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductAttributeRequest other = (ProductAttributeRequest) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
