package com.intland.eurocup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.intland.eurocup.controller.response.ResponseStorage;
import com.intland.eurocup.model.Response;

@RestController
@RequestMapping("/api")
public class RestApiController {
	
	@Autowired
	private ResponseStorage responseStorage;

	@RequestMapping(value = "status", method = RequestMethod.GET)
	public Response fetchResponse(final Long responseId) {
		return responseStorage.getResponse(responseId);
	}
}