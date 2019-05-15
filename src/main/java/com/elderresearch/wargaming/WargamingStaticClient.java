/*******************************************************************************
 * Copyright (c) 2017 Elder Research, Inc.
 * All rights reserved.
 *******************************************************************************/
package com.elderresearch.wargaming;

import java.util.logging.Level;

import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.logging.LoggingFeature.Verbosity;

import com.elderresearch.commons.lang.CachedSupplier;
import com.elderresearch.commons.lang.CachedSupplier.Result;
import com.elderresearch.commons.rest.client.RestClient;
import com.elderresearch.commons.rest.client.WebParam.WebTemplateParam;
import com.elderresearch.wargaming.WargamingConfig.WargamingOption;

import lombok.val;
import lombok.extern.java.Log;

@Log
public class WargamingStaticClient extends RestClient {
	private static final String BASE = "https://cwxstatic-{realm}.wargaming.net/v25/";
	
	WargamingStaticClient(Level level, Verbosity verbosity, String realm) {
		super(builderWithFeatures(new LoggingFeature(log, level, verbosity, LoggingFeature.DEFAULT_MAX_ENTITY_SIZE))
			.register(WargamingClient.JSON_PROVIDER));
		
		setBase(BASE);
		setPerpetualParams(WebTemplateParam.of("realm", realm));
	}
	
	@SuppressWarnings("resource")
	private static final CachedSupplier<WargamingStaticClient> INSTANCE = new CachedSupplier<>(() -> {
		val level       = WargamingOption.WARGAMING_API_LOG_LEVEL.asLoggingLevel();
		val verbosity   = WargamingOption.WARGAMING_API_LOG_VERBOSITY.getEnum(Verbosity.class);
		val realm       = WargamingOption.WARGAMING_API_REALM.get();
		
		return Result.completed(new WargamingStaticClient(level, verbosity, realm));
	});

	static void reset() {
		INSTANCE.get().close();
		INSTANCE.reset();
	}
	
	public static WargamingStaticClient staticClient() {
		return INSTANCE.get();
	}
}
