package com.elderresearch.wargaming.model;

import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

@Data @Accessors(chain = true)
public class VehicleProfile {
	private int hp, hullHp, hullWeight, maxAmmo, maxWeight, speedBackward, speedForward, weight;
	
	private List<Ammo> ammo;
	private Armor armor;
	private Engine engine;
	private Suspension suspension;
	private Gun gun;
	private Turret turret;
	private Radio radio;
}
