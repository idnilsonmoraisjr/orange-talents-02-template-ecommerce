package com.desafio.mercadolivre.external.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.desafio.mercadolivre.external.request.NewPurchaseInvoiceResquest;
import com.desafio.mercadolivre.external.request.NewPurchaseRankingResquest;

@RestController
public class ExternalController {

	@PostMapping(value = "/invoices")
	public void createInvoice(@RequestBody @Valid NewPurchaseInvoiceResquest request) throws InterruptedException {
		System.out.println("creating invoice to "+request);
		Thread.sleep(150);
	}
	
	@PostMapping(value = "/rankings")
	public void ranking(@RequestBody @Valid NewPurchaseRankingResquest request) throws InterruptedException {
		System.out.println("creating ranking to seller "+ request);
		Thread.sleep(150);
	}
}
