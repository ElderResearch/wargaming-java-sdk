package com.elderresearch.wargaming.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum BattleType {
	ATTACK,
	DEFENCE;
	
	@Override @JsonValue
	public String toString() {
		return name().toLowerCase();
	}
	
	@JsonCreator
	public static BattleType from(String s) {
		return s == null? null : BattleType.valueOf(s.toUpperCase());
	}
}
