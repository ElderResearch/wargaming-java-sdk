/*******************************************************************************
 * Copyright (c) 2017 Elder Research, Inc.
 * All rights reserved.
 *******************************************************************************/
package com.elderresearch.commons.rest.client;

import java.util.function.Function;
import java.util.stream.Stream;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Feature;

import org.apache.logging.log4j.jul.LogManager;
import org.jooq.lambda.Seq;
import org.jooq.lambda.tuple.Tuple;

import lombok.val;

public class RestClient implements AutoCloseable {
	public static void bridgeLogging() {
		System.setProperty("java.util.logging.manager", LogManager.class.getName());
	}
	static { bridgeLogging(); }
	
	private Client client;
	
	private WebTarget base;
	private WebParam[] perpetualParams;
	
	public RestClient(ClientBuilder cb) {
		client = cb.build();
		setPerpetualParams();
	}
	
	public <T> T withClient(Function<Client, T> fn) {
		return fn.apply(client);
	}
	
	public RestClient setBase(WebTarget base) {
		this.base = base;
		return this;
	}
	
	public RestClient setBase(String base) {
		return setBase(client.target(base));
	}
	
	public RestClient addPerpetualParams(WebParam... perpetualParams) {
		return setPerpetualParams(WebParam.add(this.perpetualParams, perpetualParams));
	}
	
	public RestClient setPerpetualParams(WebParam... perpetualParams) {
		this.perpetualParams = perpetualParams;
		return this;
	}
	
	public Invocation.Builder request(RecursiveTarget target, WebParam... params) {
		// If a request parameter has the same type and name as a perpetual param, let it override the param and don't
		// apply them twice
		val allParams = Seq.concat(Seq.of(params), Seq.of(perpetualParams))
				.distinct(p -> Tuple.tuple(p.getClass(), p.getName()))
				.toList();
		
		WebTarget wt = target.resolve(base);
		for (val p : allParams) { wt = p.applyToTarget(wt); }
		
		Invocation.Builder rb = wt.request();
		for (val p : allParams) { rb = p.applyToRequest(rb); }
		
		return rb;
	}
	
	@Override
	public void close() {
		client.close();
	}
	
	protected static ClientBuilder builderWithFeatures(Feature... features) {
		val ret = ClientBuilder.newBuilder();
		// Allow https over IP addresses. While this is a potential security vulnerability, users of this code are
		// trusted and likely know what they are doing (e.g. forced to use IP address due to firewall rules).
		ret.hostnameVerifier((host, session) -> true);
		Stream.of(features).forEach(ret::register);
		return ret;
	}
}
