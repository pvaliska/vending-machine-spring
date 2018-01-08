package com.valiska.vendingmachinespring.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.valiska.vendingmachinespring.enums.Coins;
import com.valiska.vendingmachinespring.model.Coin;
import com.valiska.vendingmachinespring.model.Item;
import com.valiska.vendingmachinespring.model.Payment;

@RunWith(MockitoJUnitRunner.class)
public class PaymentServiceTest {

	@Mock
	private CoinService coinService;
	
	@Mock
	private ItemService itemService;
	
	private PaymentService paymentService;
	private Payment payment;
	List<Coin> repositoryCoins;
	
	@Before public void setup() {
		paymentService = new PaymentServiceImpl(coinService, itemService);
		Item sprite = new Item();
		sprite.setName("Pepsi");
		sprite.setPrice(new BigDecimal("1.30"));
		sprite.setQuantity(1);
		payment = new Payment();
		payment.setItem(sprite);
		repositoryCoins = new ArrayList<Coin>();
		
	}
	@Test
	public void getValue() {
		assertEquals(new BigDecimal("0"), paymentService.getValue(null));
		List<Coin> coins = new ArrayList<Coin>();
		assertEquals(new BigDecimal("0"), paymentService.getValue(coins));
		coins = addCoin(coins, Coins.eur1);
		assertEquals(new BigDecimal("1.00"), paymentService.getValue(coins));
		coins = addCoin(coins, Coins.cent20);
		coins = addCoin(coins, Coins.cent10);
		assertEquals(new BigDecimal("1.30"), paymentService.getValue(coins));
	}
	
	@Test
	public void getReturnCoins() {
		List<Coin> paymentCoins = new ArrayList<Coin>();
		paymentCoins = addCoin(paymentCoins, Coins.eur1);
		paymentCoins = addCoin(paymentCoins, Coins.cent20);
		paymentCoins = addCoin(paymentCoins, Coins.cent10);
		BigDecimal value = new BigDecimal("0.30");
		List<Coin> returnedCoins = paymentService.getReturnCoins(value, paymentCoins);
		assertEquals(new BigDecimal("0.30"), paymentService.getValue(returnedCoins));
		assertThat(returnedCoins, hasSize(2));
		
		value = new BigDecimal("0");
		returnedCoins = paymentService.getReturnCoins(value, paymentCoins);
		assertEquals(new BigDecimal("0"), paymentService.getValue(returnedCoins));
		assertThat(returnedCoins, hasSize(0));
		
		value = new BigDecimal("1.30");
		returnedCoins = paymentService.getReturnCoins(value, paymentCoins);
		assertEquals(new BigDecimal("1.30"), paymentService.getValue(returnedCoins));
		assertThat(returnedCoins, hasSize(3));
		
		value = new BigDecimal("1.50");
		returnedCoins = paymentService.getReturnCoins(value, paymentCoins);
		assertEquals(new BigDecimal("1.30"), paymentService.getValue(returnedCoins));
		assertThat(returnedCoins, hasSize(3));
		
		paymentCoins = addCoin(paymentCoins, Coins.eur2);
		paymentCoins = addCoin(paymentCoins, Coins.cent50);
		paymentCoins = addCoin(paymentCoins, Coins.cent10);
		value = new BigDecimal("2.70");
		returnedCoins = paymentService.getReturnCoins(value, paymentCoins);
		assertEquals(new BigDecimal("2.70"), paymentService.getValue(returnedCoins));
		assertThat(returnedCoins, hasSize(3));
		
		value = new BigDecimal("4.20");
		returnedCoins = paymentService.getReturnCoins(value, paymentCoins);
		assertEquals(new BigDecimal("3.90"), paymentService.getValue(returnedCoins));
		assertThat(returnedCoins, hasSize(6));
		
	}
	
	@Test
	public void pay_exact() {
		repositoryCoins = new ArrayList<Coin>();
		List<Coin> paymentCoins = new ArrayList<Coin>();
		paymentCoins = addCoin(paymentCoins, Coins.eur1);
		paymentCoins = addCoin(paymentCoins, Coins.cent20);
		paymentCoins = addCoin(paymentCoins, Coins.cent10);
		payment.setCoins(paymentCoins);
		assertEquals("Enjoy your Pepsi! Money back: 0", paymentService.pay(payment, repositoryCoins).getMessage());
		
		repositoryCoins = addCoin(repositoryCoins, Coins.cent50);
		repositoryCoins = addCoin(repositoryCoins, Coins.eur1);
		assertEquals("Enjoy your Pepsi! Money back: 0", paymentService.pay(payment, repositoryCoins).getMessage());
	}
	
	@Test
	public void pay_more_return_exact() {
		repositoryCoins = new ArrayList<Coin>();
		repositoryCoins = addCoin(repositoryCoins, Coins.cent50);
		repositoryCoins = addCoin(repositoryCoins, Coins.eur1);
		List<Coin> paymentCoins = new ArrayList<Coin>();
		paymentCoins = new ArrayList<Coin>();
		paymentCoins = addCoin(paymentCoins, Coins.eur1);
		paymentCoins = addCoin(paymentCoins, Coins.cent20);
		paymentCoins = addCoin(paymentCoins, Coins.cent20);
		paymentCoins = addCoin(paymentCoins, Coins.cent10);
		payment.setCoins(paymentCoins);
		assertEquals("Enjoy your Pepsi! Money back: 0.20", paymentService.pay(payment, repositoryCoins).getMessage(), "Enjoy your Pepsi! Money back: 0.20");
	}
	
	@Test
	public void pay_more_return_from_repository() {
		repositoryCoins = new ArrayList<Coin>();
		repositoryCoins = addCoin(repositoryCoins, Coins.cent50);
		repositoryCoins = addCoin(repositoryCoins, Coins.cent10);
		repositoryCoins = addCoin(repositoryCoins, Coins.cent10);
		List<Coin> paymentCoins = new ArrayList<Coin>();
		paymentCoins = addCoin(paymentCoins, Coins.eur2);
		payment.setCoins(paymentCoins);
		assertEquals("Enjoy your Pepsi! Money back: 0.70", paymentService.pay(payment, repositoryCoins).getMessage());
		
	}
	
	@Test
	public void pay_more_dont_return() {
		repositoryCoins = new ArrayList<Coin>();
		repositoryCoins = addCoin(repositoryCoins, Coins.cent50);
		repositoryCoins = addCoin(repositoryCoins, Coins.eur1);
		List<Coin> paymentCoins = new ArrayList<Coin>();
		paymentCoins = addCoin(paymentCoins, Coins.eur2);
		payment.setCoins(paymentCoins);
		assertEquals("We can't process your order right now. Money back: 2.00", paymentService.pay(payment, repositoryCoins).getMessage());
	}
	
	public List<Coin> addCoin(List<Coin> coins, Coins type){
		Coin coin = new Coin();
		coin.setValue(type.getValue());
		coins.add(coin);
		return coins;
	}
	
}
