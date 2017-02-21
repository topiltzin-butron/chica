<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><tiles:getAsString name="title" /></title>

<link href="<c:url value='/resources/core/css/chica.css' />" rel="stylesheet" media="screen" />


</head>
<body>

<div id="page">
	<div id="header">
		<div id="logo"><tiles:insertAttribute name="logo" /></div>
		<div id="slogan"><tiles:insertAttribute name="header" /></div>
		<div id="menu"><tiles:insertAttribute name="menu" /></div>
	</div>
	<div id="content">
		<tiles:insertAttribute name="content" />
	</div>
	<div id="footer">
		<tiles:insertAttribute name="footer" />
	</div>
</div>

</body>
</html>