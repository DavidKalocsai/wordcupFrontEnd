<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
<jsp:include page="fragments/header.jsp" />
<script>
	$(document).ready(function() {
		validate();
	});

	function validateEmail() {
		var pattern = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		var email = document.getElementById('email').value;
		var validationResult = pattern.test(email);
		console.log('Email' + validationResult);
		document.getElementById('email-validation').style.display = validationResult ? 'none' : 'block';
		document.getElementById('form-group-email').className = validationResult ? 'form-group' : 'form-group has-error';
		return validationResult;
	}

	function validateVoucher() {
		var pattern = /^([A-Z0-9]){10}$/;
		var code = document.getElementById('code').value;
		
		var validationResult = pattern.test(code);
		console.log('code' + validationResult);
		document.getElementById('voucher-validation').style.display = validationResult ? 'none' : 'block';
		document.getElementById('form-group-voucher').className = validationResult ? 'form-group' : 'form-group has-error';
		return validationResult;
	}

	function disableSubmitButton() {
		document.getElementById('redeem-button').disabled = true;
		document.getElementById('redeem-button').className = 'btn-lg btn-secondary pull-right';
	}

	function enableSubmitButton() {
		document.getElementById('redeem-button').disabled = false; 	
		document.getElementById('redeem-button').className = 'btn-lg btn-primary  pull-right';
	}

	function validate() {
		if (validateEmail() && validateVoucher()) {
			enableSubmitButton();
			console.log('enable');
		} else {
			disableSubmitButton();
			console.log('disable');
		}
	}
</script>
<body>

	<div class="container">
		<jsp:include page="fragments/message.jsp" />

		<form:form class="form-horizontal" method="post"
			modelAttribute="formData" action="/${territory}/form">

			<div class="form-group" id ="form-group-email">
				<label class="col-sm-2 control-label">Email</label>
				<div class="col-sm-10">
					<form:input path="email" type="text" class="form-control"
						id="email" placeholder="Email" onkeyup="validate()" />
					<small class="text-danger" id="email-validation"><strong>Please enter valid email!</strong></small>
				</div>
			</div>
			<div class="form-group" id ="form-group-voucher">
				<label class="col-sm-2 control-label">Voucher</label>
				<div class="col-sm-10">
					<form:input path="code" type="text" class="form-control" id="code"
						placeholder="Voucher Code" onkeyup="validate()" maxlength="10" />
					<small class="text-danger" id="voucher-validation"><strong>Please enter valid voucher code!</strong></small>
				</div>
			</div>

			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="submit" id="redeem-button" class="btn-lg pull-right">Redeem</button>
				</div>
			</div>
		</form:form>
	</div>
</body>
<jsp:include page="fragments/footer.jsp" />
</html>