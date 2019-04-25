package com.elderresearch.wargaming.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data @Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class Gun extends Weighted {
	private float aimTime, dispersion, fireRate, reloadTime;
	private int caliber, moveDownArc, moveUpArc, traverseSpeed;
}
