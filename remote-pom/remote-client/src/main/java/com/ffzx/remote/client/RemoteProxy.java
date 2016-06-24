package com.ffzx.remote.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.ffzx.remote.core.Command;
import com.ffzx.remote.core.CommonMessage;

@Component
public class RemoteProxy implements InvocationHandler {

	@Resource
	private RemoteInvoker invoker;

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

		CommonMessage message=new CommonMessage();
		if(args.length==1){
			message.setData(args[0]);
		}else{
			message.setData(args);
		}
		Class<?> target=method.getDeclaringClass();
		Remote clsRemote=target.getAnnotation(Remote.class);
		Remote remote=method.getAnnotation(Remote.class);
		String exchange=clsRemote.exchange();
		String routeKey=clsRemote.routeKey();
		if (remote != null) {
			if (StringUtils.isNoneBlank(remote.exchange())) {
				exchange = remote.exchange();
			}
			if (StringUtils.isNoneBlank(remote.routeKey())) {
				routeKey = remote.routeKey();
			}
		}
		Command command=method.getAnnotation(Command.class);
		String methodName="";
		if(StringUtils.isNoneBlank(command.value())){
			methodName=command.value();
		}else{
			methodName=target.getName();
			if(methodName.contains(".client")){
				methodName=methodName.replace(".client", "");
			}
			methodName=methodName+"."+method.getName();
		}		
		
		message.setCommand(methodName);
		
		
		InternalRequest request=new InternalRequest();
		request.setExchange(exchange);
		request.setRouteKey(routeKey);
		request.setReturnType(method.getGenericReturnType());
		request.setMessage(message);
		Object ret=invoker.send(request);
		return ret;
	}

}
