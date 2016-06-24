package com.ffzx.remote.core;

import java.io.IOException;
import java.lang.reflect.Type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonMapper {
	private static Logger logger = LoggerFactory.getLogger(JsonMapper.class);

	private JsonMapper() {
	}

	private static class Holder {
		private final static ObjectMapper objectMapper = new ObjectMapper();
	}

	public static ObjectMapper getInstance() {
		ObjectMapper objectMapper = Holder.objectMapper;
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		return objectMapper;
	}

	public static <T> T from(String src, Class<T> cls) {
		try {
			return getInstance().readValue(src, cls);
		} catch (IOException e) {
			logger.warn("parse json string error:" + src, e);
			return null;
		}
	}

	public static <T> T from(byte[] src, Class<T> cls) {
		try {
			return getInstance().readValue(src, cls);
		} catch (IOException e) {
			logger.warn("parse json string error:" + src, e);
			return null;
		}
	}

	public static <T> T from(byte[] src, Type type) {
		try {
			JavaType javaType = getInstance().getTypeFactory().constructType(type);
			return getInstance().readValue(src, javaType);
		} catch (IOException e) {
			logger.warn("parse json string error:" + src, e);
			return null;
		}
	}

	public static String toJsonString(Object o) {
		try {
			return getInstance().writeValueAsString(o);
		} catch (JsonProcessingException e) {
			logger.warn("write to json string error:" + o, e);
			return null;
		}
	}

	public static byte[] toJsonBytes(Object o) {
		try {
			return getInstance().writeValueAsBytes(o);
		} catch (JsonProcessingException e) {
			logger.warn("write to json string error:" + o, e);
			return null;
		}
	}
	
	/**
	 * 
	 * @Title: convertVal @Description: 类型转换 @param @param src @param @param
	 * dest @param @return    设定文件 @return T    返回类型 @throws
	 */
	public static <T> T convertVal(Object src, Class<T> dest) {
		return JsonMapper.getInstance().convertValue(src, dest);

	}
}
