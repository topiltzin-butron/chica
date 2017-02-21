<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>

<h1>App - Request Mapping</h1>

<div>

	<c:forEach var="handlerMethod" items="${handlerMethods}" >
		<b>${handlerMethod.key}</b>: ${handlerMethod.value} 
		<hr />
	</c:forEach>

</div>
