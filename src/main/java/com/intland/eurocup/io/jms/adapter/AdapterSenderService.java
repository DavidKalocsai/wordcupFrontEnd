package com.intland.eurocup.io.jms.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.intland.eurocup.common.jms.model.MessageFromFrontend;
import com.intland.eurocup.controller.response.ResponseStorage;
import com.intland.eurocup.io.SenderService;
import com.intland.eurocup.io.jms.JmsSender;
import com.intland.eurocup.model.Voucher;

/**
 * Adapter between JMS and the application. Converts voucher into outgoing message and passes message to JMS.
 */
@Component
@Profile("default")
public class AdapterSenderService implements SenderService {
	@Autowired
	private ResponseStorage responseStorage;
	
	@Autowired
	private MessageConverter messageConverter;

	@Autowired
	private JmsSender sender;

	@Override
	public void send(final Voucher voucher) {
		final MessageFromFrontend message = messageConverter.convert(voucher);
		responseStorage.registerRequestId(voucher.getId());
		sender.send(message);
	}
}
