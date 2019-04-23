package com.elderresearch.wargaming.model;

import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.experimental.Accessors;

@Data @Accessors(chain = true)
public class Vehicle {
	private String name, description, nation;
	private List<Integer> engines, guns, provisions, radios;
	
	private boolean isGift, isPremium, isWheeled;
	
	private Map<String, Integer> nextTanks;
	
	private Integer priceCredit, priceGold;
}
