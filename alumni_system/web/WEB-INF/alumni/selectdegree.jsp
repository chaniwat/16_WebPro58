<%@ page import="com.alumnisystem.utility.RouteUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="template" uri="/WEB-INF/tlds/TemplateTag.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<template:page title="Alumni System - View Alumni">

    <div class="container">

        <div class="page-header">
            <h1>เลือกระดับการศึกษา</h1>
        </div>

        <a href="<%= RouteUtils.generateURL(request, "alumni/bachelor") %>" class="btn btn-primary btn-lg btn-block">ปริญญาตรี</a>
        <a href="<%= RouteUtils.generateURL(request, "alumni/master") %>" class="btn btn-primary btn-lg btn-block">ปริญญาโท</a>
        <a href="<%= RouteUtils.generateURL(request, "alumni/doctoral") %>" class="btn btn-primary btn-lg btn-block">ปริญญาเอก</a>

        <template:footerpage/>
    </div>

</template:page>
