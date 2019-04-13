package com.intland.eurocup.controller;

import java.util.logging.Logger;

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

	Logger logger = Logger.getLogger(RestApiController.class.getSimpleName());

	// save or update user
	@RequestMapping(value = "status", method = RequestMethod.GET)
	public Response getStatus(final Long responseId) {
		logger.info("getStatus - " + responseId);
		return responseStorage.getResponse(responseId);
	}
}