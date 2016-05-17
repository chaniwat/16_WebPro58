<%@ page import="com.alumnisystem.utility.RouteHelper" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="template" uri="/WEB-INF/tlds/TemplateTag.tld" %>

<%
    if(RouteHelper.isRequestStaticResource()) {
        return;
    }

    pageContext.setAttribute("isFromAdmin", session.getAttribute("route.errorFromAdmin"));
    session.setAttribute("route.errorFromAdmin", false);
%>

<template:errorPage isFromAdmin="${isFromAdmin}">
    ไม่พบหน้านี้ - 404
</template:errorPage>