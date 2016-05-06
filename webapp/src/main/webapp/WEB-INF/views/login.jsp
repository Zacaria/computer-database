<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cdb" tagdir="/WEB-INF/tags"%>

<cdb:default_layout>
	<jsp:attribute name="body_area">	
	<section id="main">
		<div class="container">
			<div class="row centered-form">
				<form class="well form-horizontal col-md-offset-3 col-md-5" action="./login" method="POST">
					<h1>Log in</h1>
					<c:if test="${param.error ne null}">
						<div class="alert alert-dismissible alert-danger">
							<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
							Invalid username and password combination.
						</div>
					</c:if>
					<c:if test="${param.logout ne null}">
						<div class="alert alert-dismissible alert-success">
							<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
							You have been logged out.
						</div>
					</c:if>
					<div class="form-group">
						<div class="col-md-12">
							<input placeholder="Username" class="form-control col-md-12" type="text" name="username" required />
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-12">
							<input placeholder="Password" class="form-control" type="password" name="password" required />
						</div>
					</div>
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					<br />
					<div class="form-group row">
						<div class="col-md-6">
							<input class="btn btn-block btn-lg btn-primary" type="submit" value="Sign In" />
						</div>
						<div class="col-md-6">
							<a href="register" class="btn btn-block btn-lg btn-default">
								Register
							</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</section>	
	</jsp:attribute>
</cdb:default_layout>