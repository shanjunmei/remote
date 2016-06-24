package com.ffzx.remote.client;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.ffzx.remote.core.JsonMapper;

@Component
public class RemoteInvokerImpl implements RemoteInvoker {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private RabbitTemplate rabbitTemplate;

	@Override
	public <T> T send(InternalRequest request) {
		MessageProperties messageProperties = new MessageProperties();
		byte[] body = null;
		body = JsonMapper.toJsonBytes(request.getMessage());
		Message message = new Message(body, messageProperties);
		if (request.getReturnType() == null || Void.TYPE == request.getReturnType()) {
			rabbitTemplate.send(request.getExchange(), request.getRouteKey(), message);
		} else {
			Message responseMsg = rabbitTemplate.sendAndReceive(request.getExchange(), request.getRouteKey(), message);
			return JsonMapper.from(responseMsg.getBody(), request.getReturnType());
		}
		return null;
	}

}
