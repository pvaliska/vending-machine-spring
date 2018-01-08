package com.valiska.vendingmachinespring.service;

import java.math.BigDecimal;
import java.util.List;

import com.valiska.vendingmachinespring.model.Coin;
import com.valiska.vendingmachinespring.model.Payment;
import com.valiska.vendingmachinespring.model.PaymentResult;

public interface PaymentService {
	public PaymentResult pay(Payment payment);
	public PaymentResult pay(Payment payment, List<Coin> repositoryCoins);
	public List<Coin> getReturnCoins(BigDecimal value, List<Coin> coins);
	public void saveCoins(List<Coin> coins);
	public void returnCoins(List<Coin> coins);
	public BigDecimal getValue(List<Coin> coins);
}
