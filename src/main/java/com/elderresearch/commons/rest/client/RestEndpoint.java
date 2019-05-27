package com.elderresearch.commons.rest.client;

import javax.ws.rs.client.Invocation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class RestEndpoint {
	private RestClient client;
	private RecursiveTarget target;
	
	public Invocation.Builder request(WebParam... params) {
		return client.request(target, params);
	}
}
