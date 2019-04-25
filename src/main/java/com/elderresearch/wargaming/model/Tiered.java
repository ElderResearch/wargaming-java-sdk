package com.elderresearch.wargaming.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data @Accessors(chain = true)
public abstract class Tiered {
	private int tier;
	private String name, tag;
}
