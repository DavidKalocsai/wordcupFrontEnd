package com.intland.eurocup.io.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.intland.eurocup.common.jms.model.MessageFromBackend;
import com.intland.eurocup.io.ReceiverService;

import lombok.extern.log4j.Log4j;

@Log4j
@Component
public class JmsReceiver {
	
	@Autowired
	private ReceiverService receiverService;
	
	@JmsListener(destination = "${jms.queue.to.ui.name}")
	public void receiveMessage(final MessageFromBackend message) {
		log.info("Received <" + message + ">");
		receiverService.persist(message);
	}
}
