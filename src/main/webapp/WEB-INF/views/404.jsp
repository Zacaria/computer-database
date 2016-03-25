<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="cdb" tagdir="/WEB-INF/tags"%>
<c:set var="root" value="${pageContext.request.contextPath}/resources" />
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
			<cdb:link target="dashboard" label="Application - Computer Database"
				classes="navbar-brand" />
		</div>
	</header>

	<section id="main">
		<div class="container">
			<div class="jumbotron"><h1>404</h1></div>
			<div class="alert alert-danger">
				Error 404: Page not found. Too bad bitch! <br />
				<c:if test="${not empty errors}">
					<c:forEach items="${errors}" var="error">
						<div class="alert alert-danger alert-dismissible" role="alert">
							<button type="button" class="close" data-dismiss="alert"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<strong>Oops!</strong> Something happened : ${error}
						</div>
					</c:forEach>
					<c:remove var="errors" scope="session" />
				</c:if>
			</div>
		</div>
	</section>

	<script src="${root}/js/jquery.min.js"></script>
	<script src="${root}/js/bootstrap.min.js"></script>
	<script src="${root}/js/dashboard.js"></script>

</body>
</html>