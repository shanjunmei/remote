package com.ffzx.remote.server;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

import com.ffzx.remote.core.CommonMessage;
import com.ffzx.remote.core.JsonMapper;

@Component
public class MqMessageListener implements MessageListener {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private TargetLoader targetLoader;

	@Override
	public void onMessage(Message message) {

		String body = new String(message.getBody());
		logger.info(body);
		CommonMessage msg = JsonMapper.from(body, CommonMessage.class);
		HandlerMethod method = targetLoader.load(msg.getCommand());
		Object[] parameter = parameterConvert(method, msg.getData().toString());
		try {
			method.getTargetMethod().invoke(method.getTarget(), parameter);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	public Object[] parameterConvert(HandlerMethod target, String source) {
		Object[] ps = new Object[0];// default target method parameter
		List<Parameter> pt = target.getParameters();
		if (pt.size() > 0) {
			Map<String, Object> paramMap = JsonMapper.from(source, Map.class);
			ps = convert(pt, paramMap);
		}
		return ps;
	}

	public Object[] convert(List<Parameter> pt, Map<String, Object> paramMap) {
		Object[] ps;
		ps = new Object[pt.size()];
		for (int i = 0; i < pt.size(); i++) {
			Parameter pb = pt.get(i);
			if (TypeUtils.isWrapperType(pb.getType())) {
				ps[i] = paramMap.get(pb.getName());
			} else {
				ps[i] = JsonMapper.convertVal(paramMap, pb.getType());
			}
		}
		return ps;
	}

}
