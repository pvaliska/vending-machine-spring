package com.valiska.vendingmachinespring.model;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CoinRepository extends JpaRepository<Coin, Long> {

	@Query("SELECT c FROM Coin c WHERE c.value = :value")
	public List<Coin> findByValue(@Param("value") BigDecimal value);
}
