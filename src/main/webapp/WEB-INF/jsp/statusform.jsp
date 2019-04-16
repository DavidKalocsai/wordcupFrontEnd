<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<title>Spring MVC Form Handling</title>
</head>
<body>
	<jsp:include page="fragments/header.jsp" />
	<script>
		$(document).ready(function() {
			var interval = function() {
			$.ajax({
				type : "get",
				url : "http://localhost:8080/api/fetchResponse", //this is my servlet
				data: {	"responseId" : ${id} },
				success : function(response) {
					console.log(response.status + response.message);
					var dt = new Date();
					var time = dt.getHours() + ":" + dt.getMinutes() + ":" + dt.getSeconds();
					var text = $('#details').val();
					$('#details').val(time  + ' -- {' + response.status + " " + response.message + '}\n' + text);
					if (response.status === 'NO') {
						setTimeout(interval, 100);
					}
				},
			});
			}
			setTimeout(interval, 2000);
		});
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
				class="col-sm-2 control-label">${email}</label>
		</div>
		<div class="col-sm-10">
			<label class="col-sm-2 control-label">Voucher</label> <label
				class="col-sm-2 control-label">${code}</label>
		</div>
		<div class="col-sm-10">
			<label class="col-sm-2 control-label">Details:</label>
			<textarea class="col-sm-10" rows="5" id="details"></textarea>
		</div>

	</div>
	<jsp:include page="fragments/footer.jsp" />
</body>
</html>