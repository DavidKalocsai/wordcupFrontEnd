package com.intland.eurocup.controller.model;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

@Service
public class ModelViewFactory {
	private static final String CSS_KEY = "css";
	private static final String CSS_DANGER = "danger";
	private static final String CSS_SUCCESS = "success";
	
	private static final String MSG_KEY = "msg";
	private static final String MSG_VALIDATION_ISSUE = "Validation failed!";
	private static final String MSG_SUBMIT = "Voucher redeem request submitted!";		
	
	private static final String VIEW_FORM = "voucherForm";
	private static final String VIEW_STATUS = "statusform";
	
	public ModelAndView getModelView(final boolean validationPassed) {
		ModelAndView alertMap;
		if (validationPassed) {
			alertMap = new ModelAndView(VIEW_STATUS);
			alertMap.addObject(CSS_KEY, CSS_SUCCESS);
			alertMap.addObject(MSG_KEY, MSG_SUBMIT);
		} else {
			alertMap = new ModelAndView(VIEW_FORM);
			alertMap.addObject(CSS_KEY, CSS_DANGER);
			alertMap.addObject(MSG_KEY, MSG_VALIDATION_ISSUE);
		}
		return alertMap;
		
	}
}
