<%@ include file="/WEB-INF/importlib.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% pageContext.setAttribute("degree", Curriculum.Degree.valueOf(((String) request.getAttribute("degree")).toUpperCase())); %>

<template:page>

    <template:head title="Alumni System - View Alumni">

        <template:style />

    </template:head>

    <template:body>

        <template:navbar />

        <div class="container">

            <div class="page-header">
                <h1>เลือกรุ่นการศึกษา <small>${pageScope.degree.getNameTH()}</small></h1>
            </div>

            <a href="${RouteHelper:generateURL("alumni/".concat(requestScope.degree).concat("/all"))}" class="btn btn-info btn-lg btn-block">แสดงทุกรุ่น</a>

            <c:if test="${requestScope.generations != null}">
                <c:forEach items="${requestScope.generations}" var="generation">
                    <a href="${RouteHelper:generateURL("alumni/".concat(requestScope.degree).concat("/").concat(generation))}" class="btn btn-success btn-lg btn-block">รุ่นที่ ${generation}</a>
                </c:forEach>
            </c:if>

            <template:footer/>
        </div>

        <template:script />

    </template:body>

</template:page>
