<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.TreeMap"%>

<%@ include file="lateralMenu.jsp"%>

<!DOCTYPE html>
<html>
	<head>
		<link rel='stylesheet' type='text/css' media='screen' href='<%=request.getContextPath()%>/css/configurator.css'>
		<!-- 
		This link below prevents from web page to load an inexistence favicon and avoid innecesary payload.
		Remove this when favicon is loaded in the project.
		-->
		<link rel="icon" href="data:;base64,iVBORw0KGgo=">
		
		<title>SIS2 | CONFIGURATOR</title>
	</head>
	<body>
	 	<a class="" href="<%=request.getContextPath()%>/jsp/logout.jsp">Log out</a>
	 	
		<script src="<%=request.getContextPath()%>/js/configurator.js"></script>
	</body>
</html> 