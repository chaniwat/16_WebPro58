<%@ page import="model.utility.RouteUtils" %>
<%@ page import="model.auth.Authorization" %>
<%@ page import="model.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.utility.ResponseCodeUtils" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="template" uri="/WEB-INF/tlds/TemplateTag.tld" %>

<%
    User user = (User) request.getAttribute("user");
    Alumni alumni = (Alumni) request.getAttribute("alumni");
    Staff staff = (Staff) request.getAttribute("staff");
    Teacher teacher = (Teacher) request.getAttribute("teacher");

    User currentuser = Authorization.getAuthInstance(session).getCurrentUser();

    boolean isAlumniProfileEditable = !((currentuser.getType() == User.UserType.TEACHER) ||
            (currentuser.getType() == User.UserType.ALUMNI && Alumni.getAlumniByUserId(currentuser.getId()).getStudent_id() != alumni.getStudent_id()));
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
                <h2>ข้อมูลบัญชี</h2>
                <form id="user-form" class="form-horizontal">
                    <div class="form-group">
                        <label for="user-form-username" class="col-md-3 control-label">Username</label>
                        <div class="col-md-9">
                            <input type="text" class="form-control" id="user-form-username" placeholder="Username" value="${user.username}" readonly>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="user-form-usertype" class="col-md-3 control-label">Account Type</label>
                        <div class="col-md-9">
                            <input type="text" class="form-control" id="user-form-usertype" placeholder="Type" value="${user.type}" readonly>
                        </div>
                    </div>
                </form>
                <hr/>
                <h2>ประวัติส่วนตัว</h2>
                <form action="<%= RouteUtils.generateURL(request, "profile/edit") %>" method="POST" id="alumni-form" class="form-horizontal">
                    <input type="hidden" id="profilepage-usertype" name="usertype" value="${user.type}"/>
                    <div class="form-group">
                        <label for="alumni-form-stuid" class="col-md-3 control-label">รหัสนักศักษา</label>
                        <div class="col-md-9">
                            <input type="text" class="form-control" name="alumni-form-stuid" id="alumni-form-stuid" placeholder="Student ID" value="${alumni.student_id}" data-lock="true">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="alumni-form-pnameth" class="col-md-3 control-label">คำนำหน้าชื่อ (ภาษาไทย)</label>
                        <div class="col-md-9">
                            <input type="text" class="form-control" name="alumni-form-pnameth" id="alumni-form-pnameth" placeholder="Title (TH)" value="${alumni.pname_th}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="alumni-form-fnameth" class="col-md-3 control-label">ชื่อ (ภาษาไทย)</label>
                        <div class="col-md-9">
                            <input type="text" class="form-control" name="alumni-form-fnameth" id="alumni-form-fnameth" placeholder="Name (TH)" value="${alumni.fname_th}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="alumni-form-lnameth" class="col-md-3 control-label">นามสกุล (ภาษาไทย)</label>
                        <div class="col-md-9">
                            <input type="text" class="form-control" name="alumni-form-lnameth" id="alumni-form-lnameth" placeholder="Surname (TH)" value="${alumni.lname_th}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="alumni-form-pnameen" class="col-md-3 control-label">คำนำหน้าชื่อ (ภาษาอังกฤษ)</label>
                        <div class="col-md-9">
                            <input type="text" class="form-control" name="alumni-form-pnameen" id="alumni-form-pnameen" placeholder="Title (EN)" value="${alumni.pname_en}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="alumni-form-fnameen" class="col-md-3 control-label">ชื่อ (ภาษาอังกฤษ)</label>
                        <div class="col-md-9">
                            <input type="text" class="form-control" name="alumni-form-fnameen" id="alumni-form-fnameen" placeholder="Name (EN)" value="${alumni.fname_en}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="alumni-form-lnameen" class="col-md-3 control-label">นามสกุล (ภาษาอังกฤษ)</label>
                        <div class="col-md-9">
                            <input type="text" class="form-control" name="alumni-form-lnameen" id="alumni-form-lnameen" placeholder="Surname (EN)" value="${alumni.lname_en}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="alumni-form-nickname" class="col-md-3 control-label">ชื่อเล่น</label>
                        <div class="col-md-9">
                            <input type="text" class="form-control" name="alumni-form-nickname" id="alumni-form-nickname" placeholder="Nickname" value="${alumni.nickname}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="alumni-form-birthdate" class="col-md-3 control-label">วันเกิด</label>
                        <div class="col-md-9" id="alumni-form-birthdate">
                            <%
                                int day = 0, month = 0, year = 0;
                                if(alumni.getBirthdate() != null) {
                                    day = Integer.parseInt(new SimpleDateFormat("dd").format(alumni.getBirthdate()));
                                    month = Integer.parseInt(new SimpleDateFormat("MM").format(alumni.getBirthdate()));
                                    year = Integer.parseInt(new SimpleDateFormat("yyyy").format(alumni.getBirthdate()));
                                }
                            %>
                            <div class="row">
                                <div class="col-md-3">
                                    <select class="form-control" name="alumni-form-birthdate-year" id="alumni-form-birthdate-year" data-default="<%= year == 0 ? "null" : year %>">
                                        <option value="null">Year</option>
                                    </select>
                                </div>
                                <div class="col-md-3">
                                    <select class="form-control" name="alumni-form-birthdate-month" id="alumni-form-birthdate-month" data-default="<%= month == 0 ? "null" : month %>">
                                        <option value="null">Month</option>
                                    </select>
                                </div>
                                <div class="col-md-3">
                                    <select class="form-control" name="alumni-form-birthdate-day" id="alumni-form-birthdate-day" data-default="<%= day == 0 ? "null" : day %>">
                                        <option value="null">Day</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="alumni-form-email" class="col-md-3 control-label">ที่อยู่</label>
                        <div class="col-md-9">
                            <textarea class="form-control" name="alumni-form-address" id="alumni-form-address" placeholder="Address">${alumni.address.address}</textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="alumni-form-email" class="col-md-3 control-label">ตำบล/แขวง</label>
                        <div class="col-md-9">
                            <input type="text" class="form-control" name="alumni-form-district" id="alumni-form-district" placeholder="District" value="${alumni.address.district}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="alumni-form-email" class="col-md-3 control-label">อำเภอ/เขต</label>
                        <div class="col-md-9">
                            <input type="text" class="form-control" name="alumni-form-amphure" id="alumni-form-amphure" placeholder="Amphure" value="${alumni.address.amphure}">
                        </div>
                    </div>
                    <%
                        ArrayList<Address.Province> provinces = Address.Province.getAllProvince();
                    %>
                    <div class="form-group">
                        <label for="alumni-form-email" class="col-md-3 control-label">จังหวัด</label>
                        <div class="col-md-9">
                            <%
                                if(alumni.getAddress().getProvince() == null) {
                            %>
                                <select class="form-control" name="alumni-form-province" id="alumni-form-province">
                            <%
                                } else {
                            %>
                                <select class="form-control" name="alumni-form-province" id="alumni-form-province" data-default="${alumni.address.province.province_id}">
                            <%
                                }
                            %>
                                    <option value="null">Province</option>
                                    <%
                                        for(Address.Province province : provinces) {
                                    %>
                                    <option value="<%= province.getProvince_id() %>"><%= province.getName_th() %></option>
                                    <%
                                        }
                                    %>
                                </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="alumni-form-email" class="col-md-3 control-label">รหัสไปรษณีย์</label>
                        <div class="col-md-9">
                            <input type="text" class="form-control" name="alumni-form-zipcode" id="alumni-form-zipcode" placeholder="Zipcode" value="${alumni.address.zipcode}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="alumni-form-email" class="col-md-3 control-label">อีเมลล์</label>
                        <div class="col-md-9">
                            <input type="text" class="form-control" name="alumni-form-email" id="alumni-form-email" placeholder="Email" value="${alumni.email}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="alumni-form-phone" class="col-md-3 control-label">เบอร์โทรศัพท์</label>
                        <div class="col-md-9">
                            <input type="text" class="form-control" name="alumni-form-phone" id="alumni-form-phone" placeholder="Phone number" value="${alumni.phone}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="alumni-form-occupation" class="col-md-3 control-label">อาชีพ</label>
                        <div class="col-md-9">
                            <input type="text" class="form-control" name="alumni-form-occupation" id="alumni-form-occupation" placeholder="Occupation" value="${alumni.occupation}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="alumni-form-workname" class="col-md-3 control-label">ชื่อบริษัท</label>
                        <div class="col-md-9">
                            <input type="text" class="form-control" name="alumni-form-workname" id="alumni-form-workname" placeholder="Company name" value="${alumni.work_name}"b>
                        </div>
                    </div>
                    <%
                        if(isAlumniProfileEditable) {
                    %>
                    <div class="form-group">
                        <div class="col-sm-offset-3 col-sm-9">
                            <button type="button" id="alumni-form-btn" class="btn btn-primary">แก้ไข</button>
                        </div>
                    </div>
                    <%
                        }
                    %>
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
                        <th>ปีที่เข้าการศึกษา</th>
                        <th>ปีที่จบการศึกษา</th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                        int i = 1;
                        for(Track track : alumni.getTracks()) {
                    %>
                    <tr>
                        <td><%= i++ %></td>
                        <td><%= track.getName_th() %></td>
                        <td><%= track.getCurriculum().getName_th() %></td>
                        <td><%= track.getStarteduyear() %></td>
                        <td><%= track.getEndeduyear() %></td>
                    </tr>
                    <%
                        }
                    %>
                    </tbody>
                </table>
                <%
                    if(isAlumniProfileEditable) {
                %>
                <%-- FIXME MODAL OR FORM TO ADD NEW TRACK (NOT LINK LIKE THIS OKAY?) --%>
                <a href="<%= RouteUtils.generateURL(request, "profile/track/add") %>" class="btn btn-primary">เพิ่มแทร๊กใหม่</a>
                <%
                    }
                %>
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
