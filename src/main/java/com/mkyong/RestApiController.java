package com.mkyong;

import java.util.Random;
import java.util.logging.Logger;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RestApiController {

	Logger logger = Logger.getLogger(RestApiController.class.getSimpleName());

	// save or update user
	@RequestMapping(value = "status", method = RequestMethod.GET)
	public String getStatus() {
		logger.info("hello");
		Random rand = new Random();

		// Obtain a number between [0 - 49].
		int n = rand.nextInt(50);
		if (n > 48) {
			return "stop";
		} else {
			return "cool";
		}
			
		
	}
}