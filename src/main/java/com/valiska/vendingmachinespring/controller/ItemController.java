package com.valiska.vendingmachinespring.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.valiska.vendingmachinespring.model.Item;
import com.valiska.vendingmachinespring.service.ItemService;

@RestController
@RequestMapping("/api/items")
@CrossOrigin
public class ItemController {
	
	private ItemService itemService;

	@Inject
	public ItemController(ItemService itemService) {
		this.itemService = itemService;
	}
	
	@GetMapping("")
	public List<Item> getItems() {
		return itemService.getItems();
	}

}
