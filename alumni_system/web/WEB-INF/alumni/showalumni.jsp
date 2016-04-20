<%@ page import="model.Alumni" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Curriculum" %>
<%@ page import="model.utility.RouteUtils" %>
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

<template:page title="Alumni System - View Alumni">

    <div class="container">

        <% if(alumnis != null && alumnis.size() > 0) { %>
        <div class="page-header">
            <h1>ข้อมูลศิษย์เก่าระดับการศึกษา<%= degreeth %> <% if(request.getAttribute("generation") != null) { %><small>รุ่นที่ <%= request.getAttribute("generation") %></small><% } %></h1>
        </div>

        <table class="table table-bordered" id="alumni-table">

            <thead>
                <tr>
                    <th>Student ID</th>
                    <th>Generation</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Nickname</th>
                    <th>Occupation</th>
                </tr>
            </thead>

            <tbody>
            <% for(Alumni alumni : alumnis) { %>
                <tr>
                    <%
                        for(Alumni.Track track : alumni.getTracks()) {
                            if(track.getTrack().getCurriculum().getCurriculum_id() == (Curriculum.Degree.valueOf(((String)request.getAttribute("degree")).toUpperCase())).getValue()) {
                    %>
                        <td><a href="<%= RouteUtils.generateURL(request, "profile/" + track.getStudent_id()) %>"><%= track.getStudent_id() %></a></td>
                        <td><%= track.getGeneration() %></td>
                    <%
                            }
                        }
                    %>
                    <td><%= alumni.getPname_th() + alumni.getFname_th() %></td>
                    <td><%= alumni.getLname_th() %></td>
                    <td><%= alumni.getNickname() %></td>
                    <td><%= alumni.getOccupation() %></td>
                </tr>
            <% } %>
            </tbody>

        </table>
        <% } else { %>
            <h1>ไม่มีข้อมูลศิษย์เก่า</h1>
        <% } %>

        <template:footerpage/>
    </div>

</template:page>
