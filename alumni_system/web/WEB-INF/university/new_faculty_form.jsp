<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Alumni System - New Faculty</title>

    <%@ include file="/WEB-INF/layouts/header.jsp"%>
</head>

<body class="new-faculty-page">
    <div class="container">
        <center><h1>Add Faculty</h1></center>

        <form action="<%= RouteHelper.generateURL(request, "faculty/new") %>" method="POST" id="new-faculty-form">
            <div class="form-group">
                <div class="input-group">
                        <span class="input-group-addon" id="faculty-name-th-addon">
                            <span class="fa fa-user"></span>
                        </span>
                    <input type="text" class="form-control" id="faculty-name-th" name="faculty-name-th" placeholder="Name in Thai" aria-describedby="faculty-name-th-addon" />
                </div>
            </div>

            <div class="form-group">
                <div class="input-group">
                        <span class="input-group-addon" id="faculty-name-en-addon">
                            <span class="fa fa-user"></span>
                        </span>
                    <input type="text" class="form-control" id="faculty-name-en" name="faculty-name-en" placeholder="Name in English" aria-describedby="faculty-name-en-addon" />
                </div>
            </div>

            <button type="button" class="btn btn-default">ADD</button>
        </form>
    </div>

    <%@ include file="/WEB-INF/layouts/script-all.jsp"%>
    <script>
        $(document).ready(function() {

        });
    </script>
</body>
</html>