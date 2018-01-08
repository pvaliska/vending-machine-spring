package com.valiska.vendingmachinespring.service;

import java.math.BigDecimal;
import java.util.List;

import com.valiska.vendingmachinespring.model.Coin;

public interface CoinService {

	public Coin addCoin(Coin coin);
	
	public void addCoins(List<Coin> coins);
	
	public List<Coin> getCoins();

	public Coin getCoinByValue(BigDecimal value);
	
	public void deleteCoin(Coin coin);
	
	public void deleteCoinByValue(Coin coin);
}
