<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Alumni System</title>

    <%@ include file="/WEB-INF/layouts/header.jsp"%>
</head>

<body>
    <div class="block">
        <div class="container">
            <h1>Bello!!</h1>

            <a href="<%= RouteHelper.generateURL(request, "faculty/new") %>" class="btn btn-default">ADD NEW FACULTY</a> <a href="<%= RouteHelper.generateURL(request, "major/new") %>" class="btn btn-default">ADD NEW MAJOR</a> <a href="<%= RouteHelper.generateURL(request, "student/new") %>" class="btn btn-default">ADD NEW STUDENT</a> <a href="<%= RouteHelper.generateURL(request, "logout") %>" class="btn btn-default">LOGOUT</a>
        </div>
    </div>

    <%@ include file="/WEB-INF/layouts/script-all.jsp"%>
</body>
</html>