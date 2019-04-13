package com.intland.eurocup.jms.frontend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.intland.eurocup.common.jms.model.BackendJmsMesage;
import com.intland.eurocup.controller.response.ResponseStorage;

@Component
public class JmsFrontendReceiver {
	
	@Autowired
	private ResponseStorage responseStorage;

	@JmsListener(destination = "${jms.queue.to.ui.name}")
    public void receiveMessage(final BackendJmsMesage voucher) {
        System.out.println("Received <" + voucher + ">");
        responseStorage.saveResponseIfIdExists(voucher);
    }
}
