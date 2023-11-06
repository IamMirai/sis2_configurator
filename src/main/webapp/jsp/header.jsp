<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.TreeMap"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name='viewport' content='width=device-width, initial-scale=1'>
		<link rel='stylesheet' type='text/css' media='screen' href='<%=request.getContextPath()%>/css/header.css'>
	</head>
	<body>
		<div class="poweredBy">
		<%
		Map<Integer, ArrayList<String>> treeMap_view_language_literals = (TreeMap<Integer, ArrayList<String>>) session.getAttribute("view_language_literals");
		%>
			<h3 class="poweredBy_title"><%=treeMap_view_language_literals.get(0).get(1)%></h3>
		</div>
	
		<header>
		
			<div class="header_title">
			
				<div class="header_img">
				</div>
				
				<div class="header_text">
				
					<h4><%=session.getAttribute("ws_title")%></h4>
					<h6><%=session.getAttribute("ws_subtitle")%></h6>
					
					<% 
						String ws_purpose = (String) session.getAttribute("ws_purpose");
					
						ws_purpose = ws_purpose.trim();

						switch(ws_purpose) {
					    case "t":
					    	ws_purpose = "Plataforma de test";
					      break;

					    default:
					    	ws_purpose = "Opción inválida";
					      break;
					 	 }
					
					%>
					
					<h6><%=ws_purpose%></h6>
				</div>
			</div>
			
			<div class="empty">
			</div>
			
			<div class="header_user">
			
				<div class="header_user_img">
				</div>
				
				<div class="header_user_data">
					<p class="header_user_data_username"><%=request.getParameter("user")%></p>
					<p>example@gmail.com</p>
					<p>Example</p>
				</div>
			</div>
		</header>
	</body>
</html>