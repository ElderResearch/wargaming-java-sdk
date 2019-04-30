package com.elderresearch.wargaming.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data @Accessors(chain = true)
public class Ammo {
	private DistributionSummary damage, penetration;
	private String type;
}
