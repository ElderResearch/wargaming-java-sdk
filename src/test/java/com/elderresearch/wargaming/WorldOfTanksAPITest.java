package com.elderresearch.wargaming;

import com.elderresearch.wargaming.WorldOfTanksAPI.ClanAPI;
import com.elderresearch.wargaming.WorldOfTanksAPI.ClanBattlesAPI;
import com.elderresearch.wargaming.WorldOfTanksAPI.ClansAPI;
import com.elderresearch.wargaming.WorldOfTanksAPI.PageParams;
import com.elderresearch.wargaming.WorldOfTanksAPI.ProvinceGeoAPI;
import com.elderresearch.wargaming.WorldOfTanksAPI.VehiclesAPI;

import junit.framework.TestCase;

public class WorldOfTanksAPITest extends TestCase {
	@Override
	protected void setUp() throws Exception {
		Thread.sleep(100L);
	}
	
	public void testClans() {
		System.out.println(ClansAPI.get(PageParams.of(1, 5)).getData().get(0));
	}
	
	public void testClanDetails() {
		int id = 1000008386;
		System.out.println(ClanAPI.get(id).getData().get(String.valueOf(id)));
	}
	
	public void testVehicles() {
		System.out.println(VehiclesAPI.get(PageParams.of(1, 5)).getData().get("1"));
	}
	
	public void testProvinceGeo() {
		System.out.println(ProvinceGeoAPI.get("edson"));
	}
	
	public void testClanBattles() {
		System.out.println(ClanBattlesAPI.get(1000002392).getData().get(0));
	}
}
