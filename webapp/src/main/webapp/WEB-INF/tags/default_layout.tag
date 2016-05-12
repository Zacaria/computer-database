<%@tag description="Default Template" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="cdb" tagdir="/WEB-INF/tags"%>

<%@attribute name="title"%>
<%@attribute name="body_area" fragment="true"%>
<%@attribute name="script_area" fragment="true"%>
<c:set var="root" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="${pageContext.response.locale}">
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="UTF-8">
<sec:csrfMetaTags />
<link rel="stylesheet" type="text/css"
	href="//fonts.googleapis.com/css?family=Roboto:300,400,500,700">
<link rel="stylesheet" type="text/css" href="//fonts.googleapis.com/icon?family=Material+Icons">
<!-- Bootstrap -->
<link href="${root}/resources/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.2/css/font-awesome.min.css">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/g/bootstrap.material-design@0.5.9(css/bootstrap-material-design.min.css+css/ripples.min.css)">

<link href="${root}/resources/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
<link href="${root}/resources/css/bootstrap-select.min.css" rel="stylesheet" media="screen">
<link href="${root}/resources/css/main.css" rel="stylesheet" media="screen">
</head>

<body>
	<header class="navbar navbar-fixed-top">
		<div class="container">
			<!-- 			<div class="pull-left"> -->
			<%-- 				<cdb:link target="computer" label="Application - Computer Database" classes="navbar-brand" /> --%>
			<!-- 			</div> -->

			<ul class="nav navbar-nav col-sm-12">
				<li class="navbar-left">
					<cdb:link target="computer" label="Application - Computer Database" classes="navbar-brand" />
				</li>
				<sec:authorize access="hasRole('ROLE_USER')">
					<li class="center-block label label-success navbar-btn">User</li>
				</sec:authorize>

				<sec:authorize access="hasRole('ROLE_ADMIN')">
					<li class="center-block label label-danger navbar-btn">Admin</li>
				</sec:authorize>

				<li class="navbar-lang navbar-right">
					<a href="javascript:void(0)" class="navbar-brand navbar-link" data-lang="fr">Fr</a>
				</li>
				<li class="navbar-lang navbar-right">
					<a href="javascript:void(0)" class="navbar-brand navbar-link" data-lang="en">En</a>
				</li>
				<sec:authorize access="!isAuthenticated()">
					<li class="navbar-right">
						<a href="login"> <i class="fa fa-sign-in fa-lg" aria-hidden="true"></i>
						</a>
					</li>
					<li class="navbar-right">
						<a href="register"> <i class="fa fa-user-plus fal-lg" aria-hidden="true"></i>
						</a>
					</li>
				</sec:authorize>
				<sec:authorize access="isAuthenticated()">
					<li class="pull-right">
						<form class="navbar-form navbar-out" action="${root}/logout" method="POST">
							<span class="user-welcome">Hi <sec:authentication property="principal.username" /> !
							</span>
							<button type="submit" class="btn-link navbar-btn">
								<i class="fa fa-sign-out fa-lg" aria-hidden="true"></i>
							</button>
							<sec:csrfInput />
						</form>
					</li>
				</sec:authorize>
			</ul>
		</div>
	</header>

	<jsp:invoke fragment="body_area" />

	<script src="${root}/resources/js/jquery.min.js"></script>
	<script src="${root}/resources/js/bootstrap.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/g/bootstrap.material-design@0.5.9(js/material.min.js+js/ripples.min.js)"></script>
	<script src="${root}/resources/js/main.js"></script>
	<jsp:invoke fragment="script_area" />

</body>
</html>