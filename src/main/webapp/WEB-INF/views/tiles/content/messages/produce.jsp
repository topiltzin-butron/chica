<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<h1>Message producer</h1>

<c:if test="${produced}">
<div id="message">
	Message produced successfully!
</div>
</c:if>