<%@ include file="/WEB-INF/layouts/header.jsp"%>

<div class="container">
    <h1>Login Form</h1>
    <form action="<%= RouteHelper.generateURL(request, "login") %>" method="POST">
        <div class="form-group">
            <div class="form-group">
                <div class="input-group">
                    <span class="input-group-addon" id="username-addon">
                        <span class="fa fa-user"></span>
                    </span>
                    <input type="text" class="form-control" id="username" name="username" placeholder="Username" aria-describedby="username-addon" />
                </div>
            </div>

            <div class="form-group">
                <div class="input-group">
                    <span class="input-group-addon" id="password-addon">
                        <span class="fa fa-unlock-alt"></span>
                    </span>
                    <input type="password" class="form-control" id="password" name="password" placeholder="Password" aria-describedby="password-addon" />
                </div>
            </div>

            <button type="submit" class="btn btn-default">Login</button>
        </div>
    </form>
</div>

<%@ include file="/WEB-INF/layouts/footer.jsp"%>