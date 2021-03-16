package com.desafio.mercadolivre.external.request;

import javax.validation.constraints.NotNull;

public class NewPurchaseInvoiceResquest {

	@NotNull
	private Long purchaseId;
	@NotNull
	private Long buyerId;
	
	public NewPurchaseInvoiceResquest(@NotNull Long purchaseId, @NotNull Long buyerId) {
		this.purchaseId = purchaseId;
		this.buyerId = buyerId;
	}

	@Override
	public String toString() {
		return "NewPurchaseInvoiceResquest [purchaseId=" + purchaseId + ", buyerId=" + buyerId + "]";
	}
}
