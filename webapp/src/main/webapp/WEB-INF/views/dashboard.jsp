<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="cdb" tagdir="/WEB-INF/tags"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:set var="root" value="${pageContext.request.contextPath}/resources" />
<c:set var="dto" value="${data.get('dto')}" />

<spring:message code="Dashboard.Search.Button" var="DashboardSearchButton" />
<c:catch var="paramError">
	<fmt:parseNumber var="currentPage" integerOnly="true" type="number" value="${dto.getPage()}" />
	<fmt:parseNumber var="range" integerOnly="true" type="number"
		value="${dto.getOptions().getRange()}" />
</c:catch>
<c:choose>
	<c:when test="${pageContext.response.locale == 'fr'}">
		<c:set var="datePattern" value="dd-MM-yyyy" />
	</c:when>
	<c:otherwise>
		<c:set var="datePattern" value="yyyy-MM-dd" />
	</c:otherwise>
</c:choose>

<c:if test="${not empty paramError}">
	<c:set var="currentPage" value="1" />
	<c:set var="range" value="10" />
</c:if>

<c:if test="${empty paramError}">
	<c:set var="currentPage" value="${empty currentPage ? 1 : currentPage}" />
	<c:set var="range" value="${empty range ? 10 : range}" />
</c:if>
<cdb:default_layout>
	<jsp:attribute name="body_area">
	<section id="main">
		<div class="container">
			<c:if test="${not empty messages.get('success')}">
				<div class="alert alert-success alert-dismissible" role="alert">
					<button type="button" class="close" data-dismiss="alert" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<spring:message code="${messages.get('success') }" />
				</div>
			</c:if>
			<c:if test="${not empty messages.get('errors')}">
				<c:forEach items="${messages.get('errors')}" var="error">
					<div class="alert alert-danger alert-dismissible" role="alert">
						<button type="button" class="close" data-dismiss="alert" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<strong>Oops!</strong> Something happened :
						<spring:message code="${error.getCode()}" />
					</div>
				</c:forEach>
				<c:remove var="errors" scope="session" />
			</c:if>
			<h1 id="homeTitle">${dto.getPager().getTotal()} <spring:message code="Dashboard.Title" />
				<c:if test="${not empty param.s}"> <spring:message code="Dashboard.Search.Label" /> <c:out
							value="${param.s}" />
				</c:if>
			</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="computer" method="GET" class="form-inline">

						<input type="search" id="searchbox" name="s" class="form-control" placeholder="Search name" />
						<input type="submit" id="searchsubmit" value="${DashboardSearchButton}"
								class="btn btn-primary"></input>
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="computer/add"><spring:message
								code="AddComputer.Title" /> </a> <a class="btn btn-default" id="editComputer" href="#"
							onclick="$.fn.toggleEditMode();">Edit</a>
				</div>
			</div>
		</div>

		<form:form id="deleteForm" modelAttribute="deleteComputerForm" action="deleteComputer"
				method="POST">
			<input type="hidden" name="computersToDelete" value="">
		</form:form>

		<div class="container" style="margin-top: 10px;">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<!-- Variable declarations for passing labels as parameters -->
						<!-- Table header for Computer Name -->

						<th class="editMode" style="width: 60px; height: 22px;"><input type="checkbox"
								id="selectall" /> <span style="vertical-align: top;">  <a href="#"
									id="deleteSelected" onclick="$.fn.deleteSelected();"> <i class="fa fa-trash-o fa-lg"></i>
							</a>
						</span></th>
						<th><spring:message code="Computer.Name" /><span data-column="name"
								class="button-sort glyphicon glyphicon-sort pull-right" aria-hidden="true"></span></th>
						<th><spring:message code="Computer.Introduced" /><span data-column="introduced"
								class="button-sort glyphicon glyphicon-sort pull-right" aria-hidden="true"></span></th>
						<!-- Table header for Discontinued Date -->
						<th><spring:message code="Computer.Discontinued" /><span data-column="discontinued"
								class="button-sort glyphicon glyphicon-sort pull-right" aria-hidden="true"></span></th>
						<!-- Table header for Company -->
						<th><spring:message code="Computer.Company" /><span data-column="company.name"
								class="button-sort glyphicon glyphicon-sort pull-right" aria-hidden="true"></span></th>
					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">
					<c:forEach items="${dto.getComputers().getElements()}" var="computer">
					<fmt:parseDate value="${computer.getIntroduced()}" var="introduced" pattern="yyyy-MM-dd" />
					<fmt:parseDate value="${computer.getDiscontinued()}" var="discontinued" pattern="yyyy-MM-dd" />
						<tr>
							<td class="editMode"><input type="checkbox" name="cb" class="cb"
									value="${computer.getId()}"></td>
							<td><a href="computer/edit/${computer.getId()}" onclick=""><c:out
											value="${computer.getName()}" /></a></td>
							<td class="date">
								<fmt:formatDate value="${introduced}" pattern="${datePattern}" />
							</td>
							<td class="date">
								<fmt:formatDate value="${discontinued}" pattern="${datePattern}" />
							</td>
							<td><c:out value="${computer.getCompanyName()}" /></td>

						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</section>
	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			<cdb:pagination total="${dto.getPager().getTotalPage()}" search="${param.s}"
					current="${currentPage}" range="${range}" orderBy="${param.col}" asc="${param.asc}">
			</cdb:pagination>

			<div class="btn-group btn-group-sm pull-right" role="group">
				<cdb:link target="computer" label="10" page="${currentPage}" limit="10" search="${param.s}"
						classes="btn btn-default ${range == 10 || empty range ? 'active' : '' }"
						orderBy="${param.col}" asc="${param.asc}" />
				<cdb:link target="computer" label="50" page="${currentPage}" limit="50" search="${param.s}"
						classes="btn btn-default ${range== 50 ? 'active' : '' }" orderBy="${param.col}"
						asc="${param.asc}" />
				<cdb:link target="computer" label="100" page="${currentPage}" limit="100" search="${param.s}"
						classes="btn btn-default ${range== 100 ? 'active' : '' }" orderBy="${param.col}"
						asc="${param.asc}" />
			</div>
		</div>
	</footer>
	
	</jsp:attribute>

	<jsp:attribute name="script_area">
		<script src="${root}/js/dashboard.js"></script>
	</jsp:attribute>
</cdb:default_layout>