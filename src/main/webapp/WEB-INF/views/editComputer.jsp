<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="cdb" tagdir="/WEB-INF/tags"%>
<c:set var="root" value="${pageContext.request.contextPath}/resources" />
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="${root}/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="${root}/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="${root}/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
<link href="${root}/css/bootstrap-select.min.css" rel="stylesheet" media="screen">
<link href="${root}/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<cdb:link target="dashboard" label="Application - Computer Database" classes="navbar-brand" />
		</div>
	</header>
	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<c:if test="${success == true}">
						<div class="alert alert-success alert-dismissible" role="alert">
							<button type="button" class="close" data-dismiss="alert" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<strong>Success!</strong> Your computer has been successfully updated
						</div>
					</c:if>
					<c:if test="${success == false}">
						<c:forEach items="${errors}" var="error">
							<div class="alert alert-danger alert-dismissible" role="alert">
								<button type="button" class="close" data-dismiss="alert" aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
								<strong>Oops!</strong> Something happened : ${error}
							</div>
						</c:forEach>
					</c:if>
					<div class="label label-default pull-right">id: ${computer.getId()}</div>
					<h1>Edit Computer</h1>

					<form action="editComputer" method="POST">
						<input name="id" type="hidden" value="${computer.getId()}" />
						<fieldset>
							<div class="form-group">
								<label for="computerName">Computer name</label> <input type="text" class="form-control"
									id="computerName" value="<c:out value='${computer.getName()}'/>"
									placeholder="Computer name" name="name" required>
							</div>
							<div class="form-group">
								<label for="introduced">Introduced date</label> <input type="date"
									class="form-control form_datetime" id="introduced"
									value="<c:out value='${computer.getIntroduced()}'/>" placeholder="Introduced date"
									name="introduced" readonly>
							</div>
							<div class="form-group">
								<label for="discontinued">Discontinued date</label> <input type="date"
									class="form-control form_datetime" id="discontinued" placeholder="Discontinued date"
									value="<c:out value='${computer.getDiscontinued()}'/>" name="discontinued" readonly>
							</div>
							<div class="form-group">
								<label for="companyId">Company</label>
								<select class="form-control selectpicker" id="companyId" name="companyId" required>
									<c:forEach items="${companies.getElements()}" var="company">
										<option ${computer.getCompanyId() == company.getId() ? "selected" : ''}
											value="${company.getId()}">${company.getName()}</option>
									</c:forEach>
								</select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="Edit" class="btn btn-primary"> or
							<cdb:link target="dashboard" label="Cancel" classes="btn btn-default" />
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