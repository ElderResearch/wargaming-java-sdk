/*******************************************************************************
 * Copyright (c) 2017 Elder Research, Inc.
 * All rights reserved.
 *******************************************************************************/
package com.elderresearch.wargaming;

import java.util.TimeZone;

import org.glassfish.jersey.logging.LoggingFeature;

import com.elderresearch.commons.rest.client.RestClient;
import com.elderresearch.commons.rest.client.WebParam.WebQueryParam;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import lombok.val;
import lombok.experimental.Accessors;
import lombok.extern.java.Log;

@Log
@Accessors(fluent = true)
public class WargamingClient extends RestClient {
	static final JacksonJsonProvider JSON_PROVIDER;
	static {
		// Mapping to APIs is not exhaustive in some cases- don't throw an error
		val om = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		// Jackson defaults to UTC, which means dates are converted to the timezone of the server running the client.
		// Set the default to the system default so dates are left unconverted.
		om.setTimeZone(TimeZone.getDefault());
		
		// Set default naming strategy to snake so we avoid this annotation on all domain classes
		om.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
		
		// When putting updated objects, we commonly will leave some fields null- don't send those so they are unchanged
		om.setDefaultPropertyInclusion(Include.NON_NULL);
		
		JSON_PROVIDER = new JacksonJaxbJsonProvider();
		JSON_PROVIDER.setMapper(om);
	}
	
	WargamingClient(WargamingConfig config) {
		super(builderWithFeatures(new LoggingFeature(log, config.getLogLevel(), config.getLogVerbosity(), LoggingFeature.DEFAULT_MAX_ENTITY_SIZE))
			.register(JSON_PROVIDER));
		
		setBase(config.getUrl());
		setPerpetualParams(
			WebQueryParam.of("application_id", config.getApplicationId()),
			WebQueryParam.of("access_token", config.getAccessToken())
		);
	}
}
