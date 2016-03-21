<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<%@ attribute name="target" required="true" type="java.lang.String" description="Target page" %>
<%@ attribute name="label" required="true" type="java.lang.String" description="aria-label" %>
<%@ attribute name="page" required="false" type="java.lang.Integer" description="Page number" %>
<%@ attribute name="limit" required="false" type="java.lang.Integer" description="Page range" %>

<%@ attribute name="classes" required="false" type="java.lang.String" description="classes" %>

<c:if test="${empty page}">
	<c:set var="page" value="1"/>
</c:if>
<c:if test="${empty limit}">
	<c:set var="limit" value="10"/>
</c:if>

<%-- <c:if test="${class}"> --%>
<%-- 	<c:set var="class" value=" "/> --%>
<%-- </c:if> --%>

<a class="${classes}" href="${target}?p=${page}&r=${limit}">${label}</a>