<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cdb" tagdir="/WEB-INF/tags"%>

<c:set var="root" value="${pageContext.request.contextPath}/resources" />

<cdb:default_layout>
	<jsp:attribute name="body_area">	
	<section id="main">
		<div class="container">
			<div class="row centered-form">
<!-- 				<div class="col-xs-8 col-xs-offset-2 box"> -->
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
					
					<form:form modelAttribute="registerForm" action="" method="POST"
						class="well form-horizontal col-md-offset-3 col-md-5">
						<h1>
							<spring:message code="Register.Title" />
						</h1>
						<spring:hasBindErrors name="registerForm">
							<div class="alert alert-dismissible alert-danger">
								<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
								<form:errors/>
							</div>
						</spring:hasBindErrors>						
						
						<fieldset>	
							<div class="form-group">
								<div class="col-md-12">
									<form:input cssClass="form-control" id="username" placeholder="Username" path="username"
										required="true" />
									<form:errors path="username" class="help-block form-message-error" />
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-12">
									<form:password cssClass="form-control" id="password" placeholder="Password" path="password"
										required="true" />
									<form:errors path="password" class="help-block form-message-error" />
								</div>	
							</div>
							<div class="form-group">
								<div class="col-md-12">
							        <label class="form-label">Password Strength</label>
							        <div id="password-progress-bar-container"></div>
							    </div>
						    </div>
						    <div class="form-group has-feedback">
								<div class="col-md-12">
							        <input type="password" class="form-control password-retype" placeholder="Re-type password"/>
							        <span class="glyphicon glyphicon-ok form-control-feedback" aria-hidden="true"></span>
							        <span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>
							    </div>
						    </div>
						</fieldset>
						<div class="actions form-group row">
							<div class="col-md-6">
								<input type="submit" value="Register" class="btn btn-primary btn-lg btn-block" required/>
							</div>							
							<div class="col-md-6">
								<a href="login" class="btn btn-default btn-lg btn-block">
									Login
								</a>
							</div>							
						</div>
					</form:form>
<!-- 				</div> -->
			</div>
		</div>
	</section>	
	</jsp:attribute>

	<jsp:attribute name="script_area">
		<script src="${root}/js/password-score.js"></script>
		<script src="${root}/js/password-score-options.js"></script>
		<script src="${root}/js/bootstrap-strength-meter.js"></script>
		<script src="${root}/js/register.js"></script>
	</jsp:attribute>
</cdb:default_layout>