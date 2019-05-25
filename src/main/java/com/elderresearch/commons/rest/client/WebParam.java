/*******************************************************************************
 * Copyright (c) 2017 Elder Research, Inc.
 * All rights reserved.
 *******************************************************************************/
package com.elderresearch.commons.rest.client;

import javax.ws.rs.client.Invocation.Builder;

import java.util.Arrays;

import javax.ws.rs.client.WebTarget;

import org.jooq.lambda.Seq;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.val;

public interface WebParam {
	String getName();
	WebTarget applyToTarget(WebTarget t);
	Builder applyToRequest(Builder b);
	
	interface WebTargetParam extends WebParam {
		@Override
		default Builder applyToRequest(Builder b) { return b; }
	}
	
	interface WebRequestParam extends WebParam {
		@Override
		default WebTarget applyToTarget(WebTarget t) { return t; }
	}
	
	@Getter
	class WebParamGroup implements WebParam {
		private WebParam[] params;
		
		protected WebParamGroup(WebParam... params) {
			this.params = params;
		}
		
		@Override
		public Builder applyToRequest(Builder b) {
			for (val p : params) { b = p.applyToRequest(b); }
			return b;
		}
		
		@Override
		public WebTarget applyToTarget(WebTarget t) {
			for (val p : params) { t = p.applyToTarget(t); }
			return t;
		}
		
		public static WebParamGroup of(WebParam... params) {
			return new WebParamGroup(params);
		}
		
		public static WebParamGroup of(WebParam[] arr, WebParam... others) {
			return of(add(arr, others));
		}
		
		@Override
		public String getName() {
			return Seq.of(params).map(WebParam::getName).toString(", ", "[", "]");
		}
	}
	
	@Getter
	@AllArgsConstructor(staticName = "of")
	class WebTemplateParam implements WebTargetParam {
		private String name;
		private Object value;
		
		@Override
		public WebTarget applyToTarget(WebTarget t) { return t.resolveTemplate(name, value); }
	}
	
	@Getter
	class WebQueryParam implements WebTargetParam {
		private String name;
		private Object[] values;
		
		protected WebQueryParam(String name, Object... values) {
			this.name = name;
			this.values = values;
		}
		
		@Override
		public WebTarget applyToTarget(WebTarget t) { return t.queryParam(name, values); }
		
		public static WebQueryParam of(String name, Object... values) {
			return new WebQueryParam(name, values);
		}
	}
	
	@Getter
	@AllArgsConstructor(staticName = "of")
 	class WebProperty implements WebRequestParam {
		private String name;
		private Object value;
		
		@Override
		public Builder applyToRequest(Builder t) { return t.property(name, value); }
	}
	
	@Getter
	@AllArgsConstructor(staticName = "of")
	@Setter(AccessLevel.PROTECTED)
	class WebHeader implements WebRequestParam {
		private String name;
		private Object value;
		
		@Override
		public Builder applyToRequest(Builder t) { return t.header(name, value); }
	}
	
	static WebParam[] add(WebParam[] arr, WebParam... others) {
		val ret = Arrays.copyOf(arr, arr.length + others.length);
		System.arraycopy(others, 0, ret, arr.length, others.length);
		return ret;
	}
}
