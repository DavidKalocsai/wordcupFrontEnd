
package com.intland.eurocup.controller.model;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.intland.eurocup.controller.exception.UnsupportedModelViewTypeException;
import com.intland.eurocup.model.Voucher;

@Service
public class ModelViewFactory {
	private static final String CSS_KEY = "css";
	private static final String CSS_SUCCESS = "success";
	
	private static final String MSG_KEY = "msg";
	private static final String MSG_SUBMIT = "Voucher redeem request submitted!";		
	
	private static final String FORM_VIEW = "submitform";
	private static final String STATUS_VIEW = "statusform";
	
	private static final String FORM_DATA = "voucher";
	
	public ModelAndView getModelView(final ModelViewType type, final Voucher voucher) {
		final ModelAndView modelView;
		if (type == ModelViewType.VOUCHER_FORM_VIEW) {
			modelView = new ModelAndView(FORM_VIEW);
			modelView.addObject(FORM_DATA, voucher);
		} else if (type == ModelViewType.STATUS_VIEW) {
			modelView = new ModelAndView(STATUS_VIEW);
			modelView.addObject(CSS_KEY, CSS_SUCCESS);
			modelView.addObject(MSG_KEY, MSG_SUBMIT);
		}  else {
			throw new UnsupportedModelViewTypeException();
		}
		return modelView;		
	}
	
	public enum ModelViewType {
		VOUCHER_FORM_VIEW,
		STATUS_VIEW
	}
}
