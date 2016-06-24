package com.ffzx.remote.client;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.Advised;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.ffzx.remote.core.ReflectUtils;

@Component
public class ServerLoader implements ApplicationContextAware {
	

	
	@Resource
	private RemoteProxy remoteProxy;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		Map<String, Object> beansCache = applicationContext.getBeansWithAnnotation(Component.class);
		for (Entry<String, Object> e : beansCache.entrySet()) {
			Object target = e.getValue();
			Class<?> tc = ReflectUtils.fetchValidClass(target);
			Field[] fields = findByAnnotation(tc);
			if(fields!=null){
				for (Field field : fields) {
					Class<?> i=field.getType();
					Object proxy=AbstractProxyFactory.buildJdkProxy(i, remoteProxy);
					
					
					boolean flag=field.isAccessible();
					field.setAccessible(true);
					try {
						System.out.println(target);
						if(target instanceof Advised){
							TargetSource o=((Advised) target).getTargetSource();
							field.set(o.getTarget(), proxy);;
							
						}
						field.set(target, proxy);
						field.setAccessible(flag);
					} catch (Exception e1) {
						e1.printStackTrace();
					} 
				}
			}
		}
	}

	private Field[] findByAnnotation(Class<?> cls) {
		Field[] fields = cls.getDeclaredFields();

		List<Field> retFields = new ArrayList<>();
		if (fields != null) {
			for (Field field : fields) {
				if (field.getAnnotation(Remote.class) != null) {
					retFields.add(field);
				}
			}
		}
		return retFields.toArray(new Field[retFields.size()]);
	}
}
