<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<title>Spring MVC Form Handling</title>
</head>
<body>
	<jsp:include page="fragments/header.jsp" />
	<script>
		$(document).ready(function() {
			setTimeout(ajaxPoll, 100);
		});
		
		function ajaxPoll() {
			$.ajax({
					type : "get",
					url : "http://localhost:8080/api/fetchResponse", //this is my servlet
					data: {	"responseId" : ${id}},
					success : responseArrived
			});
		};
		
		function responseArrived(response) {
			console.log(response.status + response.message);
			$('#details').text('{' + response.status + " " + response.message + '}\n');
			if (response.status === 'NO') {
				setTimeout(ajaxPoll, 100);
			}
		}
	</script>
	

	<div class="container">
		<jsp:include page="fragments/message.jsp" />


		<h3>Voucher submitted</h3>
		<div class="col-sm-10"></div>
		<div class="col-sm-10">
			<label class="col-sm-2 control-label">Request Id</label> <label
				class="col-sm-2 control-label">${id}</label>
		</div>
		<div class="col-sm-10">
			<label class="col-sm-2 control-label">Email</label> <label
				class="col-sm-2 control-label">${formData.email}</label>
		</div>
		<div class="col-sm-10">
			<label class="col-sm-2 control-label">Voucher</label> <label
				class="col-sm-2 control-label">${formData.code}</label>
		</div>
		<div class="col-sm-10">
			<label class="col-sm-2 control-label">Details:</label> <label
				class="col-sm-10 control-label" id="details"></label>
		</div>

	</div>
	<jsp:include page="fragments/footer.jsp" />
</body>
</html>