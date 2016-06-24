package com.ffzx.remote.server;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.ffzx.remote.core.Command;
import com.ffzx.remote.core.ReflectUtils;

@Component
public class AnnotationTargetLoader implements TargetLoader {

	Logger logger = LoggerFactory.getLogger(getClass());

	private Map<String, HandlerMethod> handlerMethods = new HashMap<>();



	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		detectHandleMethods(applicationContext);
		// if (logger.isDebugEnabled()) {
		for (Entry<String, HandlerMethod> e : handlerMethods.entrySet()) {
			String registe_info = e.getKey() + " registed to :" + e.getValue();
			logger.info(registe_info);
		}
		// }
	}

	@Override
	public HandlerMethod load(String method) {
		return handlerMethods.get(method);
	}

	/**
	 * 
	 * <p>
	 * 
	 * 请求方法注册
	 *
	 * </p>
	 * 
	 * @param applicationContext
	 * 
	 * @author hz15101769
	 * @date 2015年11月6日 下午4:12:31
	 * @version
	 */
	public void detectHandleMethods(ApplicationContext applicationContext) {

		Map<String, Object> beansCache = applicationContext.getBeansWithAnnotation(Component.class);
		for (Entry<String, Object> e : beansCache.entrySet()) {
			Object target = e.getValue();
			Class<?> validClass = ReflectUtils.fetchValidClass(target);// 获取代理类的真实class

			if(validClass!=null){
				String baseMapping = "";
				Command base = validClass.getAnnotation(Command.class);
				if (base != null) {
					baseMapping = base.value();
				}
				Method[] methods = findMethodByAnnotation(validClass, Command.class);
				for (Method method : methods) {
					handleMethod(target, method, baseMapping);

					// TODO 向服务中心注册
				}
			}

		}
	}

	public void handleMethod(Object target, Method method, String baseMapping) {

		Command reqmapping = method.getAnnotation(Command.class);
		String urlMappling =method.getDeclaringClass().getName()+"."+method.getName();
		if (StringUtils.isNotBlank(reqmapping.value())) {
			urlMappling = reqmapping.value();
		}

		urlMappling = baseMapping + urlMappling;

		if (handlerMethods.containsKey(urlMappling)) {
			throw new RuntimeException("mapping " + urlMappling + " has registed");
		}

		HandlerMethod targetBean = new HandlerMethod();
		targetBean.setTargetMethod(method);
		targetBean.setTarget(target);
		String[] pnames = ReflectUtils.getParameterNames(method);
		Class<?>[] ptypes = method.getParameterTypes();
		List<Parameter> mp = new ArrayList<Parameter>();

		Parameter pb = null;
		for (int i = 0; i < pnames.length; i++) {
			pb = new Parameter();
			pb.setName(pnames[i]);
			pb.setType(ptypes[i]);
			mp.add(pb);
		}
		targetBean.setParameters(mp);

		handlerMethods.put(urlMappling, targetBean);
	}



	public Method[] findMethodByAnnotation(Class<?> cls, Class<? extends Annotation> annotation) {
		java.lang.reflect.Method[] methods = cls.getMethods();

		List<Method> methodList = new ArrayList<Method>();

		for (Method method : methods) {
			Annotation t = method.getAnnotation(annotation);
			if (t != null) {
				methodList.add(method);
			}
		}
		return methodList.toArray(new Method[methodList.size()]);
	}

}
