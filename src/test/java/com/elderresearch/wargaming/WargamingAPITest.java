package com.elderresearch.wargaming;

import com.elderresearch.wargaming.WargamingAPI.ClanAPI;
import com.elderresearch.wargaming.WargamingAPI.ClansAPI;
import com.elderresearch.wargaming.WargamingAPI.PageParams;

import junit.framework.TestCase;

public class WargamingAPITest extends TestCase {

	public void testClans() {
		System.out.println(ClansAPI.get(PageParams.of(1, 5)).getData().get(0));
	}
	
	public void testClanDetails() {
		int id = 1000008386;
		System.out.println(ClanAPI.get(id).getData().get(String.valueOf(id)));
	}
}
