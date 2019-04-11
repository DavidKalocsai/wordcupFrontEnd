package com.mkyong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

//http://docs.spring.io/spring/docs/current/spring-framework-reference/html/validation.html#validation-mvc-configuring
@Component
public class UserFormValidator implements Validator {

	@Autowired
	@Qualifier("emailValidator")
	EmailValidator emailValidator;

	@Override
	public boolean supports(Class<?> clazz) {
		return Voucher.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		Voucher user = (Voucher) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "voucher", "NotEmpty.userForm.voucher");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.userForm.email");

		if (!emailValidator.valid(user.getEmail())) {
			errors.rejectValue("email", "Pattern.userForm.email");
		}

		if (user.getVoucher() == null || user.getVoucher().length() != 10) {
			errors.rejectValue("voucher", "NotEmpty.userForm.voucher");
		}
	}

}