<%@ page import="com.alumnisystem.factory.WorkSectionFactory" %>
<%@ page import="java.util.ArrayList" %>
<%@ include file="/WEB-INF/importlib.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    ArrayList<Work.Section> sections = new WorkSectionFactory().all();
    request.setAttribute("sections", sections);
%>

<template:page adminPage="${true}">

    <template:head title="Alumni System - Admin - Add New Alumni">

        <template:style />

    </template:head>

    <template:body>

        <template:navbar />

        <div class="container-admin">

            <div class="page-header" style="border-bottom: 0px;">
                <h1>เพิ่มผู้ใช้ใหม่</h1>

                <c:if test="${ResponseHelper:hasCodeInRequest()}">
                    <c:if test="${ResponseHelper:getRequestCode() == ResponseHelper.FORM_INPUT_NOT_COMPLETE}">
                        <div class="alert alert-danger alert-dismissible" role="alert">
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            FORM_INPUT_NOT_COMPLETE
                        </div>
                    </c:if>
                    <c:if test="${ResponseHelper:getRequestCode() == ResponseHelper.FORM_PASSWORD_NOT_MATCH}">
                        <div class="alert alert-danger alert-dismissible" role="alert">
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            FORM_PASSWORD_NOT_MATCH
                        </div>
                    </c:if>
                    <c:if test="${ResponseHelper:getRequestCode() == ResponseHelper.ADD_NEW_TEACHER_COMPLETE}">
                        <div class="alert alert-success alert-dismissible" role="alert">
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            ADD_NEW_TEACHER_COMPLETE
                        </div>
                    </c:if>
                    <c:if test="${ResponseHelper:getRequestCode() == ResponseHelper.ADD_NEW_STAFF_COMPLETE}">
                        <div class="alert alert-success alert-dismissible" role="alert">
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            ADD_NEW_STAFF_COMPLETE
                        </div>
                    </c:if>
                </c:if>

                <ul class="nav nav-tabs" role="tablist">
                    <li role="presentation" class="active"><a href="#teacher" aria-controls="teacher" role="tab" data-toggle="tab">อาจารย์</a></li>
                    <li role="presentation"><a href="#staff" aria-controls="staff" role="tab" data-toggle="tab">พนักงาน/เจ้าหน้าที่</a></li>
                </ul>
            </div>

            <div class="container">

                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane active" id="teacher">
                        <form action="${RouteHelper:generateURL("admin/user/add")}" method="POST" id="teacher-form" class="form-horizontal">
                            <input type="hidden" name="usertype" value="TEACHER"/>
                            <div class="form-group">
                                <label for="teacher-form-username" class="col-md-3 control-label">Username*</label>
                                <div class="col-md-9">
                                    <input type="text" class="form-control" name="teacher-form-username" id="teacher-form-username" placeholder="Username" data-empty="false">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="teacher-form-password" class="col-md-3 control-label">Password*</label>
                                <div class="col-md-9">
                                    <input type="password" class="form-control" name="teacher-form-password" id="teacher-form-password" placeholder="Password" data-empty="false">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="teacher-form-copassword" class="col-md-3 control-label">Confirm Password*</label>
                                <div class="col-md-9">
                                    <input type="password" class="form-control" name="teacher-form-copassword" id="teacher-form-copassword" placeholder="Confirm Password" data-empty="false">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="teacher-form-pnameth" class="col-md-3 control-label">คำนำหน้าชื่อ (ภาษาไทย)*</label>
                                <div class="col-md-9">
                                    <input type="text" class="form-control" name="teacher-form-pnameth" id="teacher-form-pnameth" placeholder="Title (TH)" data-empty="false">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="teacher-form-fnameth" class="col-md-3 control-label">ชื่อ (ภาษาไทย)*</label>
                                <div class="col-md-9">
                                    <input type="text" class="form-control" name="teacher-form-fnameth" id="teacher-form-fnameth" placeholder="Name (TH)" data-empty="false">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="teacher-form-lnameth" class="col-md-3 control-label">นามสกุล (ภาษาไทย)*</label>
                                <div class="col-md-9">
                                    <input type="text" class="form-control" name="teacher-form-lnameth" id="teacher-form-lnameth" placeholder="Surname (TH)" data-empty="false">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="teacher-form-pnameen" class="col-md-3 control-label">คำนำหน้าชื่อ (ภาษาอังกฤษ)</label>
                                <div class="col-md-9">
                                    <input type="text" class="form-control" name="teacher-form-pnameen" id="teacher-form-pnameen" placeholder="Title (EN)">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="teacher-form-fnameen" class="col-md-3 control-label">ชื่อ (ภาษาอังกฤษ)</label>
                                <div class="col-md-9">
                                    <input type="text" class="form-control" name="teacher-form-fnameen" id="teacher-form-fnameen" placeholder="Name (EN)">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="teacher-form-lnameen" class="col-md-3 control-label">นามสกุล (ภาษาอังกฤษ)</label>
                                <div class="col-md-9">
                                    <input type="text" class="form-control" name="teacher-form-lnameen" id="teacher-form-lnameen" placeholder="Surname (EN)">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="teacher-form-email" class="col-md-3 control-label">อีเมลล์</label>
                                <div class="col-md-9">
                                    <input type="text" class="form-control" name="teacher-form-email" id="teacher-form-email" placeholder="Email">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="teacher-form-phone" class="col-md-3 control-label">เบอร์โทรศัพท์</label>
                                <div class="col-md-9">
                                    <input type="text" class="form-control" name="teacher-form-phone" id="teacher-form-phone" placeholder="Phone number">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="teacher-form-workstatus" class="col-md-3 control-label">สถานะ</label>
                                <div class="col-md-9">
                                    <select class="form-control" name="teacher-form-workstatus" id="teacher-form-workstatus">
                                        <option value="EMPLOYEE">พนักงาน</option>
                                        <option value="OFFICIAL">ข้าราชการ</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-3 col-sm-9">
                                    <button type="submit" id="teacher-form-btn" class="btn btn-primary">สร้างบัญชีผู้ใช้สำหรับอาจารย์</button>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div role="tabpanel" class="tab-pane" id="staff">
                        <form action="${RouteHelper:generateURL("admin/user/add")}" method="POST" id="staff-form" class="form-horizontal">
                            <input type="hidden" name="usertype" value="STAFF"/>
                            <div class="form-group">
                                <label for="staff-form-username" class="col-md-3 control-label">Username*</label>
                                <div class="col-md-9">
                                    <input type="text" class="form-control" name="staff-form-username" id="staff-form-username" placeholder="Username" data-empty="false">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="staff-form-password" class="col-md-3 control-label">Password*</label>
                                <div class="col-md-9">
                                    <input type="password" class="form-control" name="staff-form-password" id="staff-form-password" placeholder="Password" data-empty="false">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="staff-form-copassword" class="col-md-3 control-label">Confirm Password*</label>
                                <div class="col-md-9">
                                    <input type="password" class="form-control" name="staff-form-copassword" id="staff-form-copassword" placeholder="Confirm Password" data-empty="false">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="staff-form-pnameth" class="col-md-3 control-label">คำนำหน้าชื่อ (ภาษาไทย)*</label>
                                <div class="col-md-9">
                                    <input type="text" class="form-control" name="staff-form-pnameth" id="staff-form-pnameth" placeholder="Title (TH)" data-empty="false">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="staff-form-fnameth" class="col-md-3 control-label">ชื่อ (ภาษาไทย)*</label>
                                <div class="col-md-9">
                                    <input type="text" class="form-control" name="staff-form-fnameth" id="staff-form-fnameth" placeholder="Name (TH)" data-empty="false">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="staff-form-lnameth" class="col-md-3 control-label">นามสกุล (ภาษาไทย)*</label>
                                <div class="col-md-9">
                                    <input type="text" class="form-control" name="staff-form-lnameth" id="staff-form-lnameth" placeholder="Surname (TH)" data-empty="false">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="staff-form-pnameen" class="col-md-3 control-label">คำนำหน้าชื่อ (ภาษาอังกฤษ)</label>
                                <div class="col-md-9">
                                    <input type="text" class="form-control" name="staff-form-pnameen" id="staff-form-pnameen" placeholder="Title (EN)">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="staff-form-fnameen" class="col-md-3 control-label">ชื่อ (ภาษาอังกฤษ)</label>
                                <div class="col-md-9">
                                    <input type="text" class="form-control" name="staff-form-fnameen" id="staff-form-fnameen" placeholder="Name (EN)">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="staff-form-lnameen" class="col-md-3 control-label">นามสกุล (ภาษาอังกฤษ)</label>
                                <div class="col-md-9">
                                    <input type="text" class="form-control" name="staff-form-lnameen" id="staff-form-lnameen" placeholder="Surname (EN)">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="staff-form-email" class="col-md-3 control-label">อีเมลล์</label>
                                <div class="col-md-9">
                                    <input type="text" class="form-control" name="staff-form-email" id="staff-form-email" placeholder="Email">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="staff-form-phone" class="col-md-3 control-label">เบอร์โทรศัพท์</label>
                                <div class="col-md-9">
                                    <input type="text" class="form-control" name="staff-form-phone" id="staff-form-phone" placeholder="Phone number">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="staff-form-worksection" class="col-md-3 control-label">ฝ่าย</label>
                                <div class="col-md-9">
                                    <select class="form-control" name="staff-form-worksection" id="staff-form-worksection">
                                        <c:forEach var="section" items="${sections}">
                                            <option value="${section.id}">${section.name_th}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-3 col-sm-9">
                                    <button type="submit" id="staff-form-btn" class="btn btn-primary">สร้างบัญชีผู้ใช้สำหรับเจ้าหน้าที่/พนักงาน</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>

            </div>

        </div>

        <template:script />

    </template:body>

</template:page>