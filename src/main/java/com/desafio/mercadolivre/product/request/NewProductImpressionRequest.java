package com.desafio.mercadolivre.product.request;

import java.util.Optional;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.desafio.mercadolivre.product.Product;
import com.desafio.mercadolivre.product.ProductImpression;
import com.desafio.mercadolivre.user.User;

public class NewProductImpressionRequest {

	@NotNull
	@Min(value = 1)
    @Max(value = 5)
	Double grade;
	@NotBlank
	String title;
	@NotBlank
	@Size(max = 500)
	String description;
	
	public NewProductImpressionRequest() {}
	
	public NewProductImpressionRequest(@NotNull @Min(1) @Max(5) Double grade, @NotBlank String title,
			@NotBlank @Size(max = 500) String description) {
		this.grade = grade;
		this.title = title;
		this.description = description;
	}
	
	public Double getGrade() {
		return grade;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public  ProductImpression toModel(User userAthenticated, Optional<Product> product) {
		return new ProductImpression(this.grade, this.title, this.description, userAthenticated, product.get());
	}
}
