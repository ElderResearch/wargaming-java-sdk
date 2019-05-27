package com.elderresearch.wargaming;

import java.util.logging.Level;

import org.junit.AfterClass;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.elderresearch.wargaming.WorldOfTanksAPI.PageParams;

import lombok.val;

public class WorldOfTanksAPITest {
	private static WorldOfTanksAPI wot;
	
	@BeforeClass
	public static void setUp() throws InterruptedException {
		val appId = System.getenv("wot_application_id");
		Assume.assumeTrue("You must specify environment variable wot_applicaton_id to run these tests", appId != null);
		
		Thread.sleep(100L);
		wot = new WargamingConfig().setApplicationId(appId).setLogLevel(Level.INFO).wot();
	}
	
	@AfterClass
	public static void tearDown() {
		if (wot != null) { wot.close(); }
	}
	
	@Test
	public void testClans() {
		System.out.println(wot.clans().get(PageParams.of(1, 5)).getData().get(0));
	}
	
	@Test
	@Ignore("Requires access token")
	public void testClanDetails() {
		int id = 1000008386;
		System.out.println(wot.clans().clan().get(id).getData().get(String.valueOf(id)));
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
		System.out.println(wot.globalMap().clanBattles().get(1000002392).getData().get(0));
	}
}
