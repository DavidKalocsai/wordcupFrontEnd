package com.intland.eurocup.controller.id;

import java.util.concurrent.atomic.AtomicLong;

public class UniqueRequestIdGenerator {
	private AtomicLong id = new AtomicLong(0);
	
	public Long getNext() {
		return id.incrementAndGet();
	}
}
