package com.ffzx.remote.server;

import org.springframework.context.ApplicationContextAware;

public interface TargetLoader extends ApplicationContextAware{
	

	HandlerMethod load(String method);
	
}
