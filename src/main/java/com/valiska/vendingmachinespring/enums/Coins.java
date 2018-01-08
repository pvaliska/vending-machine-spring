package com.valiska.vendingmachinespring.enums;

import java.math.BigDecimal;

public enum Coins {
	eur2(new BigDecimal(2.0)), 
	eur1(new BigDecimal(1)), 
	cent50(new BigDecimal(0.5)), 
	cent20(new BigDecimal(0.2)), 
	cent10(new BigDecimal(0.1)), 
	cent5(new BigDecimal(0.05));

	private final BigDecimal value;

	Coins(BigDecimal value) {
		this.value = value;
	}

	public BigDecimal getValue() {
		return value.setScale(2,  BigDecimal.ROUND_HALF_UP);
	}
}
