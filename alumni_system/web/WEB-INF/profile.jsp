<%@ include file="/WEB-INF/importlib.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<template:page>

    <template:head title="Alumni System - Profile">

        <template:style />

    </template:head>

    <template:body>

        <template:navbar />

        <div class="container">

            <div class="page-header">
                <c:if test="${Authorization:getCurrentUser().id == requestScope.user.id}">
                    <h1>โปรไฟล์ส่วนตัว</h1>
                </c:if>
                <c:if test="${Authorization:getCurrentUser().id != requestScope.user.id}">
                    <h1>โปรไฟล์สมาชิก</h1>
                </c:if>
            </div>

            <c:if test="${ResponseHelper:hasCodeInRequest()}">
                <c:if test="${ResponseHelper:getRequestCode() == ResponseHelper.NOT_ENOUGH_PERMISSION}">
                    <div class="alert alert-danger alert-dismissible" role="alert">
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        NOT_ENOUGH_PERMISSION
                    </div>
                </c:if>
                <c:if test="${ResponseHelper:getRequestCode() == ResponseHelper.FORM_INPUT_NOT_COMPLETE}">
                    <div class="alert alert-danger alert-dismissible" role="alert">
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        FORM_INPUT_NOT_COMPLETE
                    </div>
                </c:if>
                <c:if test="${ResponseHelper:getRequestCode() == ResponseHelper.PROFILE_UPDATED_COMPLETE}">
                    <div class="alert alert-success alert-dismissible" role="alert">
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        PROFILE_UPDATED_COMPLETE
                    </div>
                </c:if>
                <c:if test="${ResponseHelper:getRequestCode() == ResponseHelper.NO_USER_MODEL_FOUND}">
                    <div class="alert alert-warning alert-dismissible" role="alert">
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        NO_USER_MODEL_FOUND
                    </div>
                </c:if>
            </c:if>

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
                </div>
                <c:if test="${requestScope.user.type eq 'ALUMNI'}">
                    <div role="tabpanel" class="tab-pane" id="track">
                        <br/>
                        <template:alumniTrack user="${requestScope.user}" />
                    </div>
                </c:if>
            </div>

            <template:footer/>
        </div>

        <template:script />

    </template:body>

</template:page>
