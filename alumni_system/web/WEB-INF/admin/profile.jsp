<%@ include file="/WEB-INF/importlib.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<template:page adminPage="${true}">

    <template:head title="Alumni System - Admin - Profile">

        <template:style />

    </template:head>

    <template:body>

        <template:navbar />

        <div class="container-admin">

            <div class="page-header" style="border-bottom: 0px;">
                <c:if test="${requestScope.user.type eq 'ALUMNI'}">
                    <h1>โปรไฟล์ศิษย์เก่า</h1>
                </c:if>
                <c:if test="${requestScope.user.type eq 'STAFF'}">
                    <h1>โปรไฟล์พนักงาน</h1>
                </c:if>
                <c:if test="${requestScope.user.type eq 'TEACHER'}">
                    <h1>โปรไฟล์อาจารย์</h1>
                </c:if>

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
                    <c:if test="${ResponseHelper:getRequestCode() == ResponseHelper.MAKE_ADMIN_COMPLETE}">
                        <div class="alert alert-success alert-dismissible" role="alert">
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            MAKE_ADMIN_COMPLETE
                        </div>
                    </c:if>
                    <c:if test="${ResponseHelper:getRequestCode() == ResponseHelper.REMOVE_ADMIN_COMPLETE}">
                        <div class="alert alert-success alert-dismissible" role="alert">
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            REMOVE_ADMIN_COMPLETE
                        </div>
                    </c:if>
                    <c:if test="${ResponseHelper:getRequestCode() == ResponseHelper.REMOVE_USER_COMPLETE}">
                        <div class="alert alert-success alert-dismissible" role="alert">
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            REMOVE_USER_COMPLETE
                        </div>
                    </c:if>
                </c:if>

                <ul class="nav nav-tabs" role="tablist">
                    <li role="presentation" class="active"><a href="#profile" aria-controls="profile" role="tab" data-toggle="tab">Profile</a></li>
                    <c:if test="${requestScope.user.type eq 'ALUMNI'}">
                        <li role="presentation"><a href="#track" aria-controls="track" role="tab" data-toggle="tab">Track</a></li>
                    </c:if>
                    <li role="presentation"><a href="#setting" aria-controls="setting" role="tab" data-toggle="tab">Setting</a></li>
                </ul>
            </div>

            <div class="container">
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
                            <template:alumniTrack user="${requestScope.user}" />
                        </div>
                    </c:if>
                    <div role="tabpanel" class="tab-pane" id="setting">
                        <h2>สถานะยูสเซอร์ : ${requestScope.user.admin ? 'ADMIN' : 'USER'}</h2><br />
                        <c:if test="${!requestScope.user.admin}">
                            <button type="button" class="btn btn-warning btn-lg" data-toggle="modal" data-target="#adminModal">
                                เปลี่ยนเป็นผู้ดูแลระบบ
                            </button>
                        </c:if>
                        <c:if test="${requestScope.user.admin}">
                            <button type="button" class="btn btn-warning btn-lg" data-toggle="modal" data-target="#adminModal">
                                ยกเลิกการเป็นผู้ดูแลระบบ
                            </button>
                        </c:if>
                        <button type="button" class="btn btn-danger btn-lg" data-toggle="modal" data-target="#removeModal">
                            ลบผู้ใช้นี้
                        </button>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal fade" id="adminModal" tabindex="-1" role="dialog" aria-labelledby="adminModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="adminModalLabel">Alert</h4>
                    </div>
                    <div class="modal-body">
                        <c:if test="${!requestScope.user.admin}">
                            <h2>ยืนยันการเปลี่ยนผู้ใช้รหัส ${requestScope.user.id} เป็นผู้ดูแลระบบ</h2>
                            ชื่อ: ${requestScope.user.pname_th}${requestScope.user.fname_th} ${requestScope.user.lname_th}<br />
                            ประเภทผู้ใช้: ${requestScope.user.type}
                            สถานะยูสเซอร์ : USER
                        </c:if>
                        <c:if test="${requestScope.user.admin}">
                            <h2>ยืนยันการยกเลิกผู้ใช้รหัส ${requestScope.user.id} เป็นผู้ดูแลระบบ</h2>
                            ชื่อ: ${requestScope.user.pname_th}${requestScope.user.fname_th} ${requestScope.user.lname_th}<br />
                            ประเภทผู้ใช้: ${requestScope.user.type}
                            สถานะยูสเซอร์ : ADMIN
                        </c:if>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger" data-dismiss="modal">ยกเลิก</button>
                        <c:if test="${!requestScope.user.admin}">
                            <a href="${RouteHelper:generateURL("admin/profile/status")}/${requestScope.user.id}?to=ADMIN" class="btn btn-success">ยืนยัน</a>
                        </c:if>
                        <c:if test="${requestScope.user.admin}">
                            <a href="${RouteHelper:generateURL("admin/profile/status")}/${requestScope.user.id}?to=USER" class="btn btn-success">ยืนยัน</a>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal fade" id="removeModal" tabindex="-1" role="dialog" aria-labelledby="removeModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="removeModalLabel">Alert</h4>
                    </div>
                    <div class="modal-body">
                        <h2>ยืนยันการลบผู้ใช้รหัส ${requestScope.user.id} เป็นผู้ดูแลระบบ</h2>
                        ชื่อ: ${requestScope.user.pname_th}${requestScope.user.fname_th} ${requestScope.user.lname_th}<br />
                        ประเภทผู้ใช้: ${requestScope.user.type}
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger" data-dismiss="modal">ยกเลิก</button>
                        <a href="${RouteHelper:generateURL("admin/user/remove")}/${requestScope.user.id}" class="btn btn-success">ยืนยัน</a>
                    </div>
                </div>
            </div>
        </div>

        <template:script/>

    </template:body>

</template:page>
