package com.ffzx.remote.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * 
 * @ClassName: AbstractProxyFactory
 * @Description: 代理工厂
 * @author 李淼淼 445052471@qq.com
 * @date 2016年6月1日 上午9:20:59
 */
public abstract class AbstractProxyFactory {

	@SuppressWarnings("unchecked")
	public static <T> T buildJdkProxy(Class<T> interfaces, final InvocationHandler methodInterceptor) {
		return (T) Proxy.newProxyInstance(interfaces.getClassLoader(), new Class[] { interfaces }, methodInterceptor);

	}

}