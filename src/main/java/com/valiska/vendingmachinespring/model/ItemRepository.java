package com.valiska.vendingmachinespring.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

	@Query("SELECT i FROM Item i WHERE i.name = :name")
	public List<Item> findByName(@Param("name") String name);
}
