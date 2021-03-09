package com.desafio.mercadolivre.product.request;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

public class NewProductImageRequest {

	@Size(min = 1)
	@NotNull
	List<MultipartFile> images = new ArrayList<>();
	
	public NewProductImageRequest() {}
	
	public void setImages(List<MultipartFile> images) {
		this.images = images;
	}

	public List<MultipartFile> getImages() {
		return images;
	}
}

