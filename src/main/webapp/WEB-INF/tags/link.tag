<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<%@ attribute name="target" required="true" type="java.lang.String" description="Target page"%>
<%@ attribute name="label" required="true" type="java.lang.String" description="aria-label"%>
<%@ attribute name="page" required="false" type="java.lang.Integer" description="Page number"%>
<%@ attribute name="limit" required="false" type="java.lang.Integer" description="Page range"%>

<%@ attribute name="classes" required="false" type="java.lang.String" description="classes"%>
<%@ attribute name="search" required="false" type="java.lang.String" description="search"%>
<%@ attribute name="orderBy" required="false" type="java.lang.String" description="orderBy column"%>
<%@ attribute name="asc" required="false" type="java.lang.String" description="orderBy direction"%>

<!-- Sets default values -->
<c:if test="${empty page}">
	<c:set var="page" value="1" />
</c:if>
<c:if test="${empty limit}">
	<c:set var="limit" value="10" />
</c:if>

<!-- Minimum link value -->
<c:set var="link" value="${target}?p=${page}&r=${limit}" />

<!-- Add optional values to link -->
<c:if test="${not empty search}">
	<c:set var="link" value="${link}&s=${search}" />
</c:if>

<c:if test="${not empty orderBy}">
	<c:set var="link" value="${link}&col=${orderBy}" />
</c:if>

<c:if test="${not empty asc}">
	<c:set var="link" value="${link}&asc=${asc}" />
</c:if>


<!-- Fill up the HTML tag -->
<a class="${classes}" href="${link}">${label}</a>