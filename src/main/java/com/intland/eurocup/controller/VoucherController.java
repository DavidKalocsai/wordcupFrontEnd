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
import com.intland.eurocup.jms.adapter.AdapterSenderService;
import com.intland.eurocup.model.Voucher;

@Controller
public class VoucherController {

	Logger logger = Logger.getLogger(VoucherController.class.getSimpleName());

	@Autowired
	private UniqueRequestIdGenerator uniqueRequestIdGenerator;

	@Autowired
	private AdapterSenderService senderService;

	@RequestMapping("/")
	public String welcome() {
		return "welcome";
	}

	// show add user form
	@RequestMapping(value = "{territory}/form", method = RequestMethod.GET)
	public String showVoucherForm(@PathVariable String territory, Model model) {
		model.addAttribute("formData", buildDefaultVoucher());
		model.addAttribute("territory", territory);
		return "voucherForm";
	}

	// save or update user
	@RequestMapping(value = "{territory}/form", method = RequestMethod.POST)
	public ModelAndView saveOrUpdateUser(@PathVariable String territory, @ModelAttribute("formData") @Validated Voucher voucher, BindingResult result) {

		if (result.hasErrors()) {
			ModelAndView model = new ModelAndView("voucherForm");
			model.addObject("css", "danger");
			model.addObject("msg", "Validation failed!");
			return model;
		} else {
			final Long requestId = uniqueRequestIdGenerator.getNext();
			ModelAndView model = new ModelAndView("statusform");
			model.addObject("css", "success");
			model.addObject("msg", "Voucher redeem request submitted!");
			model.addObject("email", voucher.getEmail());
			model.addObject("code", voucher.getCode());
			model.addObject("territory", territory);
			model.addObject("id", requestId);
			voucher.setTerritory(Territory.getEnumFromCode(territory));
			senderService.send(requestId, voucher);
			return model;
		}
	}
	
	private Voucher buildDefaultVoucher() {
		final Voucher voucher = new Voucher();
		voucher.setCode("abcdef123");
		voucher.setEmail("test@gmail.com");
		return voucher;
	}
}