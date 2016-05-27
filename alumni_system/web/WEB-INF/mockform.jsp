<%@ page import="com.alumnisystem.utility.*" %>
<%@ include file="/WEB-INF/importlib.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    pageContext.setAttribute("formschema", FileHelper.getSurveySchema((String) request.getAttribute("schema")));
%>

<template:page adminPage="${true}">

    <template:head title="Alumni System - Admin - Mock Form">

        <template:style />

    </template:head>

    <template:body>

        <template:navbar />

        <div class="container">
            ${formschema}
        </div>

        <template:script/>

    </template:body>

</template:page>
