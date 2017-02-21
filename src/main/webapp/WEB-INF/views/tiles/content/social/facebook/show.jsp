<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<h1>${socialMediaName}</h1>

<h2>Hello ${facebookProfile.name}</h2>

<h3>Awesome Feed:</h3>

<c:forEach items="${feed}" var="post">
	<div>
		<b>${post.from.name}</b> wrote:
		<p>${post.message}</p>
		<c:if test="${not empty post.image}">
			<img alt="" src="${post.image}">
		</c:if>
		<hr />
	</div>
</c:forEach>
