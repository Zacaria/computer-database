<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="cdb" tagdir="/WEB-INF/tags"%>
<c:set var="root" value="${pageContext.request.contextPath}/resources" />
<c:catch var="paramError">
	<fmt:parseNumber var="currentPage" integerOnly="true" type="number" value="${current}" />
	<fmt:parseNumber var="range" integerOnly="true" type="number" value="${range}" />
</c:catch>
<c:if test="${not empty paramError}">
	<c:set var="currentPage" value="1" />
	<c:set var="range" value="10" />
</c:if>

<c:if test="${empty paramError}">
	<c:set var="currentPage" value="${empty current ? 1 : current}" />
	<c:set var="range" value="${empty range ? 10 : range}" />
</c:if>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="${root}/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="${root}/css/font-awesome.css" rel="stylesheet" media="screen">
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
			<c:if test="${success == true}">
				<div class="alert alert-success alert-dismissible" role="alert">
					<button type="button" class="close" data-dismiss="alert" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<strong>Successful</strong> deletion
				</div>
				<c:remove var="success" scope="session" />
			</c:if>
			<c:if test="${not empty errors}">
				<c:forEach items="${sessionScope.errors}" var="error">
					<div class="alert alert-danger alert-dismissible" role="alert">
						<button type="button" class="close" data-dismiss="alert" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<strong>Oops!</strong> Something happened : ${error}
					</div>
				</c:forEach>
				<c:remove var="errors" scope="session" />
			</c:if>
			<h1 id="homeTitle">${count}&nbsp;Computers&nbsp;found
				<c:if test="${not empty param.s}"> for : ${param.s}</c:if>
			</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="dashboard" method="GET" class="form-inline">

						<input type="search" id="searchbox" name="s" class="form-control" placeholder="Search name" />
						<input type="submit" id="searchsubmit" value="Filter by name" class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="addComputer">Add Computer</a> <a
						class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">Edit</a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="deleteComputer" method="POST">
			<input type="hidden" name="selection" value="">
		</form>

		<div class="container" style="margin-top: 10px;">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<!-- Variable declarations for passing labels as parameters -->
						<!-- Table header for Computer Name -->

						<th class="editMode" style="width: 60px; height: 22px;"><input type="checkbox"
							id="selectall" /> <span style="vertical-align: top;"> - <a href="#"
								id="deleteSelected" onclick="$.fn.deleteSelected();"> <i class="fa fa-trash-o fa-lg"></i>
							</a>
						</span></th>
						<th>Computer name<span data-column="computer_name"
							class="glyphicon glyphicon-sort pull-right" aria-hidden="true"></span></th>
						<th>Introduced date<span data-column="introduced"
							class="glyphicon glyphicon-sort pull-right" aria-hidden="true"></span></th>
						<!-- Table header for Discontinued Date -->
						<th>Discontinued date<span data-column="discontinued"
							class="glyphicon glyphicon-sort pull-right" aria-hidden="true"></span></th>
						<!-- Table header for Company -->
						<th>Company <span data-column="company_name" class="glyphicon glyphicon-sort pull-right"
							aria-hidden="true"></span></th>
					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">
					<c:forEach items="${computers.getElements()}" var="computer">
						<tr>
							<td class="editMode"><input type="checkbox" name="cb" class="cb"
								value="${computer.getId()}"></td>
							<td><a href="editComputer?id=${computer.getId()}" onclick="">${computer.getName()}</a></td>
							<td>${computer.getIntroduced()}</td>
							<td>${computer.getDiscontinued() }</td>
							<td>${computer.getCompanyName()}</td>

						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</section>
	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			<cdb:pagination total="${totalPage}" search="${param.s}" current="${currentPage}"
				range="${range}" orderBy="${param.col}" asc="${param.asc}"> </cdb:pagination>

			<div class="btn-group btn-group-sm pull-right" role="group">
				<cdb:link target="dashboard" label="10" page="${currentPage}" limit="10" search="${param.s}"
					classes="btn btn-default ${range == 10 || empty range ? 'active' : '' }" orderBy="${param.col}"
					asc="${param.asc}" />
				<cdb:link target="dashboard" label="50" page="${currentPage}" limit="50" search="${param.s}"
					classes="btn btn-default ${range== 50 ? 'active' : '' }" orderBy="${param.col}"
					asc="${param.asc}" />
				<cdb:link target="dashboard" label="100" page="${currentPage}" limit="100" search="${param.s}"
					classes="btn btn-default ${range== 100 ? 'active' : '' }" orderBy="${param.col}"
					asc="${param.asc}" />
			</div>
		</div>
	</footer>
	<script src="${root}/js/jquery.min.js"></script>
	<script src="${root}/js/bootstrap.min.js"></script>
	<script src="${root}/js/dashboard.js"></script>

</body>
</html>