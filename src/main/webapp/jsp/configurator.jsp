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
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.css" integrity="sha512-KOWhIs2d8WrPgR4lTaFgxI35LLOp5PRki/DxQvb7mlP29YZ5iJ5v8tiLWF7JLk5nDBlgPP1gHzw96cZ77oD7zQ==" crossorigin="anonymous" referrerpolicy="no-referrer" />
		<link rel='stylesheet' type='text/css' media='screen' href='<%=request.getContextPath()%>/css/configurator.css'>
		<title>SIS2 | CONFIGURATOR</title>
	</head>
	<body>
	
		<div class="poweredBy">
		<%
		Map<Integer, ArrayList<String>> treeMap_view_language_literals = (TreeMap<Integer, ArrayList<String>>) request.getAttribute("view_language_literals");
		
		for (Integer key : treeMap_view_language_literals.keySet()) {
		
		%>
			<h3 class="poweredBy_title"><%=treeMap_view_language_literals.get(key).get(1)%></h3>
		<%
		}
		%>
		</div>
	
		<header>
		
			<div class="header_title">
			
				<div class="header_img">
				</div>
				
				<div class="header_text">
				
					<h4><%=request.getAttribute("ws_title")%></h4>
					<h6><%=request.getAttribute("ws_subtitle")%></h6>
					
					<% 
						String ws_purpose = (String) request.getAttribute("ws_purpose");
					
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
	
	 	<nav>
	 	
	 		<div class="menu_list" style="margin-top: 100px">
	 		
	 			<div class="menu_item">
	 			
	 				<div class="item_title">
	 					<a class="btn_title"><i class="fa-solid fa-palette"></i>Graphical Appearance<i class="fa-solid fa-chevron-right dropdown" style="transform: rotate(0deg);"style="transform: rotate(0deg);"></i></a>
	 					
	 					<div class="submenu">
	 						<a class="submenu_item" href="#">Subitem 01</a>
	 						<a class="submenu_item" href="#">Subitem 02</a>
	 						<a class="submenu_item" href="#">Subitem 03</a>
	 					</div>
	 					
	 				</div>
	 			</div>
	 			
	 			<div class="menu_item">
	 			
	 				<div class="item_title">
	 					<a class="btn_title"><i class="fa-sharp fa-solid fa-circle-exclamation"></i>Warnings<i class="fa-solid fa-chevron-right dropdown" style="transform: rotate(0deg);"></i></a>
	 				
	 					<div class="submenu">
	 						<a class="submenu_item" href="#">Subitem 01</a>
	 						<a class="submenu_item" href="#">Subitem 02</a>
	 						<a class="submenu_item" href="#">Subitem 03</a>
	 					</div>
	 					
	 				</div>
	 			</div>
	 			
	 			<div class="menu_item">
	 			
	 				<div class="item_title">
	 					<a class="btn_title"><i class="fa-solid fa-circle-question"></i>Access policies<i class="fa-solid fa-chevron-right dropdown" style="transform: rotate(0deg);"></i></a>
	 				
		 				<div class="submenu">
		 						<a class="submenu_item" href="#">Subitem 01</a>
		 						<a class="submenu_item" href="#">Subitem 02</a>
		 						<a class="submenu_item" href="#">Subitem 03</a>
		 				</div>
	 				
	 				</div>
	 			</div>
	 		</div>
	 	</nav>
	<script src="<%=request.getContextPath()%>/js/configurator.js" async="true"></script>
</body>
</html> 