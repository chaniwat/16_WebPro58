<%@ page import="com.alumnisystem.factory.JobTypeFactory" %>
<%@ include file="/WEB-INF/importlib.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    request.setAttribute("jobTypes", new JobTypeFactory().all());
%>

<template:page adminPage="${true}">

    <template:head title="Alumni System - Admin - Add New Alumni">

        <template:style />

    </template:head>

    <template:body>

        <template:navbar />

        <div class="container-admin">

            <div class="page-header">
                <h1>เพิ่มศิษย์เก่า</h1>
            </div>

            <div class="container">

                <form action="${RouteHelper:generateURL("admin/alumni/add")}" method="POST" id="alumni-form" class="form-horizontal">
                    <input type="hidden" name="mode" value="CREATE">
                    <div class="form-group">
                        <label for="alumni-form-pnameth" class="col-md-3 control-label">คำนำหน้าชื่อ (ภาษาไทย)*</label>
                        <div class="col-md-9">
                            <input type="text" class="form-control" name="alumni-form-pnameth" id="alumni-form-pnameth" placeholder="Title (TH)" value="${sessionScope['admin.alumni.new'].pname_th}" readonly>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="alumni-form-fnameth" class="col-md-3 control-label">ชื่อ (ภาษาไทย)*</label>
                        <div class="col-md-9">
                            <input type="text" class="form-control" name="alumni-form-fnameth" id="alumni-form-fnameth" placeholder="Name (TH)" value="${sessionScope['admin.alumni.new'].fname_th}" readonly>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="alumni-form-lnameth" class="col-md-3 control-label">นามสกุล (ภาษาไทย)*</label>
                        <div class="col-md-9">
                            <input type="text" class="form-control" name="alumni-form-lnameth" id="alumni-form-lnameth" placeholder="Surname (TH)" value="${sessionScope['admin.alumni.new'].lname_th}" readonly>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="alumni-form-pnameen" class="col-md-3 control-label">คำนำหน้าชื่อ (ภาษาอังกฤษ)</label>
                        <div class="col-md-9">
                            <input type="text" class="form-control" name="alumni-form-pnameen" id="alumni-form-pnameen" placeholder="Title (EN)">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="alumni-form-fnameen" class="col-md-3 control-label">ชื่อ (ภาษาอังกฤษ)</label>
                        <div class="col-md-9">
                            <input type="text" class="form-control" name="alumni-form-fnameen" id="alumni-form-fnameen" placeholder="Name (EN)">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="alumni-form-lnameen" class="col-md-3 control-label">นามสกุล (ภาษาอังกฤษ)</label>
                        <div class="col-md-9">
                            <input type="text" class="form-control" name="alumni-form-lnameen" id="alumni-form-lnameen" placeholder="Surname (EN)">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="alumni-form-nickname" class="col-md-3 control-label">ชื่อเล่น</label>
                        <div class="col-md-9">
                            <input type="text" class="form-control" name="alumni-form-nickname" id="alumni-form-nickname" placeholder="Nickname">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="alumni-form-birthdate" class="col-md-3 control-label">วันเกิด</label>
                        <div class="col-md-9" id="alumni-form-birthdate">
                            <div class="row">
                                <div class="col-md-3">
                                    <select class="form-control" name="alumni-form-birthdate-year" id="alumni-form-birthdate-year">
                                        <option value="null">Year</option>
                                    </select>
                                </div>
                                <div class="col-md-3">
                                    <select class="form-control" name="alumni-form-birthdate-month" id="alumni-form-birthdate-month">
                                        <option value="null">Month</option>
                                    </select>
                                </div>
                                <div class="col-md-3">
                                    <select class="form-control" name="alumni-form-birthdate-day" id="alumni-form-birthdate-day">
                                        <option value="null">Day</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="alumni-form-address" class="col-md-3 control-label">ที่อยู่</label>
                        <div class="col-md-9">
                            <textarea class="form-control" name="alumni-form-address" id="alumni-form-address" placeholder="Address"></textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="alumni-form-district" class="col-md-3 control-label">ตำบล/แขวง</label>
                        <div class="col-md-9">
                            <input type="text" class="form-control" name="alumni-form-district" id="alumni-form-district" placeholder="District">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="alumni-form-amphure" class="col-md-3 control-label">อำเภอ/เขต</label>
                        <div class="col-md-9">
                            <input type="text" class="form-control" name="alumni-form-amphure" id="alumni-form-amphure" placeholder="Amphure">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="alumni-form-province" class="col-md-3 control-label">จังหวัด</label>
                        <div class="col-md-9">
                            <input type="text" class="form-control" name="alumni-form-province" id="alumni-form-province" placeholder="Province">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="alumni-form-zipcode" class="col-md-3 control-label">รหัสไปรษณีย์</label>
                        <div class="col-md-9">
                            <input type="text" class="form-control" name="alumni-form-zipcode" id="alumni-form-zipcode" placeholder="Zipcode">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="alumni-form-email" class="col-md-3 control-label">อีเมลล์</label>
                        <div class="col-md-9">
                            <input type="text" class="form-control" name="alumni-form-email" id="alumni-form-email" placeholder="Email">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="alumni-form-phone" class="col-md-3 control-label">เบอร์โทรศัพท์</label>
                        <div class="col-md-9">
                            <input type="text" class="form-control" name="alumni-form-phone" id="alumni-form-phone" placeholder="Phone number">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="alumni-form-jobtype" class="col-md-3 control-label">ประเภทสายงาน</label>
                        <div class="col-md-9">
                            <select class="form-control" name="alumni-form-jobtype" id="alumni-form-jobtype">
                                <option value="null">โปรดเลือก</option>
                                <c:forEach var="jobtype" items="${jobTypes}">
                                    <option value="${jobtype.id}">${jobtype.name_th}</option>
                                </c:forEach>
                                <option value="0">อื่นๆ</option>
                            </select><br />
                            <input type="text" class="form-control" name="alumni-form-jobtypeother" id="alumni-form-jobtypeother" placeholder="Job Type - อื่นๆ" data-lock="true">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="alumni-form-jobname" class="col-md-3 control-label">ตำแหน่งงาน</label>
                        <div class="col-md-9">
                            <select class="form-control" name="alumni-form-jobname" id="alumni-form-jobname">
                                <option value="null">โปรดเลือก</option>
                                <option value="0">อื่นๆ</option>
                            </select><br />
                            <input type="text" class="form-control" name="alumni-form-jobnameother" id="alumni-form-jobnameother" placeholder="Position - อื่นๆ" data-lock="true">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="alumni-form-workname" class="col-md-3 control-label">ชื่อบริษัทที่ทำงาน</label>
                        <div class="col-md-9">
                            <input type="text" class="form-control" name="alumni-form-workname" id="alumni-form-workname" placeholder="Company name">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-3 col-sm-9">
                            <button type="submit" id="alumni-form-btn" class="btn btn-primary">ขั้นตอนต่อไป »</button>
                        </div>
                    </div>
                </form>

            </div>

        </div>

        <template:script />

    </template:body>

</template:page>
