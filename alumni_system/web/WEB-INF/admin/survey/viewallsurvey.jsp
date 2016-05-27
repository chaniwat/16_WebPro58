<%@ page import="com.alumnisystem.factory.SurveyFactory" %>
<%@ include file="/WEB-INF/importlib.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% pageContext.setAttribute("surveys", new SurveyFactory().all()); %>

<template:page adminPage="${true}">

    <template:head title="Alumni System - Admin - View All Event">

        <template:style>
            <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/t/bs/dt-1.10.11,fc-3.2.1,fh-3.1.1,r-2.0.2,rr-1.1.1,sc-1.4.1,se-1.1.2/datatables.min.css"/>
        </template:style>

    </template:head>

    <style>
        table td {
            vertical-align: middle !important;
        }
    </style>

    <template:body>

        <template:navbar />

        <div class="container-admin">

            <c:if test="${surveys != null && surveys.size() > 0}">
                <div class="page-header">
                    <h1>แบบสอบถาม/สำรวจทั้งหมด</h1>
                </div>

                <form class="form-inline" id="table-searchform" onsubmit="javascript:;">
                    <div class="form-group">
                        <label for="table-searchtype">Search by </label>
                        <select class="form-control input-sm" id="table-searchtype" style="width: 140px;">
                            <option value="0">Survey ID</option>
                            <option value="1">Name</option>
                            <option value="2">Create Date</option>
                            <option value="3">Schema</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="sr-only" for="table-searchinput">Search</label>
                        <input type="text" class="form-control input-sm" id="table-searchinput" placeholder="Search" style="width: 200px;">
                    </div>
                </form>
                <br />

                <table class="table table-bordered" id="alumni-table">

                    <thead>
                    <tr>
                        <th>Survey ID</th>
                        <th>Name</th>
                        <th>Create Date</th>
                        <th>Schema</th>
                        <th>View Page</th>
                        <th>View Response</th>
                    </tr>
                    </thead>

                    <tbody>
                    <c:forEach var="survey" items="${surveys}">
                        <tr>
                            <td>${survey.id}</td>
                            <td>${survey.name}</td>
                            <td>${survey.timecreate}</td>
                            <td>${survey.schemafile}</td>
                            <td width="1"><a href="${RouteHelper:generateURL("survey/")}${survey.id}" class="btn btn-primary btn-xs">ดูหน้าฟอร์ม</a></td>
                            <td width="1"><a href="${RouteHelper:generateURL("admin/survey/")}${survey.id}" class="btn btn-primary btn-xs">ดูการตอบกลับ</a></td>
                        </tr>
                    </c:forEach>
                    </tbody>

                </table>
            </c:if>
            <c:if test="${!(surveys != null && surveys.size() > 0)}">
                <h1>ไม่มีแบบสำรวจในระบบ</h1>
            </c:if>

        </div>

        <template:script>
            <script type="text/javascript" src="https://cdn.datatables.net/t/bs/dt-1.10.11,fc-3.2.1,fh-3.1.1,r-2.0.2,rr-1.1.1,sc-1.4.1,se-1.1.2/datatables.min.js"></script>
        </template:script>

    </template:body>

</template:page>