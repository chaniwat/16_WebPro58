<%@ page import="com.alumnisystem.factory.EventFactory" %>
<%@ page import="com.alumnisystem.exception.EventNotFound" %>
<%@ include file="/WEB-INF/importlib.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    try {
        Event event = new EventFactory().find(Integer.parseInt(request.getParameter("event_id")));
        pageContext.setAttribute("event", event);
    } catch (EventNotFound ignored) {
        response.sendRedirect("admin/event/all");
        return;
    }
%>

<template:page>

    <template:head title="Alumni System - Event - ${event.title}">

        <template:style />

    </template:head>

    <template:body>

        <template:navbar />

        <div class="container page-event">

            <div class="page-header" style="border-bottom: 1px solid #d6dadb;">
                <h1>${event.title}</h1>
                สร้างเมื่อ: ${event.date} | เวลา: ${event.time}
            </div>

            ${event.detail}

            <template:footer />

        </div>

        <template:script />

    </template:body>

</template:page>
