package com.intland.eurocup.jms.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.intland.eurocup.common.jms.model.MessageFromFrontend;
import com.intland.eurocup.controller.response.ResponseStorage;
import com.intland.eurocup.jms.JmsSender;
import com.intland.eurocup.model.Voucher;

@Component
public class AdapterSenderService {
	@Autowired
	private ResponseStorage responseStorage;
	
	@Autowired
	private MessageConverter messageConverter;

	@Autowired
	private JmsSender sender;

	public void send(final Long requestId, final Voucher voucher) {
		final MessageFromFrontend message = messageConverter.convert(requestId, voucher);
		responseStorage.registerRequestId(requestId);
		sender.send(message);
	}
}
