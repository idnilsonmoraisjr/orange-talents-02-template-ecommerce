package com.desafio.mercadolivre.external.request;

import javax.validation.constraints.NotNull;

public class NewPurchaseRankingResquest {

	@NotNull
	private Long purchaseId;
	@NotNull
	private Long productOwnerId;
	
	public NewPurchaseRankingResquest(@NotNull Long purchaseId, @NotNull Long productOwnerId) {
		this.purchaseId = purchaseId;
		this.productOwnerId = productOwnerId;
	}

	@Override
	public String toString() {
		return "NewPurchaseRankingResquest [purchaseId=" + purchaseId + ", buyerId=" + productOwnerId + "]";
	}
}
