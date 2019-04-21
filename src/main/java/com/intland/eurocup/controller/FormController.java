package com.intland.eurocup.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.intland.eurocup.common.model.Territory;
import com.intland.eurocup.controller.id.UniqueRequestIdGenerator;
import com.intland.eurocup.controller.model.ModelViewFactory;
import com.intland.eurocup.controller.model.ModelViewFactory.ModelViewType;
import com.intland.eurocup.controller.response.ResponseStorage;
import com.intland.eurocup.jms.adapter.AdapterSenderService;
import com.intland.eurocup.model.Response;
import com.intland.eurocup.model.ResponseStatus;
import com.intland.eurocup.model.Voucher;
import com.intland.eurocup.service.converter.TerritoryConverter;
import com.intland.eurocup.service.converter.exception.UnkownTerritoryException;

@Controller
public class FormController {

	private Logger logger = Logger.getLogger(FormController.class.getSimpleName());

	@Autowired
	private ModelViewFactory modelViewFactory;

	@InitBinder
	public void initBinder(final WebDataBinder webdataBinder) {
		webdataBinder.registerCustomEditor(Territory.class, new TerritoryConverter());
	}

	// show add user form
	@RequestMapping(value = "{territory}/form", method = RequestMethod.GET)
	public ModelAndView showVoucherForm(@PathVariable Territory territory) {
		// validate territory
		logger.info("Territory: " + territory);
		return modelViewFactory.getModelView(ModelViewType.VOUCHER_FORM_VIEW);
	}
}