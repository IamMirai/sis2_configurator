<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.TreeMap"%>

<%@ include file="header.jsp"%>

<!DOCTYPE html>
<html>
	<head>
		<link rel='stylesheet' type='text/css' media='screen' href='<%=request.getContextPath()%>/css/lateralMenu.css'>
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.css" integrity="sha512-KOWhIs2d8WrPgR4lTaFgxI35LLOp5PRki/DxQvb7mlP29YZ5iJ5v8tiLWF7JLk5nDBlgPP1gHzw96cZ77oD7zQ==" crossorigin="anonymous" referrerpolicy="no-referrer" />
	</head>
	<body>
		<!--This div below encapsulates the nav and language content-->
		<div class="flex_container">
			<nav>
		 		<div class="menu_list" style="margin-top: 100px">
					<%
					Map<Integer, ArrayList<String>> treeMap_menu_items = new TreeMap<Integer, ArrayList<String>>();
					Map<Integer, ArrayList<String>> treeMap_menu_sub_items = new TreeMap<Integer, ArrayList<String>>();
					
					Integer cont1 = 0;
					Integer cont2 = 0;
					
					for (Integer key : treeMap_view_language_literals.keySet()) {
						if(treeMap_view_language_literals.get(key).get(5) != null && treeMap_view_language_literals.get(key).get(4) != null){
							treeMap_menu_sub_items.put(cont1, treeMap_view_language_literals.get(key));
							cont1++;
						}else if(treeMap_view_language_literals.get(key).get(5) != null){
							treeMap_menu_items.put(cont2, treeMap_view_language_literals.get(key));
							cont2++;
						}
					}
					for (Integer menuKey : treeMap_menu_items.keySet()) {
					%>	 		
		 		
		 			<div class="menu_item">
		 			
		 				<div class="item_title">
		 					<a class="btn_title"><i class="fa-solid fa-palette"></i><%=treeMap_menu_items.get(menuKey).get(1)%><i class="fa-solid fa-chevron-right dropdown" style="transform: rotate(0deg)"></i></a>
		 					
		 					<div class="submenu">
			 					<%
			 					for (Integer subMenuKey : treeMap_menu_sub_items.keySet()) {
			 						if(treeMap_menu_sub_items.get(subMenuKey).get(4).equals(treeMap_menu_items.get(menuKey).get(2))){
			 							if(Boolean.parseBoolean(treeMap_menu_sub_items.get(subMenuKey).get(3)) == true){
			 					%>
		 						<a class="submenu_item" href="<%=treeMap_menu_sub_items.get(subMenuKey).get(6)%>"><%=treeMap_menu_sub_items.get(subMenuKey).get(1)%></a>
		 						<%		
		 								}else{
		 						%>
		 						<a class="submenu_item disabled" href="#"><%=treeMap_menu_sub_items.get(subMenuKey).get(1)%><i class="fa-regular fa-circle-question info"></i><span class="tooltip"><%=treeMap_view_language_literals.get(11).get(1)%></span></a>
		 						<%
			 								 }
			 						}
		 						}
		 						%>
		 					</div>
		 				</div>
		 			</div>
		 			<%
					}
		 			%>
		 		</div>
		 	</nav>
	 	<script src="<%=request.getContextPath()%>/js/lateralMenu.js"></script>
	</body>
</html>