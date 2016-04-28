<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cdb" tagdir="/WEB-INF/tags"%>
<c:set var="root" value="${pageContext.request.contextPath}/resources" />
<c:set var="computer" value="${data.get('dto').getComputer()}" />
<c:set var="computerName" value="" />
<c:set var="companies" value="${data.get('dto').getCompanies()}" />
<c:choose>
	<c:when test="${pageContext.response.locale == 'fr'}">
		<c:set var="datePattern" value="dd-MM-yyyy" />
	</c:when>
	<c:otherwise>
		<c:set var="datePattern" value="yyyy-MM-dd" />
	</c:otherwise>
</c:choose>
<cdb:default_layout>
	<jsp:attribute name="body_area">	
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
					<div class="label label-default pull-right">id: ${computer.getId()}</div>
					<h1><spring:message code="EditComputer.Title" /></h1>

					<form:form modelAttribute="editComputerForm" action="" method="POST">
						<input name="id" type="hidden" value="${computer.getId()}" />
						<fieldset>
							<div class="form-group">
								<label for="computerName">Computer name</label> 
								<form:input type="text" class="form-control" id="computerName" value="${computer.getName()}"
										placeholder="Computer name" path="name" required="true" />
								<form:errors path="name" class="help-block form-message-error" />
							</div>
							<div class="form-group">
								<fmt:parseDate value="${computer.getIntroduced()}" var="introduced" pattern="yyyy-MM-dd" />
								<fmt:formatDate value="${introduced}" pattern="${datePattern}" var="formatIntroduced" />
								<label for="introduced">Introduced date</label> 
								<form:input type="date" class="form-control form_datetime" id="introduced"
										value="${formatIntroduced}" placeholder="Introduced date" path="introduced"
										readonly="true" />
								<form:errors path="introduced" class="help-block form-message-error" />
							</div>
							<div class="form-group">
								<fmt:parseDate value="${computer.getDiscontinued()}" var="discontinued" pattern="yyyy-MM-dd" />
								<fmt:formatDate value="${discontinued}" pattern="${datePattern}" var="formatDiscontinued" />
								<label for="discontinued">Discontinued date</label>
								<form:input type="date" class="form-control form_datetime" id="discontinued"
										placeholder="Discontinued date" value="${formatDiscontinued}" path="discontinued"
										readonly="true" />
								<form:errors path="discontinued" class="help-block form-message-error" />
							</div>
							<div class="form-group">
								<label for="companyId">Company</label>
								<form:select class="form-control selectpicker" id="companyId" path="companyId"
										required="true">
									<form:option value="">Nothing Selected</form:option>
<%-- 									<form:options items="${companies.getElements()}" itemLabel="name" itemValue="id" /> --%>
									<c:forEach items="${companies.getElements()}" var="company">
										<option ${computer.getCompanyId() == company.getId() ? "selected" : ''}
												value="${company.getId()}">${company.getName()}</option>
									</c:forEach>
								</form:select>
								<form:errors path="companyId" class="help-block form-message-error" />
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="Edit" class="btn btn-primary"> or
							<cdb:link target="dashboard" label="Cancel" classes="btn btn-default" />
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</section>
	</jsp:attribute>
	
	<jsp:attribute name="script_area">
		<script src="${root}/js/bootstrap-datetimepicker.min.js"></script>
		<script src="${root}/js/bootstrap-select.min.js"></script>
		<script src="${root}/js/computer-form.js"></script>
	</jsp:attribute>
</cdb:default_layout>