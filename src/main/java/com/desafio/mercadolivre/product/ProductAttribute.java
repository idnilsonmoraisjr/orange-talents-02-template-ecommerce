package com.desafio.mercadolivre.product;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class ProductAttribute {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	private String name;
	@NotBlank @Size(max = 200) 
	private String description;
	@NotNull @Valid
	@ManyToOne
	private  Product product;

	@Deprecated
	public ProductAttribute() {}

	public ProductAttribute(@NotBlank String name, @NotBlank @Size(max = 200) String description,
			@NotNull @Valid Product product) {
				this.name = name;
				this.description = description;
				this.product = product;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
	
}
