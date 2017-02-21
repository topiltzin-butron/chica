<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<h1>Connected to Facebook!</h1>

Click <a href="${pageContext.request.contextPath}/social/facebook" >here</a> to see your awesome Facebook feed.


<form:form action="create" modelAttribute="face" method="POST">

	<table>
		<tr>
			<td>
				<form:label path="eyes">Eyes</form:label>
				<form:errors path="eyes" />
			</td>
			<td>
				
				<%-- <form:radiobuttons path="eyes" items="${eyesList}" itemLabel="" /> --%>
					
				<c:forEach items="${eyesList}" var="eye_item">
					<c:set var="eyes_image_url" value="/resources/core/images/face/eyes/"/>
					<input id="${eye_item}" name="eyes" value="${eye_item}" type="radio">
					<label for="${eye_item}">
						<img src="<c:url value='${eyes_image_url}${eye_item}.png' />" width="50" height="50" />
					</label>
				</c:forEach>
				
			</td>
		</tr>
		<tr>
			<td>
				<form:label path="nose">Nose</form:label>
				<form:errors path="nose" />
			</td>
			<td>
				<%-- <form:radiobuttons path="nose" items="${nosesList}" /> --%>
				
				<c:forEach items="${nosesList}" var="nose_item">
					<c:set var="noses_image_url" value="/resources/core/images/face/noses/"/>
					<input id="${nose_item}" name="nose" value="${nose_item}" type="radio">
					<label for="${nose_item}">
						<img src="<c:url value='${noses_image_url}${nose_item}.png' />" width="50" height="50" />
					</label>
				</c:forEach>
				
				
			</td>
		</tr>
		<tr>
			<td>
				<form:label path="mouth">Mouth</form:label>
				<form:errors path="mouth" />
			</td>
			<td>
				<%-- <form:radiobuttons path="mouth" items="${mouthsList}" /> --%>
				
				<c:forEach items="${mouthsList}" var="mouth_item">
					<c:set var="mouths_image_url" value="/resources/core/images/face/mouths/"/>
					<input id="${mouth_item}" name="mouth" value="${mouth_item}" type="radio">
					<label for="${mouth_item}">
						<img src="<c:url value='${mouths_image_url}${mouth_item}.png' />" width="50" height="50" />
					</label>
				</c:forEach>
				
			</td>
		</tr>
		<tr>
			<td colspan="2"><input type="submit" value="Generate" /></td>
		</tr>
	</table>

</form:form>