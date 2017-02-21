<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<h1>Add greet</h1>

<form:form action="create" modelAttribute="greet" method="POST">

	<table>
		<tr>
			<td>
				<form:label path="user">User</form:label>
				<form:errors path="user" />
			</td>
			<td>
				<input id="user" name="user" value="" type="text" >
			</td>
		</tr>
		<tr>
			<td>
				<form:label path="greet">Greet</form:label>
				<form:errors path="greet" />
			</td>
			<td>
				<input id="greet" name="greet" value="" type="text" >
			</td>
		</tr>
		<tr>
			<td colspan="2"><input type="submit" value="Generate" /></td>
		</tr>
	</table>

</form:form>