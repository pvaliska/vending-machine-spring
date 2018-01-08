package com.valiska.vendingmachinespring.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.valiska.vendingmachinespring.model.Item;
import com.valiska.vendingmachinespring.model.ItemRepository;

@Service
public class ItemServiceImpl implements ItemService {

	private ItemRepository itemRepository;

	@Inject
	public ItemServiceImpl(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}

	@Override
	public Item findItemByName(String name) {
		List<Item> items = itemRepository.findByName(name);
		if (items.size() > 0) {
			return itemRepository.findByName(name).get(0);
		}
		return null;
	}

	@Override
	public Item addItem(Item item) {
		return itemRepository.save(item);
	}

	@Override
	public void deleteItem(Item item) {
		itemRepository.delete(item);
	}

	@Override
	public Item reduceItemByOne(Item item) {
		int quantity = item.getQuantity();
		if (quantity > 0) {
			item.setQuantity(quantity - 1);
		}
		return itemRepository.save(item);
	}

	@Override
	public List<Item> getItems() {
		return itemRepository.findAll();
	}

}
