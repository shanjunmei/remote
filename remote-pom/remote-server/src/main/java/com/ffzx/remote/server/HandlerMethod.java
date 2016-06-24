package com.ffzx.remote.server;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 
 * @ClassName: HandMethod
 * @Description: 目标方法
 * @author 李淼淼 445052471@qq.com
 * @date 2016年5月31日 上午9:31:58
 */
public class HandlerMethod {

	private Object target;

	private Method targetMethod;

	private List<Parameter> parameters;

	public Object getTarget() {
		return target;
	}

	public void setTarget(Object target) {
		this.target = target;
	}

	public Method getTargetMethod() {
		return targetMethod;
	}

	public void setTargetMethod(Method targetMethod) {
		this.targetMethod = targetMethod;
	}

	public List<Parameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}

	@Override
	public String toString() {
		return "HandlerMethod [target=" + target + ", targetMethod=" + targetMethod + ", parameters=" + parameters
				+ "]";
	}
	
	

}
