package com.intland.eurocup.controller.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.intland.eurocup.controller.id.UniqueRequestIdGenerator;
import com.intland.eurocup.model.Voucher;

@Service
public class ModelViewFactory {
	private static final String CSS_KEY = "css";
	private static final String CSS_DANGER = "danger";
	private static final String CSS_SUCCESS = "success";
	
	private static final String MSG_KEY = "msg";
	private static final String MSG_VALIDATION_ISSUE = "Validation failed!";
	private static final String MSG_SUBMIT = "Voucher redeem request submitted!";		
	
	private static final String FORM_VIEW = "voucherForm";
	private static final String STATUS_VIEW = "statusform";
	private static final String ERROR_VIEW = "error";
	
	private static final String FORM_DATA = "formData";
	
	public ModelAndView getModelView(final ModelViewType type) {
		final ModelAndView modelView;
		if (type == ModelViewType.VOUCHER_FORM_VIEW) {
			modelView = new ModelAndView(FORM_VIEW);
			modelView.addObject(FORM_DATA, getDefaultVoucher());
		} else if (type == ModelViewType.STATUS_VIEW) {
			modelView = new ModelAndView(STATUS_VIEW);
			modelView.addObject(CSS_KEY, CSS_SUCCESS);
			modelView.addObject(MSG_KEY, MSG_SUBMIT);
		} else if (type == ModelViewType.VOUCHER_FORM_VIEW_ISSUE) {
			modelView = new ModelAndView(FORM_VIEW);
			modelView.addObject(CSS_KEY, CSS_DANGER);
			modelView.addObject(MSG_KEY, MSG_VALIDATION_ISSUE);
		}  else {
			modelView = new ModelAndView(ERROR_VIEW);
		}
		return modelView;		
	}
	
	private Voucher getDefaultVoucher() {
		final Voucher voucher = new Voucher();
		voucher.setCode("ABCDEF1234");
		voucher.setEmail("test@gmail.com");
		return voucher;
	}
	
	public enum ModelViewType {
		VOUCHER_FORM_VIEW,
		STATUS_VIEW,
		VOUCHER_FORM_VIEW_ISSUE,
		ERROR		
	}
}
