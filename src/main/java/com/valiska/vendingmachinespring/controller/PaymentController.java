package com.valiska.vendingmachinespring.controller;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.valiska.vendingmachinespring.model.Payment;
import com.valiska.vendingmachinespring.model.PaymentResult;
import com.valiska.vendingmachinespring.service.PaymentService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class PaymentController {
	
	private PaymentService paymentService;
	
	@Inject
	public PaymentController(PaymentService paymentService) {
		this.paymentService = paymentService;
	}
	
	@PostMapping("/payment")
	public PaymentResult pay(@Valid @RequestBody Payment payment) {
	    return paymentService.pay(payment);
	}
	
}
