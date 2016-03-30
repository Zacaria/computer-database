<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<c:set var="root" value="${pageContext.request.contextPath}/resources"/>
<html xmlns:h="http://java.sun.com/jsf/html">
<head>
	<title>Computer Database</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<!-- Bootstrap -->
	<link href="${root}/css/bootstrap.min.css" rel="stylesheet" media="screen">
	<link href="${root}/css/font-awesome.css" rel="stylesheet" media="screen">
	<link href="${root}/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard.html"> Application - Computer Database </a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<div class="alert alert-danger">
				Error 403: Access denied!
				<c:out value="Yo">Mama</c:out>
				<br/>
				<!-- stacktrace -->
			</div>
		</div>
	</section>

	<script src="${root}/js/jquery.min.js"></script>
	<script src="${root}/js/bootstrap.min.js"></script>
	<script src="${root}/js/dashboard.js"></script>

</body>
</html>