package com.valiska.vendingmachinespring.service;

import java.util.List;

import com.valiska.vendingmachinespring.model.Item;

public interface ItemService {
	public List<Item> getItems();
	public Item findItemByName(String name);
	public Item addItem(Item item);
	public void deleteItem(Item item);
	public Item reduceItemByOne(Item item);
}
