<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="cdb" tagdir="/WEB-INF/tags"%>
<%@ attribute name="current" required="true" type="java.lang.Integer"
	description="Current page"%>
<%@ attribute name="total" required="true" type="java.lang.Integer"
	description="Total page"%>
<%@ attribute name="range" required="true" type="java.lang.Integer"
	description="The limit of displayed computers"%>
<%@ attribute name="search" required="false" type="java.lang.String"
	description="search"%>

<c:set var="link" value="${target}?p=${page}&r=${limit}" />

<ul class="pagination">
	<li><cdb:link target="dashboard" page="1" limit="${range}"
			search="${search}" label="&laquo;" classes="btn btn-default" /></li>

	<c:if test="${current > 2 }">
		<li><cdb:link target="dashboard" page="${current - 2}"
				limit="${range}" search="${search}" label="${current - 2}"
				classes="btn btn-default" /></li>
	</c:if>
	<c:if test="${current > 1 }">
		<li><cdb:link target="dashboard" page="${current - 1}"
				limit="${range}" search="${search}" label="${current - 1}"
				classes="btn btn-default" /></li>
	</c:if>
	<li><cdb:link target="dashboard" page="${current}"
			limit="${range}" search="${search}" label="${current}"
			classes="btn btn-primary active" /></li>
	<c:if test="${current < total}">
		<li><cdb:link target="dashboard" page="${current + 1}"
				limit="${range}" search="${search}" label="${current + 1}"
				classes="btn btn-default" /></li>
	</c:if>
	<c:if test="${current < total - 1}">
		<li><cdb:link target="dashboard" page="${current + 2}"
				limit="${range}" search="${search}" label="${current + 2}"
				classes="btn btn-default" /></li>
	</c:if>

	<li><cdb:link target="dashboard" page="${total}" limit="${range}"
			search="${search}" label="&raquo;" classes="btn btn-default" /></li>
</ul>