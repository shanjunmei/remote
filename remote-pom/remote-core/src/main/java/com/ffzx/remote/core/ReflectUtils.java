package com.ffzx.remote.core;

import java.lang.reflect.Method;

import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

public class ReflectUtils {
	public static final String CGLIB_TAG = "CGLIB$$";

	public static final String CGLIB_DELIMITER = "\\$\\$";

	private static final ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();

	/**
	 * 
	* @Title: fetchValidClass 
	* @Description: 获取对象的Class，排除 动态代理干扰 ，暂未考虑重复代理情况
	* @param  o
	* @return Class<?>    返回类型 
	* @throws
	 */
	public static Class<?> fetchValidClass(Object o) {
		String className = o.getClass().getName();
		if (className.contains(CGLIB_TAG)) {
			String[] array = className.split(CGLIB_DELIMITER);
			className = array[0];
		}
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 
	* @Title: getParameterNames 
	* @Description: 获取方法参数名 
	* @param  method
	* @return String[]    返回类型 
	* @throws
	 */
	public static String[] getParameterNames(Method method){
		String[] names=parameterNameDiscoverer.getParameterNames(method);
		return names;
	}
}
