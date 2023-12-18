<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="lateralMenu.jsp"%>

<!DOCTYPE html>
<html>
	<head>
		<link rel='stylesheet' type='text/css' media='screen' href='<%=request.getContextPath()%>/css/languages.css'>
		<title>SIS2 | LANGUAGES</title>
	</head>
	<body>
			<main>
				<h3 class="language_title">Workspace Language Configuration</h3>
				
				<div class="container">
					<h4 class="list_title">Workspace Languages</h4>
					
					<div class="list_container">
						<ul class="language_list">
						<%
						    Map<Integer, ArrayList<String>> treeMap_languages = (TreeMap<Integer, ArrayList<String>>)session.getAttribute("languages");

						    for(Integer key: treeMap_languages.keySet()){
						%>
							<li class="language_list_item">
								<div class="language_list_item_image"></div>
								<div class="language_list_item_text"><%=treeMap_languages.get(key).get(1)%></div>
							</li>
						<%
						    }
						%>
						</ul>
					</div>
					<div class="list_options">
							<div class="list_option add">
								<button class="button_add">
								    <span>Add</span>
								</button class="button_accept">
							</div>
							<div class="list_option remove">
                                <button class="button_remove" disabled>
								    <span>Remove</span>
								</button>
							</div>
						</div>
				</div>
				<div class="background_modal">
                    <div class="modal">
                        <h3>Select languages to add to workspace</h3>
                        <div class="languages_container">
                            <ul class="all_language_list">
                            <%
                                Map<Integer, ArrayList<String>> treeMap_languagesNotInWorkspace = (TreeMap<Integer, ArrayList<String>>) session.getAttribute("languagesNotInWorkspace");

                                for(Integer key: treeMap_languagesNotInWorkspace.keySet()){
                            %>
                                <li class="all_language_list_item">
                                    <div class="language_list_item_text"><%=treeMap_languagesNotInWorkspace.get(key).get(1)%></div>
                                </li>
                            <%
                                }
                            %>
                            </ul>
                        </div>
                            <div class="modal_options">
                                <div class="option accept">
                                    <button class="button_accept" disabled>
                                        <span>Accept</span>
                                    </button>
                                </div>
                                <div class="option cancel">
                                    <button class="button_cancel">
                                        <span>Cancel</span>
                                    </button>
                                </div>
                            </div>
                        </div>
				    </div>
				</div>
				<script src="<%=request.getContextPath()%>/js/languages.js"></script>
			</main>
		</div>
	</body>
</html>