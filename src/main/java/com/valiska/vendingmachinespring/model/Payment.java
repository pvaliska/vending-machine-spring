package com.valiska.vendingmachinespring.model;

import java.util.List;

public class Payment {

	private Item item;
	
	private List<Coin> coins;
	
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public List<Coin> getCoins() {
		return coins;
	}
	public void setCoins(List<Coin> coins) {
		this.coins = coins;
	}
}
