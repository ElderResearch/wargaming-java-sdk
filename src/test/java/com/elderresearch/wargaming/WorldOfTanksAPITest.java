package com.elderresearch.wargaming;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.elderresearch.wargaming.WorldOfTanksAPI.ClanAPI;
import com.elderresearch.wargaming.WorldOfTanksAPI.ClanBattlesAPI;
import com.elderresearch.wargaming.WorldOfTanksAPI.ClansAPI;
import com.elderresearch.wargaming.WorldOfTanksAPI.PageParams;
import com.elderresearch.wargaming.WorldOfTanksAPI.ProvinceGeoAPI;
import com.elderresearch.wargaming.WorldOfTanksAPI.VehiclesAPI;

public class WorldOfTanksAPITest {
	@Before
	public void setUp() throws InterruptedException {
		Thread.sleep(100L);
	}
	
	@Test
	public void testClans() {
		System.out.println(ClansAPI.get(PageParams.of(1, 5)).getData().get(0));
	}
	
	@Test
	@Ignore("Requires access token")
	public void testClanDetails() {
		int id = 1000008386;
		System.out.println(ClanAPI.get(id).getData().get(String.valueOf(id)));
	}
	
	@Test
	public void testVehicles() {
		System.out.println(VehiclesAPI.get(PageParams.of(1, 5)).getData().get("1"));
	}
	
	@Test
	public void testProvinceGeo() {
		System.out.println(ProvinceGeoAPI.get("edson"));
	}
	
	@Test
	@Ignore("Requires access token and this clan may not have a current battle")
	public void testClanBattles() {
		System.out.println(ClanBattlesAPI.get(1000002392).getData().get(0));
	}
}
