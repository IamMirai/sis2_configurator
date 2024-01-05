<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="lateralMenu.jsp"%>

<!DOCTYPE html>
<html>
	<head>
		<link rel='stylesheet' type='text/css' media='screen' href='<%=request.getContextPath()%>/css/emails.css'>
		<!--
		This link below prevents from web page to load an inexistence favicon and avoid innecesary payload.
		Remove this when favicon is loaded in the project.
		-->
		<link rel="icon" href="data:;base64,iVBORw0KGgo=">

		<title>SIS2 | WS EMAILS CONFIG</title>
	</head>
	<body>
	    <main>
            <h3 class="ws-email-config__title">Workspace Emails Configuration</h3>

            <div class="ws-email-config__container">
                <h4 class="ws-email-config__subtitle">Workspace Emails Details</h4>
                <br>
                <br>
                <div class="ws-email-config__default-email-details__container">
                    <div class="ws-email-config__default-email-details__subcontainer-text">
                        <span>Default Email:</span>
                    </div>

                    <div class="ws-email-config__default-email-details__subcontainer-value">
                        <%
                        String default_email = (String)session.getAttribute("wsActualDefaultEmail");
                        %>
                        <span class="ws-email-config__default-email-details__subcontainer-value__email-text"><%=default_email%></span>
                        <input hidden type="text" class="ws-email-config__default-email-details__subcontainer-value__email-input"/>
                    </div>

                    <div class="ws-email-config__default-email-details__subcontainer-button">
                        <button class="ws-email-config__default-email-details__subcontainer-button__button-change">change</button>

                        <div class="ws-email-config__default-email-details__subcontainer-button__options__container" style="display:none;">

                            <div class="ws-email-config__default-email-details__subcontainer-button__options__icon confirm">
                                <i class="fa-solid fa-check"></i>
                            </div>

                            <div class="ws-email-config__default-email-details__subcontainer-button__options__icon cancel">
                                <i class="fa-solid fa-xmark"></i>
                            </div>
                        </div>
                    </div>
                </div>
                <span class="ws-email-config__output"></span>
            </div>

            <script src="<%=request.getContextPath()%>/js/emails.js"></script>
        </main>
	</body>
</html>