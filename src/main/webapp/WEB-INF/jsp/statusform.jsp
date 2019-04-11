<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<title>Spring MVC Form Handling</title>
</head>
<body>
	<jsp:include page="fragments/header.jsp" />
	<script>
		$(document).ready(function() {
			$("#msg-alert").delay(1000).fadeOut(500, function() {
				$(this).remove();
			});

			var interval = function() {
				$.ajax({
					type : "get",
					url : "http://localhost:8080/api/status", //this is my servlet
					success : function(msg) {
						console.log(msg);
						var dt = new Date();
						var time = dt.getHours() + ":" + dt.getMinutes() + ":" + dt.getSeconds();
						var text = $('#comment').val();
						$('#comment').val(time  + ' -- ' + msg + '\n' + text);
						if (msg !== 'stop') {
							setTimeout(interval, 100);
						}
					},
				});
			}
			setTimeout(interval, 2000);

		});
	</script>

	<div class="container">
		<c:if test="${not empty msg}">
			<div class="alert alert-${css} alert-dismissible" id="msg-alert"
				role="alert">
				<button type="button" class="close" data-dismiss="alert"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<strong>${msg}</strong>
			</div>
		</c:if>


		<h3>Voucher submitted</h3>
		<div class="col-sm-10"></div>
		<div class="col-sm-10">
			<label class="col-sm-2 control-label">Email</label> <label
				class="col-sm-2 control-label">${email}</label>
		</div>
		<div class="col-sm-10">
			<label class="col-sm-2 control-label">Voucher</label> <label
				class="col-sm-2 control-label">${voucher}</label>
		</div>
		<div class="col-sm-10">
			<label class="col-sm-2 control-label">Comment:</label>
			<textarea class="col-sm-10" rows="5" id="comment"></textarea>
		</div>

	</div>
	<jsp:include page="fragments/footer.jsp" />
</body>
</html>