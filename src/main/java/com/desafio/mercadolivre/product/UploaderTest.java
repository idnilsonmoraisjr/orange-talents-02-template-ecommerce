package com.desafio.mercadolivre.product;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Primary
public class UploaderTest implements Uploader {

	public Set<String> send(List<MultipartFile> images) {
		return images.stream()
				.map(image -> "http://bucket.io/"
						+ image.getOriginalFilename())
				.collect(Collectors.toSet());
	}
}
