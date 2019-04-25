package com.elderresearch.wargaming.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data @Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class Turret extends Weighted {
	private int hp, traverseLeftArc, traverseRightArc, traverseSpeed, viewRange;
}
