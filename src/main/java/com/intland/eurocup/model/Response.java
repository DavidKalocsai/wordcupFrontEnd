package com.intland.eurocup.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Response {
	private ResponseStatus status;
	private String message;
		
	public Response() {
		status = ResponseStatus.NO;
		message = "";		
	}
}
