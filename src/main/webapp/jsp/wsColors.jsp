<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="lateralMenu.jsp"%>

<!DOCTYPE html>
<html>
	<head>
		<link rel='stylesheet' type='text/css' media='screen' href='<%=request.getContextPath()%>/css/wsColors.css'>
		<!--
		This link below prevents from web page to load an inexistence favicon and avoid innecesary payload.
		Remove this when favicon is loaded in the project.
		-->
		<link rel="icon" href="data:;base64,iVBORw0KGgo=">

		<title>SIS2 | WS COLORS CONFIG</title>
	</head>
	<body>
	    <main>
            <h3 class="ws-color-config__title">Workspace Color Configuration</h3>

            <div class="ws-color-config__container">
                <h4 class="ws-color-config__subtitle">Workspace Color Picker</h4>

                <div class="color-picker__container">
                    <h5 class="color-picker__primary-color-text">Primary Color</h5>
                    <br>
                    <%
                    String colors = (String) session.getAttribute("wsActualColors");
                    %>
                        <div class="color-picker__background">
                            <label for="color-picker-primary-color-input" class="color-picker__actual-color primary-color">
                                <input type="color" value="<%=colors.substring(0,7)%>" id="color-picker-primary-color-input">
                            </label>
                        </div>
                </div>

                <div class="color-picker__container">
                    <h5 class="color-picker__secundary-color-text">Secundary Color</h5>
                    <br>
                        <div class="color-picker__background">
                            <label for="color-picker-secundary-color-input" class="color-picker__actual-color secundary-color">
                                <input type="color" value="<%=colors.substring(7,14)%>" id="color-picker-secundary-color-input">
                            </label>
                        </div>
                </div>

                <div class="color-picker__container">
                    <h5 class="color-picker__third-color-text">Third Color</h5>
                    <br>
                        <div class="color-picker__background">
                            <label for="color-picker-third-color-input" class="color-picker__actual-color third-color">
                                <input type="color" value="<%=colors.substring(14)%>" id="color-picker-third-color-input">
                            </label>
                        </div>
                </div>
                <br>
                <div class="save-colors__container">
                    <button class="save-colors__button">Save</button>
                </div>
            </div>
            <script src="<%=request.getContextPath()%>/js/wsColors.js"></script>
        </main>
	</body>
</html>