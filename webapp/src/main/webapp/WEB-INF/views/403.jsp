<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cdb" tagdir="/WEB-INF/tags"%>

<c:set var="root" value="${pageContext.request.contextPath}/resources" />

<cdb:default_layout>
	<jsp:attribute name="body_area">	
	<section id="main">
		<div class="container">
			<div class="alert alert-danger">
				Error 403: Access denied!
				<c:out value="${message }" />
				<br />
				<!-- stacktrace -->
				
				<div class="jumbotron">
					<div class="row">
						<div class="col-md-10">
							<a href="#" class="btn btn-lg btn-block btn-danger panic">Panic !</a>
						</div>
						<div class="col-md-2">
							<a href="/webapp" class="btn btn-lg btn-block btn-success">Stay calm</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
	</jsp:attribute>
</cdb:default_layout>