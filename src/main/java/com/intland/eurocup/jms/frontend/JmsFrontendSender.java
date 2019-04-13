package com.intland.eurocup.jms.frontend;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.intland.eurocup.common.jms.model.FrontendJmsMessage;
import com.intland.eurocup.controller.WelcomeController;
import com.intland.eurocup.controller.response.ResponseStorage;

@Service
public class JmsFrontendSender {
	Logger logger = Logger.getLogger(WelcomeController.class.getSimpleName());
	
	@Value("${jms.queue.from.ui.name}")
	private String queueName;
	
	@Autowired
	private ResponseStorage responseStorage;
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	public void send(final FrontendJmsMessage voucher) {
			logger.info("Frontend send - " + voucher);
			responseStorage.registerRequestId(voucher.getRequestId());
    	jmsTemplate.convertAndSend(queueName, voucher);
	}
}
