/*******************************************************************************
 * Copyright (c) 2017 Elder Research, Inc.
 * All rights reserved.
 *******************************************************************************/
package com.elderresearch.wargaming;

import static com.elderresearch.commons.rest.client.RecursiveTarget.target;
import static com.elderresearch.commons.rest.client.WebParam.add;

import javax.ws.rs.client.Invocation;

import com.elderresearch.commons.rest.client.RecursiveTarget;
import com.elderresearch.commons.rest.client.RestClient;
import com.elderresearch.commons.rest.client.RestEndpoint;
import com.elderresearch.commons.rest.client.WebParam;
import com.elderresearch.commons.rest.client.WebParam.WebParamGroup;
import com.elderresearch.commons.rest.client.WebParam.WebQueryParam;
import com.elderresearch.commons.rest.client.WebParam.WebTemplateParam;
import com.elderresearch.wargaming.WargamingResponse.WithList;
import com.elderresearch.wargaming.WargamingResponse.WithMap;
import com.elderresearch.wargaming.WargamingResponse.WithMapOfLists;
import com.elderresearch.wargaming.WorldOfTanksAPI.EncyclopediaAPI.VehiclesAPI;
import com.elderresearch.wargaming.model.Clan;
import com.elderresearch.wargaming.model.ClanBattle;
import com.elderresearch.wargaming.model.ClanDetails;
import com.elderresearch.wargaming.model.PlayerVehicle;
import com.elderresearch.wargaming.model.ProvinceGeo;
import com.elderresearch.wargaming.model.Vehicle;

import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.UtilityClass;

@Accessors(fluent = true)
public class WorldOfTanksAPI implements AutoCloseable {
	private WargamingClient client;
	private WargamingStaticClient staticClient;
	
	@Getter private ClansAPI clans;
	@Getter private VehiclesAPI vehicles;
	@Getter private PlayersAPI players;
	@Getter private GlobalMapAPI globalMap;
	@Getter private ProvinceGeoAPI provinceGeo;
	
	WorldOfTanksAPI(WargamingConfig config) {
		client         = new WargamingClient(config);
		clans          = new ClansAPI(client);
		vehicles       = new VehiclesAPI(client);
		players        = new PlayersAPI(client);
		globalMap      = new GlobalMapAPI(client);
		
		staticClient   = new WargamingStaticClient(config);
		provinceGeo    = new ProvinceGeoAPI(staticClient);
	}
	
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
	
	public static class ClansAPI extends RestEndpoint {
		private static final RecursiveTarget clans = target.child("clans"), list = clans.child("list/");
		
		@Getter private ClanAPI clan;

		ClansAPI(RestClient client) {
			super(client, list);
			clan = new ClanAPI(client);
		}
		
		public WithList<Clan> get(WebParam... params) {
			return request(params).get(WithList.forType(Clan.class));
		}
		
		public static class ClanAPI extends RestEndpoint {
			private static final RecursiveTarget info = clans.child("info/");
			
			ClanAPI(RestClient client) { super(client, info); }
			
			public Invocation.Builder requestFor(int id, WebParam... params) {
				return super.request(add(params, WebQueryParam.of("clan_id", id)));
			}
			
			public WithMap<ClanDetails> get(int id, WebParam... params) {
				return requestFor(id, params).get(WithMap.forType(ClanDetails.class));
			}
		}
	}
	
	@UtilityClass
	protected class EncyclopediaAPI {
		private final RecursiveTarget encyclopedia = target.child("encyclopedia");
		
		public static class VehiclesAPI extends RestEndpoint {
			private static final RecursiveTarget vehicles = encyclopedia.child("vehicles/");
			
			VehiclesAPI(RestClient client) { super(client, vehicles); }
			
			public Invocation.Builder requestFor(int tankId, WebParam... params) {
				return request(add(params, WebQueryParam.of("tank_id", tankId)));
			}
			
			public WithMap<Vehicle> get(WebParam... params) {
				return request(params).get(WithMap.forType(Vehicle.class));
			}
			
			public WithMap<Vehicle> get(int tankId, WebParam... params) {
				return requestFor(tankId, params).get(WithMap.forType(Vehicle.class));
			}
		}
	}
	
	
	public static class PlayersAPI extends RestEndpoint {
		private static final RecursiveTarget account = target.child("account");
		
		@Getter private PlayerVehiclesAPI playerVehicles;
		
		PlayersAPI(RestClient client) {
			super(client, account);
			playerVehicles = new PlayerVehiclesAPI(client);
		}
		
		public static class PlayerVehiclesAPI extends RestEndpoint {
			private static final RecursiveTarget tanks = account.child("tanks/");
			
			PlayerVehiclesAPI(RestClient client) { super(client, tanks); }
			
			public Invocation.Builder requestFor(int accountId, WebParam... params) {
				return request(add(params, WebQueryParam.of("account_id", accountId)));
			}
			
			public WithMapOfLists<PlayerVehicle> get(int accountId, WebParam... params) {
				return requestFor(accountId, params).get(WithMapOfLists.forType(PlayerVehicle.class));
			}
		}
	}
	
	public static class GlobalMapAPI extends RestEndpoint {
		private static final RecursiveTarget map = target.child("globalmap");

		@Getter private ClanBattlesAPI clanBattles;
		
		GlobalMapAPI(RestClient client) {
			super(client, map);
			clanBattles = new ClanBattlesAPI(client);
		}
		
		public static class ClanBattlesAPI extends RestEndpoint {
			private static final RecursiveTarget battles = map.child("clanbattles/");
			
			ClanBattlesAPI(RestClient client) { super(client, battles); }
			
			public Invocation.Builder requestFor(int clanId, WebParam... params) {
				return request(add(params, WebQueryParam.of("clan_id", clanId)));
			}
			
			public WithList<ClanBattle> get(int clanId, WebParam... params) {
				return requestFor(clanId, params).get(WithList.forType(ClanBattle.class));
			}
		}
	}
	
	public static class ProvinceGeoAPI extends RestEndpoint {
		private static final RecursiveTarget provinces = target("provinces_geojson/{alias}.json");
		
		ProvinceGeoAPI(RestClient client) { super(client, provinces); }
		
		public Invocation.Builder requestFor(String alias, WebParam... params) {
			return request(add(params, WebTemplateParam.of("alias", alias)));
		}
		
		public ProvinceGeo get(String alias, WebParam... params) {
			return requestFor(alias, params).get(ProvinceGeo.class);
		}
	}
	
	@Override
	public void close() {
		client.close();
		staticClient.close();
	}
}
