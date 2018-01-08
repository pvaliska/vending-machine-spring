package com.valiska.vendingmachinespring.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.valiska.vendingmachinespring.enums.Coins;
import com.valiska.vendingmachinespring.model.Coin;
import com.valiska.vendingmachinespring.model.Payment;
import com.valiska.vendingmachinespring.model.PaymentResult;

@Service
public class PaymentServiceImpl implements PaymentService {

	private CoinService coinService;
	private ItemService itemService;

	@Inject
	public PaymentServiceImpl(CoinService coinService, ItemService itemService) {
		this.coinService = coinService;
		this.itemService = itemService;
	}

	@Override
	public PaymentResult pay(Payment payment) {
		List<Coin> repositoryCoins= coinService.getCoins();
		return pay(payment, repositoryCoins);
	}
	
	@Override
	public PaymentResult pay(Payment payment, List<Coin> repositoryCoins) {
		BigDecimal paymentValue = getValue(payment.getCoins());
		BigDecimal toReturn = paymentValue.subtract(payment.getItem().getPrice());
		
		PaymentResult result = new PaymentResult();
		List<Coin> coins = payment.getCoins();
		coins.addAll(repositoryCoins);
		
		List<Coin> coinsBack = getReturnCoins(toReturn, coins);
		if(getValue(coinsBack).compareTo(toReturn) < 0) {
			result.setMessage("We can't process your order right now. Money back: " + paymentValue);
			return result;
		}
		
		saveCoins(coins);
		returnCoins(coinsBack);
		itemService.reduceItemByOne(payment.getItem());
		
		result.setMessage("Enjoy your " + payment.getItem().getName() +"! Money back: " + getValue(coinsBack));
		return result;
	}

	@Override
	public List<Coin> getReturnCoins(BigDecimal value, List<Coin> coins) {
		BigDecimal toReturn = value;
		List<Coin> returnCoins = new ArrayList<>();
		for (Coins coinEnum : Coins.values()) {
			for (Coin coin : coins) {
				if (coin.getValue().compareTo(coinEnum.getValue()) == 0) {
					if (coin.getValue().compareTo(toReturn) <= 0) {
						returnCoins.add(coin);
						toReturn = toReturn.subtract(coin.getValue());
						if (toReturn.compareTo(BigDecimal.ZERO) == 0) {
							return returnCoins;
						}
					}
				}
			}
		}
		return returnCoins;
	}

	@Override
	public void saveCoins(List<Coin> coins) {
		for(Coin c: coins) {
			if (c.getId() == null) {
				coinService.addCoin(c);
			}
		}
		
	}

	@Override
	public BigDecimal getValue(List<Coin> coins) {
		BigDecimal paymentValue = new BigDecimal("0");
		if (coins == null) {
			return paymentValue;
		}
		for (Coin c: coins) {
			paymentValue = paymentValue.add(c.getValue());
		}
		paymentValue.setScale(2, RoundingMode.HALF_UP);
		return paymentValue;
	}


	@Override
	public void returnCoins(List<Coin> coins) {
		for(Coin c: coins) {
			coinService.deleteCoinByValue(c);
		}
		
	}

}
