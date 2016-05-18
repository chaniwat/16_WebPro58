<%@ include file="/WEB-INF/importlib.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<template:page>

    <template:head title="Alumni System - View Alumni">

        <template:style />

    </template:head>

    <template:body>

        <template:navbar />

        <div class="container">

            <div class="page-header">
                <h1>เลือกระดับการศึกษา</h1>
            </div>

            <a href="${RouteHelper:generateURL("alumni/bachelor")}" class="btn btn-primary btn-lg btn-block">ปริญญาตรี</a>
            <a href="${RouteHelper:generateURL("alumni/master")}" class="btn btn-primary btn-lg btn-block">ปริญญาโท</a>
            <a href="${RouteHelper:generateURL("alumni/doctoral")}" class="btn btn-primary btn-lg btn-block">ปริญญาเอก</a>

            <template:footer/>

        </div>

        <template:script />

    </template:body>


</template:page>
