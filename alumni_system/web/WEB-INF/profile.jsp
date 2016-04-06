<%@ page import="model.User" %>
<%@ page import="model.Alumni" %>
<%@ page import="model.Teacher" %>
<%@ page import="model.Staff" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="template" uri="/WEB-INF/tlds/TemplateTag.tld" %>

<%
    User user = (User) request.getAttribute("user");
    Alumni alumni = null;
    Staff staff = null;
    Teacher teacher = null;

    switch (user.getType()) {
        case ALUMNI: alumni = (Alumni) request.getAttribute("user_detail"); break;
        case STAFF: staff = (Staff) request.getAttribute("user_detail"); break;
        case TEACHER: teacher = (Teacher) request.getAttribute("user_detail"); break;
    }
%>

<template:page title="Alumni System - Profile">
    <div class="container">
        <h1>Your Profile</h1>

        <ul class="nav nav-tabs" role="tablist">
            <li role="presentation" class="active"><a href="#profile" aria-controls="home" role="tab" data-toggle="tab">Profile</a></li>
            <li role="presentation"><a href="#track" aria-controls="profile" role="tab" data-toggle="tab">Track</a></li>
        </ul>

        <div class="tab-content">
            <div role="tabpanel" class="tab-pane active" id="profile">
                <h2>Account Details</h2>
                <form class="form-horizontal">
                    <div class="form-group">
                        <label for="AccountInputUsername" class="col-md-3 control-label">Username</label>
                        <div class="col-md-9">
                            <input type="text" class="form-control" id="AccountInputUsername" placeholder="Username" value="${user.username}" disabled>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="AccountInputType" class="col-md-3 control-label">Account Type</label>
                        <div class="col-md-9">
                            <input type="text" class="form-control" id="AccountInputType" placeholder="Type" value="${user.type}" disabled>
                        </div>
                    </div>
                </form>
            </div>
            <div role="tabpanel" class="tab-pane" id="track">
                <br/>
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th>ลำดับที่</th>
                            <th>แขนงวิชา</th>
                            <th>หลักสูตร</th>
                            <th>ปีที่เข้าศึกษา</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>1</td>
                            <td>ยังไม่ได้เลือก</td>
                            <td>เอบีซี</td>
                            <td>2558</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>

        <hr />

        <footers>
            <p>คณะเทคโนโลยีสารสนเทศ สถาบันเทคโนโลยีพระจอมเกล้าเจ้าคุณทหารลาดกระบัง<br>
                เลขที่ 1 ซอยฉลองกรุง 1 แขวงลาดกระบัง เขตลาดกระบัง กรุงเทพมหานคร 10520<br>
                โทรศัพท์ +66 (0) 2723 4900 โทรสาร +66 (0) 2723 4910</p>
        </footers>
    </div>
</template:page>
