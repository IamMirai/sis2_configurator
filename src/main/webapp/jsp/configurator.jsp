<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name='viewport' content='width=device-width, initial-scale=1'>
		<title>SIS2 | CONFIGURATOR</title>
	</head>
	<body>
		<h2>Param:</h2>
		<p>User: <%out.println("user: " + request.getParameter("user"));%></p>
	</body>
</html>