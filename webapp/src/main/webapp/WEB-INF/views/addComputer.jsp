<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cdb" tagdir="/WEB-INF/tags"%>

<c:set var="root" value="${pageContext.request.contextPath}/resources" />

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
							<strong>Success!</strong> <spring:message code="Success.AddComputer" />
						</div>
					</c:if>
					<c:if test="${not empty messages.get('errors')}">
						<c:forEach items="${messages.get('errors')}" var="error">
							<div class="alert alert-danger alert-dismissible" role="alert">
								<button type="button" class="close" data-dismiss="alert" aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
								<strong>Oops!</strong> Something happened : ${error}
							</div>
						</c:forEach>
					</c:if>
					<h1>
							<spring:message code="AddComputer.Title" />
						</h1>
					<form:form modelAttribute="addComputerForm" action="" method="POST">
						<fieldset>
							<div class="form-group">
								<label class="control-label" for="computerName"><spring:message code="Computer.Name" /></label>
								<form:input cssClass="form-control" id="computerName" placeholder="Computer name"
										path="name" required="true" />
								<form:errors path="name" class="help-block form-message-error" />
							</div>
							<div class="form-group">
								<label class="control-label" for="introduced"><spring:message
											code="Computer.Introduced" /></label>
								<form:input type="date" cssClass="form-control form_datetime" id="introduced"
										placeholder="Click to pick a date" path="introduced" readonly="true" />
								<form:errors path="introduced" class="help-block form-message-error" />
							</div>
							<div class="form-group">
								<label class="control-label" for="discontinued"><spring:message
											code="Computer.Discontinued" /></label>
								<form:input type="date" cssClass="form-control form_datetime" id="discontinued"
										placeholder="Click to pick a date" path="discontinued" readonly="true" />
								<form:errors path="discontinued" class="help-block form-message-error" />
							</div>
							<div class="form-group">
								<label class="control-label" for="companyId"><spring:message code="Computer.Company" /></label>
								<form:select cssClass="form-control selectpicker" id="companyId" title="Pick a company"
										path="companyId" required="true">

									<form:options items="${data.get('companies').getElements()}" itemLabel="name"
											itemValue="id" />
								</form:select>
								<form:errors path="companyId" class="help-block form-message-error" />
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="Add" class="btn btn-primary"> or
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