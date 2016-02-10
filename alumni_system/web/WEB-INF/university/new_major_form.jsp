<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Alumni System - New Faculty</title>

    <%@ include file="/WEB-INF/layouts/header.jsp"%>
</head>

<body class="new-major-page">
    <div class="container">
        <center><h1>Add Major</h1></center>

        <form action="<%= RouteHelper.generateURL(request, "major/new") %>" method="POST" id="new-major-form">

            <%-- TODO Faculty Selection --%>

            <div class="form-group">
                <div class="input-group">
                        <span class="input-group-addon" id="major-name-th-addon">
                            <span class="fa fa-user"></span>
                        </span>
                    <input type="text" class="form-control" id="major-name-th" name="major-name-th" placeholder="Name in Thai" aria-describedby="major-name-th-addon" />
                </div>
            </div>

            <div class="form-group">
                <div class="input-group">
                        <span class="input-group-addon" id="major-name-en-addon">
                            <span class="fa fa-user"></span>
                        </span>
                    <input type="text" class="form-control" id="major-name-en" name="major-name-en" placeholder="Name in English" aria-describedby="major-name-en-addon" />
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