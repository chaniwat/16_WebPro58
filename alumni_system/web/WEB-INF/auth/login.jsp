<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Alumni System - Login</title>

    <%@ include file="/WEB-INF/layouts/header.jsp"%>
</head>

<body class="login-page">
    <div class="container">
        <div class="login-container">
            <center><h1>Alumni System | Login</h1></center>
            <%
                if(ErrorHelper.hasRequestError(request)) {
                    if(ErrorHelper.checkRequestError(request, ErrorHelper.ERR_NO_LOGIN_401)) {
                        out.println("<div class=\"alert alert-info\" role=\"alert\">Please login first.</div>");
                    } else if(ErrorHelper.checkRequestError(request, ErrorHelper.ERR_BAD_LOGIN)) {
                        out.println("<div class=\"alert alert-danger\" role=\"alert\">Incorrect username or password.</div>");
                    }
                }
            %>
            <form action="<%= RouteHelper.generateURL(request, "login") %>" method="POST" id="login-form">
                <div class="form-group username-group">
                    <div class="input-group">
                        <span class="input-group-addon" id="username-addon">
                            <span class="fa fa-user"></span>
                        </span>
                        <input type="text" class="form-control" id="username" name="username" placeholder="Username" aria-describedby="username-addon" />
                    </div>
                </div>

                <div class="form-group password-group">
                    <div class="input-group">
                        <span class="input-group-addon" id="password-addon">
                            <span class="fa fa-unlock-alt"></span>
                        </span>
                        <input type="password" class="form-control" id="password" name="password" placeholder="Password" aria-describedby="password-addon" />
                    </div>
                </div>

                <button type="submit" class="btn btn-default">Login</button>
            </form>
        </div>
    </div>

    <%@ include file="/WEB-INF/layouts/script-all.jsp"%>
</body>
</html>