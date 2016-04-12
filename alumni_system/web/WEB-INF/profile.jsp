<%@ page import="model.utility.ResponseCodeUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="template" uri="/WEB-INF/tlds/TemplateTag.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- FIXME FINISH THIS FORM !!! --%>

<template:page title="Alumni System - Profile">

    <div class="container">
        <h1>Your Profile</h1>

        <%
            if(ResponseCodeUtils.hasCodeInRequest(request)) {
                if(ResponseCodeUtils.getRequestCode(request) == ResponseCodeUtils.NOT_ENOUGH_PERMISSION) {
                    out.println("<div class=\"alert alert-danger\" role=\"alert\">NOT_ENOUGH_PERMISSION</div>");
                } else if(ResponseCodeUtils.getRequestCode(request) == ResponseCodeUtils.FORM_INPUT_NOT_COMPLETE) {
                    out.println("<div class=\"alert alert-danger\" role=\"alert\">FORM_INPUT_NOT_COMPLETE</div>");
                } else if(ResponseCodeUtils.getRequestCode(request) == ResponseCodeUtils.PROFILE_UPDATED_COMPLETE) {
                    out.println("<div class=\"alert alert-success\" role=\"alert\">PROFILE_UPDATED_COMPLETE</div>");
                } else if(ResponseCodeUtils.getRequestCode(request) == ResponseCodeUtils.NO_USER_MODEL_FOUND) {
                    out.println("<div class=\"alert alert-warning\" role=\"alert\">NO_USER_MODEL_FOUND</div>");
                }
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

        <hr />

        <footers>
            <p>คณะเทคโนโลยีสารสนเทศ สถาบันเทคโนโลยีพระจอมเกล้าเจ้าคุณทหารลาดกระบัง<br>
                เลขที่ 1 ซอยฉลองกรุง 1 แขวงลาดกระบัง เขตลาดกระบัง กรุงเทพมหานคร 10520<br>
                โทรศัพท์ +66 (0) 2723 4900 โทรสาร +66 (0) 2723 4910</p>
        </footers>
    </div>

</template:page>
