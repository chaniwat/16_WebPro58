<%@ page import="model.utility.ResponseCodeUtils" %>
<%@ page import="model.auth.Authorization" %>
<%@ page import="model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="template" uri="/WEB-INF/tlds/TemplateTag.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    User currentUser = Authorization.getAuthInstance(session).getCurrentUser();
    User viewUser = (User)request.getAttribute("user");
%>

<template:page title="Alumni System - Profile">

    <div class="container">

        <div class="page-header">
            <% if(currentUser.getId() == viewUser.getId()) { %>
                <h1>โปรไฟล์ส่วนตัว</h1>
            <% } else { %>
                <h1>โปรไฟล์สมาชิก</h1>
            <% } %>
        </div>

        <%
            if(ResponseCodeUtils.hasCodeInRequest(request)) {
                if(ResponseCodeUtils.getRequestCode(request) == ResponseCodeUtils.NOT_ENOUGH_PERMISSION) { %>
                    <div class="alert alert-danger alert-dismissible" role="alert">
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        NOT_ENOUGH_PERMISSION
                    </div>
                <% } else if(ResponseCodeUtils.getRequestCode(request) == ResponseCodeUtils.FORM_INPUT_NOT_COMPLETE) { %>
                    <div class="alert alert-danger alert-dismissible" role="alert">
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        FORM_INPUT_NOT_COMPLETE
                    </div>
                <% } else if(ResponseCodeUtils.getRequestCode(request) == ResponseCodeUtils.PROFILE_UPDATED_COMPLETE) { %>
                    <div class="alert alert-success alert-dismissible" role="alert">
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        PROFILE_UPDATED_COMPLETE
                    </div>
                <% } else if(ResponseCodeUtils.getRequestCode(request) == ResponseCodeUtils.NO_USER_MODEL_FOUND) { %>
                    <div class="alert alert-warning alert-dismissible" role="alert">
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        NO_USER_MODEL_FOUND
                    </div>
                <% }
            }
        %>

        <ul class="nav nav-tabs" role="tablist">
            <li role="presentation" class="active"><a href="#profile" aria-controls="profile" role="tab" data-toggle="tab">Profile</a></li>
            <c:if test="${requestScope.user.type eq 'ALUMNI'}">
                <li role="presentation"><a href="#track" aria-controls="track" role="tab" data-toggle="tab">Track</a></li>
            </c:if>
        </ul>

        <div class="tab-content">
            <div role="tabpanel" class="tab-pane active" id="profile">
                <c:if test="${requestScope.user.type eq 'ALUMNI'}">
                    <template:alumniProfile user="${requestScope.user}" />
                </c:if>
                <c:if test="${requestScope.user.type eq 'STAFF'}">
                    <template:staffProfile user="${requestScope.user}" />
                </c:if>
                <c:if test="${requestScope.user.type eq 'TEACHER'}">
                    <template:teacherProfile user="${requestScope.user}" />
                </c:if>
                <%--<c:if test="${user.type eq 'DEVELOPER'}">--%>
                    <%-- TODO Developer Profile Page --%>
                <%--</c:if>--%>
            </div>
            <c:if test="${requestScope.user.type eq 'ALUMNI'}">
                <div role="tabpanel" class="tab-pane" id="track">
                    <br/>
                    <template:alumniTrack user="${requestScope.user}" />
                </div>
            </c:if>
        </div>

        <template:footerpage/>
    </div>

</template:page>
