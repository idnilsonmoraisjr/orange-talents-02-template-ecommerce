package com.desafio.mercadolivre.category;

public class CategoryResponseDTO {

	private Long id;
	private String name;
	
	public CategoryResponseDTO(Category category) {
		this.id = category.getId();
		this.name = category.getName();
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
