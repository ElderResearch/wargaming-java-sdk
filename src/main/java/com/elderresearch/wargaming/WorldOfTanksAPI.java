/*******************************************************************************
 * Copyright (c) 2017 Elder Research, Inc.
 * All rights reserved.
 *******************************************************************************/
package com.elderresearch.wargaming;

import static com.elderresearch.commons.rest.client.RecursiveTarget.target;
import static com.elderresearch.commons.rest.client.WebParam.add;

import javax.ws.rs.client.Invocation;

import com.elderresearch.commons.rest.client.RecursiveTarget;
import com.elderresearch.commons.rest.client.WebParam;
import com.elderresearch.commons.rest.client.WebParam.WebParamGroup;
import com.elderresearch.commons.rest.client.WebParam.WebQueryParam;
import com.elderresearch.commons.rest.client.WebParam.WebTemplateParam;
import com.elderresearch.wargaming.WargamingResponse.WithList;
import com.elderresearch.wargaming.WargamingResponse.WithMap;
import com.elderresearch.wargaming.WargamingResponse.WithMapOfLists;
import com.elderresearch.wargaming.model.Clan;
import com.elderresearch.wargaming.model.ClanBattle;
import com.elderresearch.wargaming.model.ClanDetails;
import com.elderresearch.wargaming.model.PlayerVehicle;
import com.elderresearch.wargaming.model.ProvinceGeo;
import com.elderresearch.wargaming.model.Vehicle;

import lombok.experimental.UtilityClass;

@UtilityClass
public class WorldOfTanksAPI {
	private WargamingClient client = WargamingClient.client();
	private WargamingStaticClient staticClient = WargamingStaticClient.client();
	
	private static final RecursiveTarget target = target("wot");
	
	public Invocation.Builder request(String path, WebParam... params) {
		return client.request(target(path), params);
	}
	
	public static class PageParams extends WebParamGroup {
		private PageParams(long pageNumber, long limit) {
			super(WebQueryParam.of("page_no", pageNumber), WebQueryParam.of("limit", limit));
		}
		public static PageParams of(long pageNumber, long limit) {
			return new PageParams(pageNumber, limit);
		}
	}
	
	@UtilityClass
	public class ClansAPI {
		private final RecursiveTarget clans = WorldOfTanksAPI.target.child("clans"), list = clans.child("list/");
		
		public Invocation.Builder request(WebParam... params) {
			return client.request(list, params);
		}
		
		public WithList<Clan> get(WebParam... params) {
			return request(params).get(WithList.forType(Clan.class));
		}
	}
	
	@UtilityClass
	public class ClanAPI {
		private final RecursiveTarget info = ClansAPI.clans.child("info/");
		
		public Invocation.Builder request(int id, WebParam... params) {
			return client.request(info, add(params, WebQueryParam.of("clan_id", id)));
		}
		
		public WithMap<ClanDetails> get(int id, WebParam... params) {
			return request(id, params).get(WithMap.forType(ClanDetails.class));
		}
	}
	
	@UtilityClass
	protected class EncyclopediaAPI {
		private final RecursiveTarget encyclopedia = WorldOfTanksAPI.target.child("encyclopedia");
	}
	
	
	@UtilityClass
	public class VehiclesAPI {
		private final RecursiveTarget vehicles = EncyclopediaAPI.encyclopedia.child("vehicles/");
		
		public Invocation.Builder request(WebParam... params) {
			return client.request(vehicles, params);
		}
		
		public Invocation.Builder request(int tankId, WebParam... params) {
			return request(add(params, WebQueryParam.of("tank_id", tankId)));
		}
		
		public WithMap<Vehicle> get(WebParam... params) {
			return request(params).get(WithMap.forType(Vehicle.class));
		}
		
		public WithMap<Vehicle> get(int tankId, WebParam... params) {
			return request(tankId, params).get(WithMap.forType(Vehicle.class));
		}
	}
	
	@UtilityClass
	public class PlayersAPI {
		private final RecursiveTarget account = WorldOfTanksAPI.target.child("account");
		
		public Invocation.Builder request(WebParam... params) {
			return client.request(account, params);
		}
	}
	
	@UtilityClass
	public class PlayerVehiclesAPI {
		private final RecursiveTarget tanks = PlayersAPI.account.child("tanks/");
		
		public Invocation.Builder request(int accountId, WebParam... params) {
			return client.request(tanks, add(params, WebQueryParam.of("account_id", accountId)));
		}
		
		public WithMapOfLists<PlayerVehicle> get(int accountId, WebParam... params) {
			return request(accountId, params).get(WithMapOfLists.forType(PlayerVehicle.class));
		}
	}
	
	@UtilityClass
	public class GlobalMapAPI {
		private final RecursiveTarget map = WorldOfTanksAPI.target.child("globalmap");
		
		public Invocation.Builder request(WebParam... params) {
			return client.request(map, params);
		}
	}
	
	@UtilityClass
	public class ClanBattlesAPI {
		private final RecursiveTarget battles = GlobalMapAPI.map.child("clanbattles/");
		
		public Invocation.Builder request(int clanId, WebParam... params) {
			return client.request(battles, add(params, WebQueryParam.of("clan_id", clanId)));
		}
		
		public WithList<ClanBattle> get(int clanId, WebParam... params) {
			return request(clanId, params).get(WithList.forType(ClanBattle.class));
		}
	}
	
	@UtilityClass
	public class ProvinceGeoAPI {
		private final RecursiveTarget provinces = target("provinces_geojson/{alias}.json");
		
		public Invocation.Builder request(String alias, WebParam... params) {
			return staticClient.request(provinces, add(params, WebTemplateParam.of("alias", alias)));
		}
		
		public ProvinceGeo get(String alias, WebParam... params) {
			return request(alias, params).get(ProvinceGeo.class);
		}
	}
	
	public void close() {
		client.close();
		staticClient.close();
	}
}
