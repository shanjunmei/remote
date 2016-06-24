package com.ffzx.remote.client;

import java.lang.reflect.Type;

import com.ffzx.remote.core.CommonMessage;

public class InternalRequest {

	// exchange 路由名
	private String exchange;
	// routeKey 队列名
	private String routeKey;

	private CommonMessage message;

	private Type returnType;

	public String getExchange() {
		return exchange;
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
	}

	public String getRouteKey() {
		return routeKey;
	}

	public void setRouteKey(String routeKey) {
		this.routeKey = routeKey;
	}

	public CommonMessage getMessage() {
		return message;
	}

	public void setMessage(CommonMessage message) {
		this.message = message;
	}

	public Type getReturnType() {
		return returnType;
	}

	public void setReturnType(Type returnType) {
		this.returnType = returnType;
	}
	
	
}
