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
			</div>
		</div>
	</section>
	</jsp:attribute>
</cdb:default_layout>