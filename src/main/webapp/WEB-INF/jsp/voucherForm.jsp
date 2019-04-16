<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
<jsp:include page="fragments/header.jsp" />
<body>

	<div class="container">
		<jsp:include page="fragments/message.jsp" />

		<form:form class="form-horizontal" method="post"
			modelAttribute="formData" action="/${territory}/form">

			<spring:bind path="email">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-sm-2 control-label">Email</label>
					<div class="col-sm-10">
						<form:input path="email" class="form-control" id="email"
							placeholder="Email" />
						<form:errors path="email" class="control-label" />
					</div>
				</div>
			</spring:bind>

			<spring:bind path="code">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-sm-2 control-label">Voucher</label>
					<div class="col-sm-10">
						<form:input path="code" type="text" class="form-control "
							id="code" placeholder="Voucher Code" />
						<form:errors path="code" class="control-label" />
					</div>
				</div>
			</spring:bind>


			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="submit" id="call"
						class="btn-lg btn-primary pull-right">Redeem</button>
				</div>
			</div>
		</form:form>
	</div>
</body>
<jsp:include page="fragments/footer.jsp" />
</html>