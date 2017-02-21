<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<h1>TILES - ${title}</h1>
<p>
	<c:if test="${not empty msg}">
		Hello ${msg}
	</c:if>

	<c:if test="${empty msg}">
		Welcome amigo!
	</c:if>
</p>

We are in ${country}

<h2>${chica}</h2>
