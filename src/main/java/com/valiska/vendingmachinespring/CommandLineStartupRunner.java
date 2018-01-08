package com.valiska.vendingmachinespring;

import java.math.BigDecimal;
import java.util.Arrays;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.valiska.vendingmachinespring.model.Coin;
import com.valiska.vendingmachinespring.model.Item;
import com.valiska.vendingmachinespring.service.CoinService;
import com.valiska.vendingmachinespring.service.ItemService;

@Component
public class CommandLineStartupRunner implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(CommandLineStartupRunner.class);

	private CoinService coinService;
	private ItemService itemService;

	@Inject
	public CommandLineStartupRunner(CoinService coinService, ItemService itemService) {
		this.coinService = coinService;
		this.itemService = itemService;
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info(
				"Application started with command-line arguments: {} . \n To kill this application, press Ctrl + C.",
				Arrays.toString(args));

		addCoin("eur2", new BigDecimal(2), 1);
		addCoin("eur1", new BigDecimal(1), 1);
		addCoin("cent50", new BigDecimal(0.5), 1);
		addCoin("cent20", new BigDecimal(0.2), 1);
		addCoin("cent10", new BigDecimal(0.1), 1);
		addCoin("cent5", new BigDecimal(0.05), 1);
		
		addItem("Pepsi", new BigDecimal("1.5"), 2);
		addItem("Cola", new BigDecimal("1.4"), 2);
		addItem("Sprite", new BigDecimal("1.3"), 2);
	}

	private void addCoin(String type, BigDecimal value, int quantity) {
		for (int i = 0; i < quantity; i++) {
			Coin coin = new Coin();
			coin.setValue(value);
			coinService.addCoin(coin);
		}
	}
	
	private void addItem(String name, BigDecimal price, int quantity) {
		Item item = new Item();
		item.setPrice(price);
		item.setName(name);
		item.setQuantity(quantity);
		itemService.addItem(item);
	}

}
