package com.mkyong;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import jdk.nashorn.internal.objects.annotations.Getter;

public class Voucher {
	private static final String VOUCHER_PATTERN = "[A-Za-z]{10}";
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	@NotBlank(message = "Voucher field is mandatory")
	@Pattern(regexp = VOUCHER_PATTERN, message = "Voucher is invalid.")
	String voucher;

	@NotBlank(message = "Email field is mandatory")
	@Pattern(regexp = EMAIL_PATTERN, message = "Invalid email")
	String email;

	public String getVoucher() {
		return voucher;
	}

	public void setVoucher(String voucher) {
		this.voucher = voucher;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
