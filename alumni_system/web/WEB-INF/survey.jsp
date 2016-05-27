<%@ page import="com.alumnisystem.utility.*" %>
<%@ include file="/WEB-INF/importlib.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    pageContext.setAttribute("formschema", FileHelper.getSurveySchema(((Survey) request.getAttribute("survey")).schemafile));
%>

<template:page>

    <template:head title="Alumni System - Survey">

        <template:style />

    </template:head>

    <template:body>

        <template:navbar />

        <div class="container">
            ${formschema}

            <template:footer />
        </div>

        <template:script/>
        <script>
            $("form").html(
                    $("form").html() +
                    " \n<input type=\'hidden\' name=\'surveytarget\' value=\'${requestScope.survey.id}\'>\n" +
                    "<div class=\"form-group\">\n" +
                    "    <div class=\"col-sm-offset-3 col-sm-9\">\n" +
                    "        <button type=\"submit\" id=\"staff-form-btn\" class=\"btn btn-success\">ส่งฟอร์ม</button>\n" +
                    "    </div>\n" +
                    "</div>"
            );

            $("form").attr("action", "${RouteHelper:generateURL("survey/send")}");
            $("form").attr("method", "POST");
        </script>

    </template:body>

</template:page>