package com.elderresearch.wargaming.model;

import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data @Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class Vehicle extends Tiered {
	private int tankId;
	private String shortName, description, nation, type;
	private List<Integer> engines, guns, provisions, radios, suspensions, turrets;
	
	private boolean isGift, isPremium, isWheeled;
	
	private Map<String, Integer> nextTanks;
	
	private Integer priceCredit, priceGold;
	
	private VehicleProfile defaultProfile;
}
