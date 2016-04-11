<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="template" uri="/WEB-INF/tlds/TemplateTag.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- FIXME FINISH THIS FORM !!! --%>

<template:page title="Alumni System - Profile">

    <div class="container">
        <h1>Your Profile</h1>

        <%-- TODO alert warning or error message --%>

        <ul class="nav nav-tabs" role="tablist">
            <li role="presentation" class="active"><a href="#profile" aria-controls="profile" role="tab" data-toggle="tab">Profile</a></li>
            <c:if test="${user.type eq 'ALUMNI'}">
                <li role="presentation"><a href="#track" aria-controls="track" role="tab" data-toggle="tab">Track</a></li>
            </c:if>
        </ul>

        <div class="tab-content">
            <div role="tabpanel" class="tab-pane active" id="profile">
                <c:if test="${user.type eq 'ALUMNI'}">
                    <template:alumniProfile user="${requestScope.user}" />
                </c:if>
                <c:if test="${user.type eq 'STAFF'}">
                    <template:staffProfile user="${requestScope.user}" />
                </c:if>
                <c:if test="${user.type eq 'TEACHER'}">
                    <template:teacherProfile user="${requestScope.user}" />
                </c:if>
                <%--<c:if test="${user.type eq 'DEVELOPER'}">--%>
                    <%-- TODO Developer Profile Page --%>
                <%--</c:if>--%>
            </div>
            <c:if test="${user.type eq 'ALUMNI'}">
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
