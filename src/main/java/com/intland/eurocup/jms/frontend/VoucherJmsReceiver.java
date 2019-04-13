package com.intland.eurocup.jms.frontend;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.intland.eurocup.jms.model.VoucherToUi;

@Component
public class VoucherJmsReceiver {

	@JmsListener(destination = "${jms.queue.to.ui.name}")
    public void receiveMessage(final VoucherToUi voucher) {
        System.out.println("Received <" + voucher + ">");
    }
}
