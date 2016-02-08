<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="layout/header.jsp" %>

<div class="container">
    <h1>Login Form</h1>
    <%
        if(request.getAttribute("res_code") != null) {
            if((int)request.getAttribute("res_code") == 1) {
                out.println("<div class=\"alert alert-danger\" role=\"alert\"><b>Error!</b> incorrect username or password</div>");
            } else if((int)request.getAttribute("res_code") == 2) {
                out.println("<div class=\"alert alert-info\" role=\"alert\">Please login first.</div>");
            }
        }
    %>
    <form action="login" method="POST">
        <div class="form-group">
            <label for="username">Username :</label>
            <input type="text" id="username" name="username" class="form-control" />
        </div>
        <div class="form-group">
            <label for="password">Password :</label>
            <input type="password" id="password" name="password" class="form-control" />
        </div>
        <button type="submit" class="btn btn-default">Login</button>
    </form>
</div>

<%@ include file="layout/footer.jsp" %>