/*******************************************************************************
 * Copyright (c) 2017 Elder Research, Inc.
 * All rights reserved.
 *******************************************************************************/
package com.elderresearch.wargaming;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;

import org.glassfish.jersey.logging.LoggingFeature.Verbosity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

/**
 * Configuration for the Wargaming client.
 * 
 * @author <a href="mailto:dimeo@elderresearch.com">John Dimeo</a>
 * @since Apr 9, 2019
 */
@Log4j2
@Getter
public class WargamingConfig {
	private static final ObjectMapper MAPPER = new YAMLMapper()
		.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
		.addMixIn(Level.class, LevelMixin.class);

	public interface LevelMixin {
		@JsonCreator public static Level parse(String s) {
			// Not actually called - Jackson calls Level.parse directly
			return null;
		}
	}
	
	private String url = "https://api.worldoftanks.com/";
	private String applicationId, accessToken, realm = "na";
	private Level logLevel = Level.FINE;
	private Verbosity logVerbosity = Verbosity.PAYLOAD_TEXT;
	
	private Map<String, Object> userConfig;
	
	public static WargamingConfig load(File file) {
		try {
			return MAPPER.readValue(file, WargamingConfig.class);
		} catch (IOException e) {
			log.warn("Error loading config from {}. Using defaults.", file, e);
			return new WargamingConfig();
		}
	}
	
	@Getter(lazy = true)
	private static final WargamingConfig config = newConfig();
	
	private static WargamingConfig newConfig() {
		return load(new File("wargaming.yaml"));
	}
}
