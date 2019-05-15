package com.elderresearch.wargaming.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum AttackType {
	GROUND,
	AUCTION,
	TOURNAMENT;
	
	@Override @JsonValue
	public String toString() {
		return name().toLowerCase();
	}
	
	@JsonCreator
	public static AttackType from(String s) {
		return s == null? null : AttackType.valueOf(s.toUpperCase());
	}
}
