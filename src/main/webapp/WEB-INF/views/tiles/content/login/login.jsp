<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<style>
.error {
	padding: 15px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #a94442;
	background-color: #f2dede;
	border-color: #ebccd1;
}

.msg {
	padding: 15px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #31708f;
	background-color: #d9edf7;
	border-color: #bce8f1;
}

#login {
	width: 300px;
	padding: 20px;
	margin: 100px auto;
	background: #fff;
	-webkit-border-radius: 2px;
	-moz-border-radius: 2px;
	border: 1px solid #000;
}
</style>


<h1>Login</h1>

<div id="login">

	<c:if test="${ not empty error }">
		<div class="error">${error}</div>
	</c:if>
	
	<c:if test="${ not empty msg }">
		<div class="msg">${msg}</div>
	</c:if>
	
	<form name="loginForm" action="<c:url value='/login' />" method="POST">
		<table>
			<tr>
				<td>User:</td>
				<td><input name="principal" type="text" /></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><input name="credentials" type="password" /></td>
			</tr>
			<tr>
				<td colspan='2'><input name="submit" type="submit"
				  value="submit" /></td>
			</tr>
		</table>
		
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	</form>

	<br />
	<div>
		<a href="${pageContext.request.contextPath}/user/register">Register</a>
	</div>
	
	<br />
	<div>
    	<a href="${pageContext.request.contextPath}/auth/facebook"><button>Facebook</button></a>
	</div>

</div>
