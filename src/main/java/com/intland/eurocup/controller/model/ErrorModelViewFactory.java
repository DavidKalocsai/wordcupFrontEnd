package com.intland.eurocup.controller.model;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class ErrorModelViewFactory {
	private static final String MSG_KEY = "msg";
	private static final String ERROR_VIEW = "error";
	private static final String UNKNOWN_TERRITORY = "Invalid Path: Territory is unsupported!";
	private static final String UNKNOWN_ISSUE = "Sorry! Please try it later!";
	
	public ModelAndView getModelView(final ErrorModelViewType type) {
		final ModelAndView modelView;
		if (type == ErrorModelViewType.UNKNOWN_TERRITORY) {
			modelView = getErrorModelAndView(UNKNOWN_TERRITORY);
		}  else {
			modelView = getErrorModelAndView(UNKNOWN_ISSUE);
		}
		log.info(modelView);
		return modelView;		
	}
	
	private ModelAndView getErrorModelAndView(final String message) {
		final ModelAndView model = new ModelAndView(ERROR_VIEW);
		model.addObject(MSG_KEY, message);
		return model;
	}
		
	public enum ErrorModelViewType {
		UNKNOWN_TERRITORY(),
		OTHER		
	}
}
