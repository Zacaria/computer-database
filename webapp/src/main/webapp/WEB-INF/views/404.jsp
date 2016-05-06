<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cdb" tagdir="/WEB-INF/tags"%>
<cdb:default_layout>
	<jsp:attribute name="body_area">
	<section id="main">
		<div class="container">
			<div class="jumbotron">
				<h1>404</h1>
				<c:if test="${errors.getClass().getSimpleName() == 'NotFoundException'}">
					<spring:message code="${errors.getErrCode()}" />
				</c:if>
				${errors.getMessage()}
				
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
	</section>
	</jsp:attribute>
</cdb:default_layout>