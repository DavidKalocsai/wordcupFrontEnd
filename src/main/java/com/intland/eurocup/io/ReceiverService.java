package com.intland.eurocup.io;

import com.intland.eurocup.common.jms.model.MessageFromBackend;

public interface ReceiverService {
	void persist(MessageFromBackend message);
}
