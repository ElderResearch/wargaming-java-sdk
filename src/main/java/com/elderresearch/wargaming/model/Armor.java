package com.elderresearch.wargaming.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data @Accessors(chain = true)
public class Armor {
	private ArmorStrength hull, turret;
	
	@Data @Accessors(chain = true)
	public static class ArmorStrength {
		private int front, rear, sides;
	}
}
