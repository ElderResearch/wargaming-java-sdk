package com.elderresearch.wargaming;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.elderresearch.wargaming.WorldOfTanksAPI.PageParams;

public class WorldOfTanksAPITest {
	private WorldOfTanksAPI wot;
	
	@Before
	public void setUp() throws InterruptedException {
		Thread.sleep(100L);
		wot = new WorldOfTanksAPI(new WargamingConfig());
	}
	
	@Test
	public void testClans() {
		System.out.println(wot.clans().get(PageParams.of(1, 5)).getData().get(0));
	}
	
	@Test
	@Ignore("Requires access token")
	public void testClanDetails() {
		int id = 1000008386;
		System.out.println(wot.clan().get(id).getData().get(String.valueOf(id)));
	}
	
	@Test
	public void testVehicles() {
		System.out.println(wot.vehicles().get(PageParams.of(1, 5)).getData().get("1"));
	}
	
	@Test
	public void testProvinceGeo() {
		System.out.println(wot.provinceGeo().get("edson"));
	}
	
	@Test
	@Ignore("Requires access token and this clan may not have a current battle")
	public void testClanBattles() {
		System.out.println(wot.clanBattles().get(1000002392).getData().get(0));
	}
}
