<%@include file="/WEB-INF/tags/importlib.tag"%>
<%@tag pageEncoding="UTF-8" %>

<%@ attribute name="isFromAdmin" rtexprvalue="true" %>

<jsp:doBody var="bodyContent"/>

<template:page adminPage="${isFromAdmin}">

    <template:head title="Alumni System - Error">

        <template:style />

    </template:head>

    <template:body>

        <template:navbar />

        <div class="container">
            <h1>${bodyContent}</h1>

            <c:if test="${!isFromAdmin}">
                <template:footer />
            </c:if>
        </div>

        <template:script />

    </template:body>

</template:page>