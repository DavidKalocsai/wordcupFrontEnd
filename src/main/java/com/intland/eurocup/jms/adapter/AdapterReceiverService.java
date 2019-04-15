package com.intland.eurocup.jms.adapter;

import org.springframework.beans.factory.annotation.Autowired;

import com.intland.eurocup.common.jms.model.MessageFromBackend;
import com.intland.eurocup.controller.response.ResponseStorage;

public class AdapterReceiverService {
	@Autowired
	private ResponseStorage responseStorage;
	
	@Autowired
	private MessageConverter messageConverter;

	public void persist(final MessageFromBackend message) {
		responseStorage.saveResponseIfRequestIdRegistered(message.getRequestId(), messageConverter.convert(message));
	}
}
