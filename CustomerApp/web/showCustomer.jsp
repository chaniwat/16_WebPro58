<%@ page import="customer.Customer" %><%--
  Created by IntelliJ IDEA.
  User: Meranote
  Date: 1/20/2016
  Time: 12:25 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Title</title>

    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet" integrity="sha256-7s5uDGW3AHqw6xtJmNNtr+OBRJUlgkNJEo78P4b0yRw= sha512-nNo+yCHEyn0smMxSswnf/OnX6/KwJuZTlNZBjauKhTK0c+zT+q5JOCx0UFhXQ6rJR9jg6Es8gPuD2uZcYDLqSw==" crossorigin="anonymous">
</head>
<body>

    <div class="container">
        <h1>Customer Information</h1>
        <%
            Customer newCustomer = (Customer)request.getAttribute("newcustomer");
            if(newCustomer != null) {
                out.println("<b>Customer ID: </b>" + newCustomer.getId() + "<br />");
                out.println("<b>First Name: </b>" + newCustomer.getFirstName() + "<br />");
                out.println("<b>Last Name: </b>" + newCustomer.getLastName() + "<br />");
                out.println("<b>Company: </b>" + newCustomer.getCompany() + "<br />");
                out.println("<b>Mobile: </b>" + newCustomer.getMobile() + "<br />");
                out.println("<b>E-mail: </b>" + newCustomer.getEmail() + "<br />");
                out.println("<b>Address: </b>" + newCustomer.getAddr() + "<br />");
            } else {
                out.println("<h2>Not Found!</h2>");
            }
        %>
    </div>

    <script src="https://code.jquery.com/jquery-1.12.0.min.js\" type="text/javascript"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha256-KXn5puMvxCw+dAYznun+drMdG1IFl3agK0p/pqT9KAo= sha512-2e8qq0ETcfWRI4HJBzQiA3UoyFk6tbNyG+qSaIBZLyW9Xf3sWZHN/lxe9fTh1U45DpPf07yj94KsUHHWe4Yk1A==" crossorigin="anonymous"></script>
</body>
</html>
