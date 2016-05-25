<%@ include file="/WEB-INF/importlib.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% pageContext.setAttribute("degree", Curriculum.Degree.valueOf(((String) request.getAttribute("degree")).toUpperCase())); %>

<template:page adminPage="${true}">

    <template:head title="Alumni System - Admin - View All Alumni">

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

            <c:if test="${requestScope.alumnis != null && requestScope.alumnis.size() > 0}">
                <div class="page-header">
                    <h1>ข้อมูลศิษย์เก่าระดับการศึกษา${pageScope.degree.getNameTH()}</h1>
                </div>

                <form class="form-inline" id="table-searchform" onsubmit="javascript:;">
                    <div class="form-group">
                        <label for="table-searchtype">Search by </label>
                        <select class="form-control input-sm" id="table-searchtype" style="width: 140px;">
                            <option value="0">Student ID</option>
                            <option value="1">Generation</option>
                            <option value="2">First Name</option>
                            <option value="3">Last Name</option>
                            <option value="4">Nickname</option>
                            <option value="5">Email</option>
                            <option value="6">Phone</option>
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
                            <th>Student ID</th>
                            <th>Generation</th>
                            <th>First Name</th>
                            <th>Last Name</th>
                            <th>Nickname</th>
                            <th>Email</th>
                            <th>Phone</th>
                            <th>View</th>
                        </tr>
                    </thead>

                    <tbody>
                        <c:forEach var="alumni" items="${requestScope.alumnis}">
                            <tr>
                                <c:forEach var="track" items="${alumni.tracks}">
                                    <c:if test="${track.curriculum.id == pageScope.degree.getValue()}">
                                        <td><a href="${RouteHelper:generateURL("profile/".concat(track.student_id))}">${track.student_id}</a></td>
                                        <td>${track.generation}</td>
                                        <c:set var="student_id" scope="page" value="${track.student_id}" />
                                    </c:if>
                                </c:forEach>
                                <td>${alumni.pname_th.concat(alumni.fname_th)}</td>
                                <td>${alumni.lname_th}</td>
                                <td>${alumni.nickname}</td>
                                <td>${alumni.email}</td>
                                <td>${alumni.phone}</td>
                                <td width="1"><a href="${RouteHelper:generateURL("admin/alumni")}/${student_id}" class="btn btn-info btn-xs">ดูข้อมูล</a></td>
                            </tr>
                        </c:forEach>
                    </tbody>

                </table>
            </c:if>
            <c:if test="${!(requestScope.alumnis != null && requestScope.alumnis.size() > 0)}">
                <h1>ไม่มีข้อมูลศิษย์เก่า</h1>
            </c:if>

        </div>

        <template:script>
            <script type="text/javascript" src="https://cdn.datatables.net/t/bs/dt-1.10.11,fc-3.2.1,fh-3.1.1,r-2.0.2,rr-1.1.1,sc-1.4.1,se-1.1.2/datatables.min.js"></script>
        </template:script>

    </template:body>

</template:page>