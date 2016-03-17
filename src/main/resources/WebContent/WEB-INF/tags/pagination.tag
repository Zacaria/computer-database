<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<%@ attribute name="current" required="true" type="java.lang.Integer"
	description="Current page"%>
<%@ attribute name="total" required="true" type="java.lang.Integer"
	description="Total page"%>
<%@ attribute name="range" required="true" type="java.lang.Integer"
	description="The limit of displayed computers"%>


<ul class="pagination">
	<li><a href="./dashboard?p=1&r=${range}" aria-label="Previous"> <span
			aria-hidden="true">&laquo;</span>
	</a></li>
	<c:if test="${current > 2 }">
		<li><a class="btn btn-default" role="button"
			href="./dashboard?p=${current - 2}&r=${range}">${current - 2}</a></li>
	</c:if>
	<c:if test="${current > 1 }">
		<li><a class="btn btn-default" role="button"
			href="./dashboard?p=${current - 1}&r=${range}">${current - 1}</a></li>
	</c:if>
	<li><a class="btn btn-primary active" role="button" href="#">${current}</a></li>
	<c:if test="${current < total}">
		<li><a class="btn btn-default" role="button"
			href="./dashboard?p=${current + 1}&r=${range}">${current + 1}</a></li>
	</c:if>
	<c:if test="${current < total - 1}">
		<li><a class="btn btn-default" role="button"
			href="./dashboard?p=${current + 2}&r=${range}">${current + 2}</a></li>
	</c:if>

	<li><a href="./dashboard?p=${total}&r=${range}" aria-label="Next">
			<span aria-hidden="true">&raquo;</span>
	</a></li>
</ul>