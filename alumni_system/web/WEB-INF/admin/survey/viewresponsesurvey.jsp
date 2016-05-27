<%@ include file="/WEB-INF/importlib.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<template:page adminPage="${true}">

    <template:head title="Alumni System - Admin - Response">

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

                <div class="page-header">
                    <h1>การตอบกลับของแบบสำรวจรหัส : ${requestScope.survey.id}</h1>
                </div>

                <sql:setDataSource var="myDatasource" dataSource="jdbc/AlumniDB" />

                <sql:query var="result" dataSource="${myDatasource}">
                    SELECT * FROM `${requestScope.survey.schemafile}`
                </sql:query>

                <form class="form-inline" id="table-searchform" onsubmit="javascript:;">
                    <div class="form-group">
                        <label for="table-searchtype">Search by </label>
                        <select class="form-control input-sm" id="table-searchtype" style="width: 140px;">
                            <option value="0">Response ID</option>
                            <% int i = 1; %>
                            <c:forEach var="columnName" items="${result.columnNames}">
                                <c:if test="${columnName != 'id'}">
                                    <option value="<%= i++ %>">${columnName}</option>
                                </c:if>
                            </c:forEach>
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
                            <c:forEach var="columnName" items="${result.columnNames}">
                                <th><c:out value="${columnName}"/></th>
                            </c:forEach>
                        </tr>
                    </thead>

                    <tbody>
                        <c:forEach var="row" items="${result.rowsByIndex}">
                        <tr>
                            <c:forEach var="column" items="${row}">
                                <td><c:out value="${column}"/></td>
                            </c:forEach>
                        </tr>
                        </c:forEach>
                    </tbody>

                </table>

        </div>

        <template:script>
            <script type="text/javascript" src="https://cdn.datatables.net/t/bs/dt-1.10.11,fc-3.2.1,fh-3.1.1,r-2.0.2,rr-1.1.1,sc-1.4.1,se-1.1.2/datatables.min.js"></script>
        </template:script>

    </template:body>

</template:page>