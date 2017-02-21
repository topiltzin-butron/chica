<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<h1>Face generator</h1>

<c:if test="${not empty successMessage}">
<div id="message">
	${successMessage}
</div>
</c:if>

<table>
	<tr>
		<th>User</th>
		<th>Id</th>
		<th>Creation date</th>
		<th>Greet</th>
	</tr>
	<c:forEach items="${greetings}" var="greet">
		<tr>
			<td>${greet.user}</td>
			<td>${greet.id}</td>
			<td>${greet.creationDate}</td>
			<td>${greet.greet}</td>
		</tr>
	</c:forEach>
</table>
