<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="cdb" tagdir="/WEB-INF/tags"%>
<c:set var="root" value="${pageContext.request.contextPath}/resources" />
<c:catch var="paramError">
	<fmt:parseNumber var="currentPage" integerOnly="true" type="number"
		value="${current}" />
	<fmt:parseNumber var="range" integerOnly="true" type="number"
		value="${range}" />
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
<link href="${root}/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="${root}/css/font-awesome.css" rel="stylesheet"
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
			<h1 id="homeTitle">${count}&nbsp;Computers&nbsp;found</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="#" method="GET" class="form-inline">

						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder="Search name" /> <input
							type="submit" id="searchsubmit" value="Filter by name"
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="addComputer">Add
						Computer</a> <a class="btn btn-default" id="editComputer" href="#"
						onclick="$.fn.toggleEditMode();">Edit</a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="#" method="POST">
			<input type="hidden" name="selection" value="">
		</form>

		<div class="container" style="margin-top: 10px;">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<!-- Variable declarations for passing labels as parameters -->
						<!-- Table header for Computer Name -->

						<th class="editMode" style="width: 60px; height: 22px;"><input
							type="checkbox" id="selectall" /> <span
							style="vertical-align: top;"> - <a href="#"
								id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
									class="fa fa-trash-o fa-lg"></i>
							</a>
						</span></th>
						<th>Computer name</th>
						<th>Introduced date</th>
						<!-- Table header for Discontinued Date -->
						<th>Discontinued date</th>
						<!-- Table header for Company -->
						<th>Company</th>

					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">
					<c:forEach items="${computers.getElements()}" var="computer">
						<tr>
							<td class="editMode"><input type="checkbox" name="cb"
								class="cb" value="0"></td>
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
			<cdb:pagination total="${totalPage}" current="${currentPage}"
				range="${range}"></cdb:pagination>

			<div class="btn-group btn-group-sm pull-right" role="group">
				<cdb:link target="dashboard" label="10" page="${currentPage}" limit="10" classes="btn btn-default ${range == 10 || empty range ? 'active' : '' }"/>
				<cdb:link target="dashboard" label="50" page="${currentPage}" limit="50" classes="btn btn-default ${range== 50 ? 'active' : '' }"/>
				<cdb:link target="dashboard" label="100" page="${currentPage}" limit="100" classes="btn btn-default ${range== 100 ? 'active' : '' }" />
			</div>
		</div>
	</footer>
	<script src="${root}/js/jquery.min.js"></script>
	<script src="${root}/js/bootstrap.min.js"></script>
	<script src="${root}/js/dashboard.js"></script>

</body>
</html>