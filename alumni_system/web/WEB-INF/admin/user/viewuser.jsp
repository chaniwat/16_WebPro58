<%@ page import="com.alumnisystem.factory.StaffFactory" %>
<%@ page import="com.alumnisystem.factory.AlumniFactory" %>
<%@ page import="com.alumnisystem.factory.TeacherFactory" %>
<%@ include file="/WEB-INF/importlib.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    switch (User.Type.valueOf(((String) request.getAttribute("type")).toUpperCase())) {
        case ALUMNI:
            pageContext.setAttribute("nameTH", "ศิษย์เก่า");
            request.setAttribute("users", new AlumniFactory().all());
            break;
        case TEACHER:
            pageContext.setAttribute("nameTH", "อาจารย์");
            request.setAttribute("users", new TeacherFactory().all());
            break;
        case STAFF:
            pageContext.setAttribute("nameTH", "พนักงาน/เจ้าหน้าที่");
            request.setAttribute("users", new StaffFactory().all());
            break;
    }
%>

<template:page adminPage="${true}">

    <template:head title="Alumni System - Admin - View All User">

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

            <c:if test="${requestScope.users != null && requestScope.users.size() > 0}">
                <div class="page-header">
                    <h1>ข้อมูลผู้ใช้ประเภท</h1>
                </div>

                <form class="form-inline" id="table-searchform" onsubmit="javascript:;">
                    <div class="form-group">
                        <label for="table-searchtype">Search by </label>
                        <select class="form-control input-sm" id="table-searchtype" style="width: 140px;">
                            <option value="0">User ID</option>
                            <option value="1">
                                <c:if test="${requestScope.type == 'alumni'}">
                                    Alumni ID
                                </c:if>
                                <c:if test="${requestScope.type == 'staff'}">
                                    Staff ID
                                </c:if>
                                <c:if test="${requestScope.type == 'teacher'}">
                                    Teacher ID
                                </c:if>
                            </option>
                            <option value="2">First Name</option>
                            <option value="3">Last Name</option>
                            <c:if test="${requestScope.type == 'staff'}">
                                <option value="4">Section</option>
                                <option value="5">Role</option>
                            </c:if>
                            <c:if test="${requestScope.type != 'staff'}">
                                <option value="4">Role</option>
                            </c:if>
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
                            <th>User ID</th>
                            <c:if test="${requestScope.type == 'alumni'}">
                                <th>Alumni ID</th>
                            </c:if>
                            <c:if test="${requestScope.type == 'staff'}">
                                <th>Staff ID</th>
                            </c:if>
                            <c:if test="${requestScope.type == 'teacher'}">
                                <th>Teacher ID</th>
                            </c:if>
                            <th>First Name</th>
                            <th>Last Name</th>
                            <c:if test="${requestScope.type == 'staff'}">
                                <th>Section</th>
                            </c:if>
                            <th>Role</th>
                            <th>View</th>
                        </tr>
                    </thead>

                    <tbody>
                        <c:forEach var="user" items="${requestScope.users}">
                            <tr>
                                <td>${user.id}</td>
                                <c:if test="${requestScope.type == 'alumni'}">
                                    <td>${user.alumni_id}</td>
                                </c:if>
                                <c:if test="${requestScope.type == 'staff'}">
                                    <td>${user.staff_id}</td>
                                </c:if>
                                <c:if test="${requestScope.type == 'teacher'}">
                                    <td>${user.teacher_id}</td>
                                </c:if>
                                <td>${user.pname_th.concat(user.fname_th)}</td>
                                <td>${user.lname_th}</td>
                                <c:if test="${requestScope.type == 'staff'}">
                                    <td>${user.section.name_th}</td>
                                </c:if>
                                <td>${user.admin ? 'ADMIN' : 'USER'}</td>
                                <td width="1"><a href="${RouteHelper:generateURL("admin/profile")}/${user.id}" class="btn btn-info btn-xs">ดูข้อมูล</a></td>
                            </tr>
                        </c:forEach>
                    </tbody>

                </table>
            </c:if>
            <c:if test="${!(requestScope.users != null && requestScope.users.size() > 0)}">
                <h1>ไม่พบข้อมูล</h1>
            </c:if>

        </div>

        <template:script>
            <script type="text/javascript" src="https://cdn.datatables.net/t/bs/dt-1.10.11,fc-3.2.1,fh-3.1.1,r-2.0.2,rr-1.1.1,sc-1.4.1,se-1.1.2/datatables.min.js"></script>
        </template:script>

    </template:body>

</template:page>