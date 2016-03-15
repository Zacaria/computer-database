<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="root" value="${pageContext.request.contextPath}/resources" />
<c:catch var="paramError">
	<fmt:parseNumber var="currentPage" integerOnly="true" type="number"
		value="${param.p}" />
</c:catch>
<c:if test="${paramError != null}">
	<c:set var="currentPage" value="1" />
</c:if>
<c:if test="${paramError == null}">
	<c:set var="currentPage" value="${empty param.p ? 1 : param.p}" />
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
			<h1 id="homeTitle">${count}&nbsp;Computersfound</h1>
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
							<td><a href="editComputer" onclick="">${computer.getName()}</a></td>
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
			<ul class="pagination">
				<li><a href="./dashboard?p=1" aria-label="Previous"> <span
						aria-hidden="true">&laquo;</span>
				</a></li>
				<c:if test="${currentPage > 2 }">
					<li><a class="btn btn-default" role="button"
						href="./dashboard?p=${currentPage-2}&r=${range}">${currentPage-2}</a></li>
				</c:if>
				<c:if test="${currentPage > 1 }">
					<li><a class="btn btn-default" role="button"
						href="./dashboard?p=${currentPage-1}&r=${range}">${currentPage-1}</a></li>
				</c:if>
				<li><a class="btn btn-primary active" role="button" href="#">${currentPage}</a></li>
				<c:if test="${currentPage <= totalPage}">
					<li><a class="btn btn-default" role="button"
						href="./dashboard?p=${currentPage + 1 }&r=${range}">${currentPage + 1 }</a></li>
				</c:if>
				<c:if test="${currentPage <= totalPage - 1}">
					<li><a class="btn btn-default" role="button"
						href="./dashboard?p=${currentPage + 2 }&r=${range}">${currentPage + 2 }</a></li>
				</c:if>

				<li><a href="./dashboard?p=${totalPage + 1}&r=${range}" aria-label="Next">
						<span aria-hidden="true">&raquo;</span>
				</a></li>
			</ul>

			<div class="btn-group btn-group-sm pull-right" role="group">
				<a href="dashboard?p=${currentPage}&r=10"
					class="btn btn-default ${param.r == 10 || empty param.r ? 'active' : '' }">10</a>
				<a href="dashboard?p=${currentPage}&r=50"
					class="btn btn-default ${param.r == 50 ? 'active' : '' }">50</a> <a
					href="dashboard?p=${currentPage}&r=100"
					class="btn btn-default ${param.r == 100 ? 'active' : '' }">100</a>
			</div>
		</div>
	</footer>
	<script src="${root}/js/jquery.min.js"></script>
	<script src="${root}/js/bootstrap.min.js"></script>
	<script src="${root}/js/dashboard.js"></script>

</body>
</html>