/*******************************************************************************
 * Copyright (c) 2017 Elder Research, Inc.
 * All rights reserved.
 *******************************************************************************/
package com.elderresearch.wargaming;

import org.glassfish.jersey.logging.LoggingFeature;

import com.elderresearch.commons.rest.client.RestClient;
import com.elderresearch.commons.rest.client.WebParam.WebTemplateParam;

import lombok.experimental.Accessors;
import lombok.extern.java.Log;

@Log
@Accessors(fluent = true)
public class WargamingStaticClient extends RestClient {
	private static final String BASE = "https://cwxstatic-{realm}.wargaming.net/v25/";
	
	WargamingStaticClient(WargamingConfig config) {
		super(builderWithFeatures(new LoggingFeature(log, config.getLogLevel(), config.getLogVerbosity(), LoggingFeature.DEFAULT_MAX_ENTITY_SIZE))
			.register(WargamingClient.JSON_PROVIDER));
		
		setBase(BASE);
		setPerpetualParams(WebTemplateParam.of("realm", config.getRealm()));
	}
}
