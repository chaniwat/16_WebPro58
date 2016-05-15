<%@ page import="com.alumnisystem.utility.RouteHelper" %>
<%@ page import="com.alumnisystem.utility.ResponseHelper" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Alumni System - Login</title>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />

    <%--<link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet" />--%>
    <link href="<%= RouteHelper.generateURL( "assets/css/bootstrap.min.theme.css") %>" rel="stylesheet" />
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet" />
    <link href="<%= RouteHelper.generateURL( "assets/fonts/ahtiti.css") %>" rel="stylesheet" />
    <link href="<%= RouteHelper.generateURL( "assets/css/style.css") %>" rel="stylesheet" />
</head>

<body class="login-page">
    <sitedata contextPath="<%= request.getContextPath() %>"></sitedata>

    <div class="login-container">
        <center><h1>Login | เข้าสู่ระบบสมาชิก</h1></center>
        <%
            if(ResponseHelper.hasCodeInRequest()) {
                if(ResponseHelper.getRequestCode() == ResponseHelper.UNAUTHORIZED) {
                    out.println("<div class=\"alert alert-info\" role=\"alert\">เข้าสู่ระบบ</div>");
                } else if(ResponseHelper.getRequestCode() == ResponseHelper.BAD_LOGIN) {
                    out.println("<div class=\"alert alert-danger\" role=\"alert\">ชื่อผู้ใช้หรือรหัสผ่านผิด</div>");
                }
            }
        %>
        <form action="<%= RouteHelper.generateURL( "login")%>" method="POST" id="login-form">
            <div class="form-group username-group">
                <div class="input-group">
                    <span class="input-group-addon" id="username-addon">
                        <span class="fa fa-user"></span>
                    </span>
                    <input type="text" class="form-control" id="username" name="username" placeholder="Username" aria-describedby="username-addon" data-empty="false" />
                </div>
            </div>

            <div class="form-group password-group">
                <div class="input-group">
                    <span class="input-group-addon" id="password-addon">
                        <span class="fa fa-unlock-alt"></span>
                    </span>
                    <input type="password" class="form-control" id="password" name="password" placeholder="Password" aria-describedby="password-addon" data-empty="false" />
                </div>
            </div>

            <button type="submit" class="btn btn-success">Login</button>
        </form>
    </div>

    <script src="https://code.jquery.com/jquery-2.2.0.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    <script src="<%= RouteHelper.generateURL( "assets/js/script.js") %>"></script>
</body>
</html>