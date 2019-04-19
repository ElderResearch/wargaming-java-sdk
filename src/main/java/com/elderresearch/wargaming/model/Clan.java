package com.elderresearch.wargaming.model;

import java.util.Date;
import java.util.Map;

import lombok.Data;
import lombok.experimental.Accessors;

@Data @Accessors(chain = true)
public class Clan {
	private int clanId;
	private String color;
	private Date createdAt;
	private int membersCount;
	private String name;
	private String tag;
	
	private Map<String, ClanEmblems> emblems;
	
	@Data @Accessors(chain = true)
	public static class ClanEmblems {
		private String portal;
		private String wowp;
		private String wot;
	}
}
