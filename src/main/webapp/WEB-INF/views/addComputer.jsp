<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<c:set var="root" value="${pageContext.request.contextPath}/resources" />
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="${root}/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="${root}/css/font-awesome.css" rel="stylesheet"
	media="screen">
<link href="${root}/css/bootstrap-datetimepicker.min.css"
	rel="stylesheet" media="screen">
<link href="${root}/css/bootstrap-select.min.css" rel="stylesheet"
	media="screen">
<link href="${root}/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard"> Application - Computer
				Database </a>
		</div>
	</header>
	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<c:if test="${success == true}">
						<div class="alert alert-success alert-dismissible" role="alert">
							<button type="button" class="close" data-dismiss="alert"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<strong>Success!</strong> Your computer has been successfully
							added
						</div>
					</c:if>
					<c:if test="${success == false}">
						<c:forEach items="${errors}" var="error">
							<div class="alert alert-danger alert-dismissible" role="alert">
								<button type="button" class="close" data-dismiss="alert"
									aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
								<strong>Oops!</strong> Something happened : ${error}
							</div>
						</c:forEach>
					</c:if>
					<h1>Add Computer</h1>
					<form action="addComputer" method="POST">
						<fieldset>
							<div class="form-group">
								<label for="computerName">Computer name</label> <input
									type="text" class="form-control" id="computerName"
									placeholder="Computer name" name="name" required>
							</div>
							<div class="form-group">
								<label for="introduced">Introduced date</label> <input
									type="date" class="form-control form_datetime" id="introduced"
									placeholder="Click to pick a date" name="introduced" readonly>
							</div>
							<div class="form-group">
								<label for="discontinued">Discontinued date</label> <input
									type="date" class="form-control form_datetime"
									id="discontinued" placeholder="Click to pick a date"
									name="discontinued" readonly>
							</div>
							<div class="form-group">
								<label for="companyId">Company</label> <select
									class="form-control selectpicker" id="companyId"
									title="Pick a company" name="companyId" required>
									<c:forEach items="${companies.getElements()}" var="company">
										<option value="${company.getId()}">${company.getName()}</option>
									</c:forEach>
								</select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="Add" class="btn btn-primary">
							or <a href="dashboard" class="btn btn-default">Cancel</a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>

	<script src="${root}/js/jquery.min.js"></script>
	<script src="${root}/js/bootstrap.min.js"></script>
	<script src="${root}/js/bootstrap-datetimepicker.min.js"></script>
	<script src="${root}/js/bootstrap-select.min.js"></script>
	<script src="${root}/js/dashboard.js"></script>
</body>
</html>