/*******************************************************************************
 * Copyright (c) 2017 Elder Research, Inc.
 * All rights reserved.
 *******************************************************************************/
package com.elderresearch.wargaming;

import java.util.logging.Level;

import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.logging.LoggingFeature.Verbosity;

import com.elderresearch.commons.rest.client.RestClient;
import com.elderresearch.commons.rest.client.WebParam.WebTemplateParam;

import lombok.Getter;
import lombok.val;
import lombok.experimental.Accessors;
import lombok.extern.java.Log;

@Log
@Accessors(fluent = true)
public class WargamingStaticClient extends RestClient {
	private static final String BASE = "https://cwxstatic-{realm}.wargaming.net/v25/";
	
	WargamingStaticClient(Level level, Verbosity verbosity, String realm) {
		super(builderWithFeatures(new LoggingFeature(log, level, verbosity, LoggingFeature.DEFAULT_MAX_ENTITY_SIZE))
			.register(WargamingClient.JSON_PROVIDER));
		
		setBase(BASE);
		setPerpetualParams(WebTemplateParam.of("realm", realm));
	}
	
	@Getter(lazy = true)
	private static final WargamingStaticClient staticClient = newClient();
	
	private static WargamingStaticClient newClient() {
		val c = WargamingConfig.getConfig();
		return new WargamingStaticClient(c.getLogLevel(), c.getLogVerbosity(), c.getRealm());
	}
}
