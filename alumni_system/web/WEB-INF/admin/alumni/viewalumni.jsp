<%@ page import="com.alumnisystem.utility.RouteHelper" %>
<%@ page import="com.alumnisystem.model.Curriculum" %>
<%@ page import="com.alumnisystem.model.Alumni" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.alumnisystem.model.Track" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="template" uri="/WEB-INF/tlds/TemplateTag.tld" %>

<%
    String degree = (String)request.getAttribute("degree");
    String degreeth = "";
    switch (degree) {
        case "bachelor": degreeth = "ปริญญาตรี"; break;
        case "master": degreeth = "ปริญญาโท"; break;
        case "doctoral": degreeth = "ปริญญาเอก"; break;
    }

    ArrayList<Alumni> alumnis = (ArrayList<Alumni>)request.getAttribute("alumnis");
%>

<template:pageadmin>

    <div class="container-admin">

        <% if(alumnis != null && alumnis.size() > 0) { %>
        <div class="page-header">
            <h1>ข้อมูลศิษย์เก่า<%= degreeth %></h1>
        </div>

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
                <th>Edit</th>
            </tr>
            </thead>

            <tbody>
            <% for(Alumni alumni : alumnis) { %>
            <tr>
                <%
                    int student_id = 0;
                    for(Track track : alumni.getTracks()) {
                        if(track.getCurriculum().getId() == (Curriculum.Degree.valueOf(((String)request.getAttribute("degree")).toUpperCase())).getValue()) {
                %>
                <td><%= track.getStudent_id() %></td>
                <td><%= track.getGeneration() %></td>
                <%
                            student_id = track.getStudent_id();
                            break;
                        }
                    }
                %>
                <td><%= alumni.getPname_th() + alumni.getFname_th() %></td>
                <td><%= alumni.getLname_th() %></td>
                <td><%= alumni.getNickname() %></td>
                <td><%= alumni.getEmail() %></td>
                <td><%= alumni.getPhone() %></td>
                <td><a href="<%= RouteHelper.generateURL( "admin/alumni/" + degree + "/" + student_id)%>" class="btn btn-success">ดูข้อมูล</a></td>
                <td><a href="<%= RouteHelper.generateURL( "admin/alumni/" + degree + "/" + student_id + "?edit=true")%>" class="btn btn-warning">แก้ไขข้อมูล</a></td>
            </tr>
            <% } %>
            </tbody>

        </table>
        <% } else { %>
        <h1>ไม่มีข้อมูลศิษย์เก่า</h1>
        <% } %>

    </div>

</template:pageadmin>