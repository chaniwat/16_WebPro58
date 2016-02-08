<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="layout/header.jsp" %>

<div class="container">
    <h1>Welcome, <% out.print(request.getAttribute("user_name")); %></h1>
    <hr />
    your account type: <% out.print(request.getAttribute("user_type")); %><br /><br />
    <a href="./logout" class="btn btn-default">LOGOUT</a>
</div>

<%@ include file="layout/footer.jsp" %>
