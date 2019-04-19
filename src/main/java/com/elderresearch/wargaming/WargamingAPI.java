/*******************************************************************************
 * Copyright (c) 2017 Elder Research, Inc.
 * All rights reserved.
 *******************************************************************************/
package com.elderresearch.wargaming;

import static com.elderresearch.commons.rest.client.RecursiveTarget.target;

import javax.ws.rs.client.Invocation;

import org.apache.commons.lang3.ArrayUtils;

import com.elderresearch.commons.rest.client.RecursiveTarget;
import com.elderresearch.commons.rest.client.WebParam;
import com.elderresearch.commons.rest.client.WebParam.WebParamGroup;
import com.elderresearch.commons.rest.client.WebParam.WebQueryParam;
import com.elderresearch.wargaming.WargamingResponse.WithList;
import com.elderresearch.wargaming.WargamingResponse.WithMap;
import com.elderresearch.wargaming.model.Clan;
import com.elderresearch.wargaming.model.ClanDetails;

import lombok.experimental.Accessors;
import lombok.experimental.UtilityClass;

@UtilityClass
@Accessors(fluent = true)
public class WargamingAPI {
	private WargamingClient client() { return WargamingClient.get(); }
	
	public Invocation.Builder request(String path, WebParam... params) {
		return client().request(target(path), params);
	}
	
	public class PageParams extends WebParamGroup {
		private PageParams(long pageNumber, long limit) {
			super(WebQueryParam.of("page_no", pageNumber), WebQueryParam.of("limit", limit));
		}
		public static PageParams of(long pageNumber, long limit) {
			return new PageParams(pageNumber, limit);
		}
	}
	
	@UtilityClass
	public class ClansAPI {
		private final RecursiveTarget target = target("clans"), list = target.child("list/");
		
		public Invocation.Builder request(WebParam... params) {
			return client().request(list, params);
		}
		
		public WithList<Clan> get(WebParam... params) {
			return request(params).get(WithList.forType(Clan.class));
		}
	}
	
	@UtilityClass
	public class ClanAPI {
		private final RecursiveTarget target = ClansAPI.target.child("info/");
		
		public Invocation.Builder request(int id, WebParam... params) {
			return client().request(target, ArrayUtils.add(params, WebQueryParam.of("clan_id", id)));
		}
		
		public WithMap<ClanDetails> get(int id, WebParam... params) {
			return request(id, params).get(WithMap.forType(ClanDetails.class));
		}
	}	
	
	public void close() {
		client().close();
	}
}
