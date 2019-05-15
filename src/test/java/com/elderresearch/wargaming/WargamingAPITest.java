package com.elderresearch.wargaming;

import java.util.concurrent.TimeUnit;

import com.elderresearch.commons.lang.Utilities;
import com.elderresearch.wargaming.WargamingAPI.ClanAPI;
import com.elderresearch.wargaming.WargamingAPI.ClanBattlesAPI;
import com.elderresearch.wargaming.WargamingAPI.ClansAPI;
import com.elderresearch.wargaming.WargamingAPI.PageParams;
import com.elderresearch.wargaming.WargamingAPI.ProvinceGeoAPI;
import com.elderresearch.wargaming.WargamingAPI.VehiclesAPI;
import com.elderresearch.wargaming.WargamingConfig.WargamingOption;

import junit.framework.TestCase;

public class WargamingAPITest extends TestCase {
	@Override
	protected void setUp() throws Exception {
		WargamingOption.WARGAMING_API_KEY.set("ef14d5a269a9c30f5803bbc03cc2d235");
		Utilities.sleep(100L, TimeUnit.MILLISECONDS);
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
