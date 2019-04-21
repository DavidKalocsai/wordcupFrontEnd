package com.intland.eurocup.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.intland.eurocup.common.model.Territory;
import com.intland.eurocup.controller.id.UniqueRequestIdGenerator;
import com.intland.eurocup.controller.model.ModelViewFactory;
import com.intland.eurocup.jms.adapter.AdapterSenderService;
import com.intland.eurocup.model.Voucher;

@Controller
public class VoucherController {

	private Logger logger = Logger.getLogger(VoucherController.class.getSimpleName());

	@Autowired
	private UniqueRequestIdGenerator uniqueRequestIdGenerator;

	@Autowired
	private AdapterSenderService senderService;
	
	@Autowired
	private ModelViewFactory modelViewFactory;

	@RequestMapping("/")
	public String welcome() {
		return "welcome";
	}

	// show add user form
	@RequestMapping(value = "{territory}/form", method = RequestMethod.GET)
	public String showVoucherForm(@PathVariable Territory territory, Model model) {
		model.addAttribute("formData", getDefaultVoucher());
		model.addAttribute("territory", territory);
		return "voucherForm";
	}

	// save or update user
	@RequestMapping(value = "{territory}/form", method = RequestMethod.POST)
	public ModelAndView saveOrUpdateUser(@PathVariable Territory territory, @ModelAttribute("formData") @Validated Voucher voucher, BindingResult result) {
		ModelAndView model; 
		if (result.hasErrors()) {
			model = modelViewFactory.getModelView(false);
		} else {
			final Long requestId = uniqueRequestIdGenerator.getNext();
			model = modelViewFactory.getModelView(true);
			model.addObject("id", requestId);
			voucher.setTerritory(territory);
			senderService.send(requestId, voucher);
			return model;
		}
		return model;		
	}
	
	private Voucher getDefaultVoucher() {
		final Voucher voucher = new Voucher();
		voucher.setCode("abcdef123");
		voucher.setEmail("test@gmail.com");
		return voucher;
	}
}