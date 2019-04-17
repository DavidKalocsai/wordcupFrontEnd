package com.intland.eurocup.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.intland.eurocup.common.jms.model.MessageFromBackend;
import com.intland.eurocup.jms.adapter.AdapterReceiverService;

@Component
public class JmsReceiver {
	private Logger logger = LoggerFactory.getLogger(JmsReceiver.class);
	
	@Autowired
	private AdapterReceiverService adapterService;

	@JmsListener(destination = "${jms.queue.to.ui.name}")
	public void receiveMessage(final MessageFromBackend message) {
		logger.info("Received <" + message + ">");
		adapterService.persist(message);
	}
}
