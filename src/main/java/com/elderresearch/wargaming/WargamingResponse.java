package com.elderresearch.wargaming;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.GenericType;

import lombok.Getter;

public abstract class WargamingResponse<T> {	
	@Getter
	private String status;
	@Getter
	private Meta meta;
	
	@Getter
	public static class Meta {
		private Integer count, total, page, pageTotal;
	}
	
	public static class WithMap<T> extends WargamingResponse<T> {
		@Getter
		private Map<String, T> data;
		
		@SafeVarargs
		public static <T> GenericType<WithMap<T>> forType(Class<T>... t) {
			return new GenericType<>(new ParameterizedType() {
				@Override public Type getRawType() { return WithMap.class; }
				@Override public Type getOwnerType() { return WithMap.class; }
				@Override public Type[] getActualTypeArguments() { return t; }
			});
		}
	}
	
	public static class WithList<T> extends WargamingResponse<T> {
		@Getter
		private List<T> data;
		
		@SafeVarargs
		public static <T> GenericType<WithList<T>> forType(Class<T>... t) {
			return new GenericType<>(new ParameterizedType() {
				@Override public Type getRawType() { return WithList.class; }
				@Override public Type getOwnerType() { return WithList.class; }
				@Override public Type[] getActualTypeArguments() { return t; }
			});
		}
	}
	
	public static class WithMapOfLists<T> extends WargamingResponse<T> {
		@Getter
		private Map<String, List<T>> data;
		
		@SafeVarargs
		public static <T> GenericType<WithMapOfLists<T>> forType(Class<T>... t) {
			return new GenericType<>(new ParameterizedType() {
				@Override public Type getRawType() { return WithMapOfLists.class; }
				@Override public Type getOwnerType() { return WithMapOfLists.class; }
				@Override public Type[] getActualTypeArguments() { return t; }
			});
		}
	}
}
