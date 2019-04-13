package com.intland.eurocup.controller;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.intland.eurocup.common.jms.model.FrontendJmsMessage;
import com.intland.eurocup.controller.validation.UserFormValidator;
import com.intland.eurocup.jms.frontend.JmsFrontendSender;
import com.intland.eurocup.model.Request;

@Controller
public class WelcomeController {

	Logger logger = Logger.getLogger(WelcomeController.class.getSimpleName());
	
	private AtomicLong requestIdSequence = new AtomicLong(0); //TODO move this out

	@Autowired
	private UserFormValidator userFormValidator;
	
	@Autowired
	private JmsFrontendSender jmsSender;
	
	

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(userFormValidator);
	}

	// inject via application.properties
	@RequestMapping("/")
	public String welcome() {
		return "welcome";
	}

	// show add user form
	@RequestMapping(value = "/users/add", method = RequestMethod.GET)
	public String showAddUserForm(Model model) {

		Request user = new Request();

		// set default value
		user.setVoucher("mkyong123");
		user.setEmail("test@gmail.com");

		model.addAttribute("userForm", user);
		return "userform";
	}

	// save or update user
	@RequestMapping(value = "/users/add", method = RequestMethod.POST)
	public ModelAndView saveOrUpdateUser(@ModelAttribute("userForm") @Validated Request user, BindingResult result) {

		if (result.hasErrors()) {
			ModelAndView model = new ModelAndView("userform");
			model.addObject("css", "danger");
			model.addObject("msg", "Validation failed!");
			return model;
		} else {
			final FrontendJmsMessage frontendJmsMessage = createVoucher(user.getEmail(), user.getVoucher());
			ModelAndView model = new ModelAndView("statusform");
			model.addObject("css", "success");
			model.addObject("msg", "User added successfully!");
			model.addObject("email", user.getEmail());
			model.addObject("voucher", user.getVoucher());
			model.addObject("id", frontendJmsMessage.getRequestId());
			jmsSender.send(frontendJmsMessage);
			return model;
		}
	}// save or update user
	
	private FrontendJmsMessage createVoucher(final String email, final String voucher) {
		final FrontendJmsMessage voucherFromUi = new FrontendJmsMessage();
		final Long requestId = requestIdSequence.getAndIncrement();
		voucherFromUi.setRequestId(requestId);
		voucherFromUi.setEmail(email);
		voucherFromUi.setTerritory("Germany");
		voucherFromUi.setVoucher(voucher);
		return voucherFromUi;
	}
}