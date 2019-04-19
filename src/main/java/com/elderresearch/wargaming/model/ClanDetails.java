package com.elderresearch.wargaming.model;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data @Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class ClanDetails extends Clan {
	private boolean acceptsJoinRequests;
	
	private int creatorId;
	private String creatorName;
	
	private String description, descriptionHtml;
	
	private boolean isClanDisbanded;
	
	private int leaderId;
	private String leaderName;
	
	private String motto;
	private String oldName, oldTag;
	
	private Date renamedAt, updatedAt;
}
