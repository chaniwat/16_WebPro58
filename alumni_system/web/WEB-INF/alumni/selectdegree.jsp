<%@ page import="com.alumnisystem.utility.RouteHelper" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="template" uri="/WEB-INF/tlds/TemplateTag.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<template:page title="Alumni System - View Alumni">

    <template:navbar />

    <div class="container">

        <div class="page-header">
            <h1>เลือกระดับการศึกษา</h1>
        </div>

        <a href="<%= RouteHelper.generateURL( "alumni/bachelor") %>" class="btn btn-primary btn-lg btn-block">ปริญญาตรี</a>
        <a href="<%= RouteHelper.generateURL( "alumni/master") %>" class="btn btn-primary btn-lg btn-block">ปริญญาโท</a>
        <a href="<%= RouteHelper.generateURL( "alumni/doctoral") %>" class="btn btn-primary btn-lg btn-block">ปริญญาเอก</a>

        <template:footer/>
    </div>

</template:page>
