/*******************************************************************************
 * Copyright (c) 2017 Elder Research, Inc.
 * All rights reserved.
 *******************************************************************************/
package com.elderresearch.commons.rest.client;

import java.util.Optional;

import javax.ws.rs.client.WebTarget;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter(AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class RecursiveTarget {
	private RecursiveTarget parent;
	private String path;
	
	public RecursiveTarget append(String addToPath) {
		return sibling(path + addToPath);
	}
	public RecursiveTarget sibling(String p) {
		return new RecursiveTarget(parent, p);
	}
	public RecursiveTarget child(String p) {
		return new RecursiveTarget(this, p);
	}
	
	public WebTarget resolve(WebTarget base) {
		return Optional.ofNullable(parent).map($ -> $.resolve(base)).orElse(base).path(path);
	}
	
	public static RecursiveTarget target(RecursiveTarget parent, String path) {
		return new RecursiveTarget(parent, path);
	}
	public static RecursiveTarget target(String path) {
		return target(null, path);
	}
}
