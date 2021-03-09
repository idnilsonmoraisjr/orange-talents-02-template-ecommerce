package com.desafio.mercadolivre.product.mail;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public interface Mailer {

	/**	@param body,
	 *	@param subject,
	 *	@param nameFrom,
	 *  @param from,
	 *	@param to
	 */
	void send(
			@NotBlank String body,
			@NotBlank String subject,
			@NotBlank String nameFrom,
			@NotBlank @Email String from,
			@NotBlank @Email String to
			);
}
