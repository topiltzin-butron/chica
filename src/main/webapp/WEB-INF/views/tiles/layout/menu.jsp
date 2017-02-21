<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page session="true" %>

<a href="${pageContext.request.contextPath}" >Home</a> |  
<a href="${pageContext.request.contextPath}/face/show" >Create</a> | 
<a href="#" >Tales</a> | 
<a href="#" >Settings</a> | 
<a href="#" >Contact</a>

<br />
<a href="${pageContext.request.contextPath}/hello" >Hello</a> |  
<a href="${pageContext.request.contextPath}/helloMobile" >Hello Mobile</a> | 
<a href="${pageContext.request.contextPath}/chica/1" >Chica 1</a> | 
<a href="${pageContext.request.contextPath}/chica/2" >Chica 2</a> | 
<a href="${pageContext.request.contextPath}/chica/3" >Chica 3</a> | 
<a href="${pageContext.request.contextPath}/chica/5" >Chica 5</a> | 
<a href="${pageContext.request.contextPath}/chica/10" >Chica 10</a> | 
<a href="${pageContext.request.contextPath}/rayito" >Rayito doesn't exist</a>

<br />
<a href="${pageContext.request.contextPath}/greeting" >Greeting</a> |   
<a href="${pageContext.request.contextPath}/greeting/add" >Add</a>

<br />
<a href="${pageContext.request.contextPath}/vaca" >Vaca</a> | 
<a href="${pageContext.request.contextPath}/vaca/admin" >Admin</a> | 
<a href="${pageContext.request.contextPath}/vaca/dba" >DBA</a>

<br />
<a href="${pageContext.request.contextPath}/msgs/produce" >Produce</a> |  
<a href="${pageContext.request.contextPath}/msgs/produceWorkQueue" >Produce Work Queue</a> | 
<a href="${pageContext.request.contextPath}/msgs/producePublishSubscribe" >Produce Publish/Subscribe</a> | 
<a href="${pageContext.request.contextPath}/msgs/produceRouting" >Produce Routing</a> | 
<a href="${pageContext.request.contextPath}/msgs/produceTopic" >Produce Topic</a> | 
<a href="${pageContext.request.contextPath}/msgs/produceRpc" >Produce RPC</a> | 
<a href="${pageContext.request.contextPath}/msgs/produceRpc2" >Produce RPC 2</a>


<br />
<a href="${pageContext.request.contextPath}/social/facebook" >Facebook</a> | 
<a href="${pageContext.request.contextPath}/social/google" >Google</a> | 
<a href="${pageContext.request.contextPath}/social/twitter" >Twiter</a>  


<br />
<a href="${pageContext.request.contextPath}/app/requestMapping" >Request Mapping</a>

<c:if test="${pageContext.request.userPrincipal != null}">

	<form action="<c:url value='/logout' />" method="POST" id="logoutForm" >
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	</form>
	
	<script>
		function formSubmit() { 
			document.getElementById("logoutForm").submit();
		}
	</script>

	<h2>User: ${pageContext.request.userPrincipal.name} </h2>
	<br />
	<a href="javascript:formSubmit()">Logout</a>
 
</c:if>


<!-- <ul> 
	<li>Opcion 1</li>
	<li>Opcion 2</li>
	<li>Opcion 3</li>
	<li><ul>
			<li>Opci&oacute;n 1</li>
			<li>Opcion 2</li>
			<li>Opcion 3</li>
			<li>Opcion 4</li>
			<li>Opcion 5</li>
		</ul></li>
	<li>Opcion 4</li>
	<li>Opcion 5</li>
</ul>
 -->
