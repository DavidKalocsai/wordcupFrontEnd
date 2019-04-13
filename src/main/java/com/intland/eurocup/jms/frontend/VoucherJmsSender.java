package com.intland.eurocup.jms.frontend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.intland.eurocup.jms.model.VoucherFromUi;

@Service
public class VoucherJmsSender {
	
	@Value("${jms.queue.from.ui.name}")
	private String queueName;
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	public void send(final VoucherFromUi voucher) {
    	jmsTemplate.convertAndSend(queueName, voucher);
	}
}
