<%@ page import="model.utility.RouteUtils" %>
<%@ page import="java.util.TreeSet" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="template" uri="/WEB-INF/tlds/TemplateTag.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    String degree = (String)request.getAttribute("degree");
    String degreeth = "";
    switch (degree) {
        case "bachelor": degreeth = "ปริญญาตรี"; break;
        case "master": degreeth = "ปริญญาโท"; break;
        case "doctoral": degreeth = "ปริญญาเอก"; break;
    }
%>

<template:page title="Alumni System - View Alumni">

    <div class="container">

        <div class="page-header">
            <h1>เลือกรุ่นการศึกษา <small><%= degreeth %></small></h1>
        </div>

        <a href="<%= RouteUtils.generateURL(request, "alumni/" + degree + "/all") %>" class="btn btn-info btn-lg btn-block">แสดงทุกรุ่น</a>

        <%
            TreeSet<Integer> generations = (TreeSet<Integer>)request.getAttribute("generations");
            if(generations != null) {
                for(Integer i : generations) {
        %>
        <a href="<%= RouteUtils.generateURL(request, "alumni/" + degree + "/" + i) %>" class="btn btn-success btn-lg btn-block">รุ่นที่ <%= i %></a>
        <%
                }
            }
        %>

        <template:footerpage/>
    </div>

</template:page>
