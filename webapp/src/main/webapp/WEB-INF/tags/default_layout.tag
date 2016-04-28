<%@tag description="Default Template" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cdb" tagdir="/WEB-INF/tags"%>

<%@attribute name="title"%>
<%@attribute name="body_area" fragment="true"%>
<%@attribute name="script_area" fragment="true"%>
<c:set var="root" value="${pageContext.request.contextPath}/resources" />

<!DOCTYPE html>
<html lang="${pageContext.response.locale}">
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="UTF-8">
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
			<cdb:link target="/computer-database/dashboard" label="Application - Computer Database"
				classes="navbar-brand" />
			<ul class="nav navbar-nav navbar-right">
				<li>
					<a href="javascript:void(0)" class="navbar-brand navbar-link navbar-lang" data-lang="fr">Fr</a>
				</li>
				<li>
					<a href="javascript:void(0)" class="navbar-brand navbar-link navbar-lang" data-lang="en">En</a>
				</li>
			</ul>
		</div>
	</header>
	
	<jsp:invoke fragment="body_area" />

	<script src="${root}/js/jquery.min.js"></script>
	<script src="${root}/js/bootstrap.min.js"></script>
	<script src="${root}/js/main.js"></script>
	<jsp:invoke fragment="script_area" />

</body>
</html>