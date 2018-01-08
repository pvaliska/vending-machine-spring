package com.valiska.vendingmachinespring.service;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.valiska.vendingmachinespring.model.Coin;
import com.valiska.vendingmachinespring.model.CoinRepository;

@Service
public class CoinServiceImpl implements CoinService {

	private CoinRepository coinRepository;

	@Inject
	public CoinServiceImpl(CoinRepository coinRepository) {
		this.coinRepository = coinRepository;
	}

	@Override
	public Coin addCoin(Coin coin) {
		return coinRepository.save(coin);
	}

	@Override
	public List<Coin> getCoins() {
		List<Coin> coins = coinRepository.findAll();
		return coins;
	}

	@Override
	public Coin getCoinByValue(BigDecimal value) {
		List<Coin> coins = coinRepository.findByValue(value);
		if (coins.size() > 0) {
			return coins.get(0);
		}
		return null;
	}

	@Override
	public void deleteCoinByValue(Coin coin) {
		Coin c = getCoinByValue(coin.getValue());
		if (c != null) {
			deleteCoin(c);
		}
	}

	@Override
	public void deleteCoin(Coin coin) {
		coinRepository.delete(coin);
	}

	@Override
	public void addCoins(List<Coin> coins) {
		for(Coin c: coins) {
			coinRepository.save(c);
		}
	}

}
