/*******************************************************************************
 * Copyright (c) 2017 Elder Research, Inc.
 * All rights reserved.
 *******************************************************************************/
package com.elderresearch.wargaming;

import java.util.logging.Level;

import org.glassfish.jersey.logging.LoggingFeature.Verbosity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Configuration for the Wargaming client.
 * 
 * @author <a href="mailto:dimeo@elderresearch.com">John Dimeo</a>
 * @since Apr 9, 2019
 */
@Getter
@Setter @Accessors(chain = true)
public class WargamingConfig {
	@Getter
	private static final ObjectMapper mapper = new YAMLMapper()
		.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
		.addMixIn(Level.class, LevelMixin.class);

	interface LevelMixin {
		@JsonCreator public static Level parse(String s) {
			// Not actually called - Jackson calls Level.parse directly
			return null;
		}
	}
	
	private String applicationId, accessToken;
	
	private String url = "https://api.worldoftanks.com/", realm = "na";
	private Level logLevel = Level.FINE;
	private Verbosity logVerbosity = Verbosity.PAYLOAD_TEXT;
	
	public WorldOfTanksAPI wot() { return new WorldOfTanksAPI(this); }
}
