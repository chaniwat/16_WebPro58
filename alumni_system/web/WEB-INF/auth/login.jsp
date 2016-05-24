<%@ include file="/WEB-INF/importlib.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<template:page>

    <template:head title="Alumni System - Login">
        <template:style />
    </template:head>

    <template:body>

        <template:navbar />

        <div class="container">

            <h1>เข้าสู่ระบบ</h1>

            <c:if test="${ResponseHelper:hasCodeInRequest()}">
                <c:if test="${ResponseHelper:getRequestCode() == ResponseHelper.UNAUTHORIZED}">
                    <div class="alert alert-info alert-dismissible" role="alert">
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        เข้าสู่ระบบ
                    </div>
                </c:if>
                <c:if test="${ResponseHelper:getRequestCode() == ResponseHelper.BAD_LOGIN}">
                    <div class="alert alert-danger alert-dismissible" role="alert">
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        ชื่อผู้ใช้หรือรหัสผ่านผิด
                    </div>
                </c:if>
            </c:if>

            <form action="${RouteHelper:generateURL("login")}" method="POST" id="login-form">
                <div class="form-group" id="username-group">
                    <div class="input-group">
                            <span class="input-group-addon" id="username-addon">
                                <span class="fa fa-user"></span>
                            </span>
                        <input type="text" class="form-control" id="username" name="username" placeholder="Username" aria-describedby="username-addon" data-empty="false" />
                    </div>
                </div>

                <div class="form-group" id="password-group">
                    <div class="input-group">
                            <span class="input-group-addon" id="password-addon">
                                <span class="fa fa-unlock-alt"></span>
                            </span>
                        <input type="password" class="form-control" id="password" name="password" placeholder="Password" aria-describedby="password-addon" data-empty="false" />
                    </div>
                </div>

                <button type="submit" class="btn btn-success">Login</button>
            </form>

            <template:footer />

        </div>

        <template:script />

    </template:body>

</template:page>