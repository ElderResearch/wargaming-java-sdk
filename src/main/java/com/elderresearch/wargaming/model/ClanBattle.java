package com.elderresearch.wargaming.model;

import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;

@Data @Accessors(chain = true)
public class ClanBattle {
	private AttackType attackType;
	private Integer competitorId;
	private String frontId, frontName;
	private String provinceId, provinceName;
	private Date time;
	private BattleType type;
	private Integer vehicleLevel;
}
