<%@ include file="/WEB-INF/importlib.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<template:page adminPage="${true}">

    <template:head title="Alumni System - Admin - New event">

        <template:style>
            <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap3-wysiwyg/0.3.3/bootstrap3-wysihtml5.min.css" rel="stylesheet" />
        </template:style>

    </template:head>

    <template:body>

        <template:navbar />

        <div class="container-admin">

            <div class="page-header">
                <h1>เพิ่มกิจกรรม/ข่าวสารใหม่</h1>
            </div>

            <div class="row">
                <div class="col-md-12">
                    <textarea class="textarea" style="width: 100%; height: 200px; font-size: 14px; line-height: 18px; border: 1px solid rgb(221, 221, 221); padding: 10px;" placeholder="Place some text here"></textarea>
                </div>
            </div>

        </div>

        <template:script>
            <script src="${RouteHelper:generateURL("assets/js/lib/bootstrap3-wysihtml5.all.min.js")}"></script>
        </template:script>

    </template:body>

</template:page>