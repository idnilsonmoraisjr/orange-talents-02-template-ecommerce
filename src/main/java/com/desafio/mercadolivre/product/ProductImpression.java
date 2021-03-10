package com.desafio.mercadolivre.product;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.desafio.mercadolivre.user.User;

@Entity
public class ProductImpression {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	@Min(value = 1)
    @Max(value = 5)
	private Double grade;
	@NotBlank
	private String title;
	@NotBlank
	@Size(max = 500)
	private String description;
	@NotNull
	@Valid
	@ManyToOne
	private User user;
	@NotNull
	@Valid
	@ManyToOne 
	private Product product;
	
	@Deprecated
	public ProductImpression() {}
	
	public ProductImpression(@NotNull @Min(1) @Max(5) Double grade, @NotBlank String title,
			@NotBlank @Size(max = 500) String description, @NotNull @Valid User user, @NotNull @Valid Product product) {
		this.grade = grade;
		this.title = title;
		this.description = description;
		this.user = user;
		this.product = product;
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
	
	public Product getProduct() {
		return product;
	}
}
