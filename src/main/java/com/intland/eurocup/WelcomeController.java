package com.intland.eurocup;

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

import com.intland.eurocup.jms.frontend.VoucherJmsSender;
import com.intland.eurocup.jms.model.VoucherFromUi;

@Controller
public class WelcomeController {

	Logger logger = Logger.getLogger(WelcomeController.class.getSimpleName());

	@Autowired
	private UserFormValidator userFormValidator;
	
	@Autowired
	private VoucherJmsSender jmsSender;

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

		Voucher user = new Voucher();

		// set default value
		user.setVoucher("mkyong123");
		user.setEmail("test@gmail.com");

		model.addAttribute("userForm", user);
		return "userform";
	}

	// save or update user
	@RequestMapping(value = "/users/add", method = RequestMethod.POST)
	public ModelAndView saveOrUpdateUser(@ModelAttribute("userForm") @Validated Voucher user, BindingResult result) {

		if (result.hasErrors()) {
			ModelAndView model = new ModelAndView("userform");
			model.addObject("css", "danger");
			model.addObject("msg", "Validation failed!");
			return model;
		} else {
			ModelAndView model = new ModelAndView("statusform");
			model.addObject("css", "success");
			model.addObject("msg", "User added successfully!");
			model.addObject("email", user.getEmail());
			model.addObject("voucher", user.getVoucher());
			jmsSender.send(createVoucher(user.getEmail(), user.getVoucher()));
			return model;
		}
	}// save or update user
	
	private VoucherFromUi createVoucher(final String email, final String voucher) {
		final VoucherFromUi voucherFromUi = new VoucherFromUi();
		voucherFromUi.setEmail(email);
		voucherFromUi.setTerritory("Germany");
		voucherFromUi.setVoucher(voucher);
		return voucherFromUi;
	}
}