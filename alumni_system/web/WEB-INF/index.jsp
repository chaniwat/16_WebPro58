<%@ page import="model.utility.RouteUtils" %>
<%@ page import="model.auth.Authorization" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="template" uri="/WEB-INF/tlds/TemplateTag.tld" %>

<%
    Authorization auth = Authorization.getAuthInstance(session);
%>

<template:page title="Alumni System">
    <div class="jumbotron">
        <div class="container">
            <h1>ยินดีต้อนรับ</h1>
            <p>ระบบศิษย์เก่า คณะเทคโนโลยีสารสนเทศ สถาบันเทคโนโลยีพระจอมเกล้าเจ้าคุณทหารลาดกระบัง</p>
            <%
                if(auth.isLogin()) {
            %>
            <p><a class="btn btn-success btn-lg" href="<%= RouteUtils.generateURL(request, "profile") %>" role="button">จัดการโปรไฟล์ »</a></p>
            <%
            } else {
            %>
            <p><a class="btn btn-primary btn-lg" href="<%= RouteUtils.generateURL(request, "login") %>" role="button">เข้าสู่ระบบศิษย์เก่า »</a></p>
            <%
                }
            %>
        </div>
    </div>

    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <h2>Slideshow</h2>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
                <h2>Heading</h2>
                <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui. </p>
                <p><a class="btn btn-default" href="#" role="button">View details »</a></p>
            </div>
            <div class="col-md-6">
                <h2>Heading</h2>
                <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui. </p>
                <p><a class="btn btn-default" href="#" role="button">View details »</a></p>
            </div>
        </div>

        <template:footerpage/>
    </div>
</template:page>