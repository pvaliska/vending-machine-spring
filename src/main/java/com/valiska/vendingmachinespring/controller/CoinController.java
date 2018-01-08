package com.valiska.vendingmachinespring.controller;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.valiska.vendingmachinespring.model.Coin;
import com.valiska.vendingmachinespring.service.CoinService;

@RestController
@RequestMapping("/api/coins")
@CrossOrigin
public class CoinController {

	private CoinService coinService;

	@Inject
	public CoinController(CoinService coinService) {
		this.coinService = coinService;
	}

	@GetMapping("")
	public List<Coin> getCoins() {
		return coinService.getCoins();
	}

	@PostMapping("/add")
	public Coin addCoin(@Valid @RequestBody Coin coin) {
		return coinService.addCoin(coin);
	}
}
