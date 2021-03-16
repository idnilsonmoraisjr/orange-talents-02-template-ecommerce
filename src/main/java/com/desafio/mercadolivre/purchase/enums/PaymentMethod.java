package com.desafio.mercadolivre.purchase.enums;

import org.springframework.web.util.UriComponentsBuilder;

import com.desafio.mercadolivre.purchase.Purchase;

public enum PaymentMethod {
	
	PAYPAL {
		@Override
		public String createReturnUrl(Purchase purchase, UriComponentsBuilder uriComponentsBuilder) {
			String urlPayPal = uriComponentsBuilder
					.path("/return-paypal/{id}")
					.buildAndExpand(purchase.getId()).toString();
			return "paypal.com/" + purchase.getId() + "?redirectUrl="+urlPayPal;
		}
	} ,
	PAGSEGURO {
		@Override
		public String createReturnUrl(Purchase purchase, UriComponentsBuilder uriComponentsBuilder) {
			String urlPagSeguro = uriComponentsBuilder
					.path("/return-pagseguro/{id}")
					.buildAndExpand(purchase.getId()).toString();
			return "pagseguro.com/" + purchase.getId() + "?redirectUrl="+urlPagSeguro;
		}
	};

	public abstract String createReturnUrl(Purchase purchase, UriComponentsBuilder uriComponentsBuilder);
}
