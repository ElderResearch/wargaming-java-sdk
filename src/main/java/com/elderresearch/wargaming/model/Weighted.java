package com.elderresearch.wargaming.model;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data @Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public abstract class Weighted extends Tiered {
	private int weight;
}
