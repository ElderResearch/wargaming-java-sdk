/*******************************************************************************
 * Copyright (c) 2017 Elder Research, Inc.
 * All rights reserved.
 *******************************************************************************/
package com.elderresearch.wargaming;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;

import org.glassfish.jersey.logging.LoggingFeature.Verbosity;

import com.elderresearch.commons.lang.CachedSupplier;
import com.elderresearch.commons.lang.CachedSupplier.Result;
import com.elderresearch.commons.lang.PropertiesKey;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.val;
import lombok.experimental.Accessors;
import lombok.experimental.UtilityClass;
import lombok.extern.java.Log;

/**
 * Configuration for the Wargaming client. See {@link WargamingOption} for the available configuration keys. There are
 * four ways of setting these keys:<ol>
 * <li>Using a .properties file named <tt>wargaming.properties</tt> in the current directory</li>
 * <li>Using a .properties file at a path/location you specify via {@link #setConfigFile(String)}</li>
 * <li>Using system properties (e.g. <tt>-Dwargaming_api_key=mykey</tt>)</li>
 * <li>Using environment variables (e.g. <tt>WARGAMING_API_KEY=mykey</tt>)</li>
 * </ol>
 * 
 * @author <a href="mailto:dimeo@elderresearch.com">John Dimeo</a>
 * @since Apr 9, 2019
 */
@Log
@UtilityClass
public class WargamingConfig {
	@NoArgsConstructor
	@AllArgsConstructor
	@Getter
	@Accessors(fluent = true)
	public enum WargamingOption implements PropertiesKey {
		WARGAMING_API_URL("https://api.worldoftanks.com/wot/"),
		WARGAMING_API_KEY,
		WARGAMING_API_LOG_LEVEL,
		WARGAMING_API_LOG_VERBOSITY(Verbosity.PAYLOAD_TEXT);
		
		private Object defVal;
		
		@Override
		public Properties props() { return PROPS.get(); }
		
		@Override
		public void set(String val) {
			PropertiesKey.super.set(val);
			WargamingClient.reset();
		}
	}
	
	private String configFile = "wargaming.properties";
	
	public void setConfigFile(String file) {
		configFile = file;
		PROPS.reset();
		WargamingClient.reset();
	}
	
	private final CachedSupplier<Properties> PROPS = new CachedSupplier<>(() -> {
		val ret = new Properties();
		try {
			PropertiesKey.load(ret, configFile, WargamingOption.values());
		} catch (IOException e) {
			log.log(Level.WARNING, "Error loading Wargaming configuration from " + configFile, e);
		}
		return Result.completed(ret);
	});
}
