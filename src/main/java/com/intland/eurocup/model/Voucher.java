package com.intland.eurocup.model;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import com.intland.eurocup.common.model.Territory;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @EqualsAndHashCode
public class Voucher {
	private static final String VOUCHER_CODE_PATTERN = "[A-Za-z0-9]{10}";
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	@NotBlank(message = "Voucher field is mandatory")
	@Pattern(regexp = VOUCHER_CODE_PATTERN, message = "Voucher is invalid.")
	private String code;

	@NotBlank(message = "Email field is mandatory")
	@Pattern(regexp = EMAIL_PATTERN, message = "Invalid email")
	private String email;
	
	private Territory territory;
	
	private String id;
}
