<%@ tag import="com.alumnisystem.factory.TeacherFactory" %>
<%@ tag import="com.alumnisystem.model.Teacher" %>
<%@ tag import="com.alumnisystem.model.User" %>
<%@ include file="/WEB-INF/tags/importlib.tag" %>
<%@tag pageEncoding="UTF-8" body-content="empty" %>

<%@attribute name="user" required="true" type="com.alumnisystem.model.User" rtexprvalue="true" %>

<%
    TeacherFactory teacherFactory = new TeacherFactory();

    Teacher teacher = teacherFactory.findByUserId(user.getId());
    User currentUser = Authorization.getCurrentUser();

    boolean editable = (currentUser.isAdmin() ||
            (currentUser.getType() == User.Type.TEACHER && teacher.getTeacher_id() == teacherFactory.findByUserId(currentUser.getId()).getTeacher_id()));

    request.setAttribute("teacher", teacher);
    request.setAttribute("editable", editable);
%>

<h2>ประวัติส่วนตัว</h2>

<hr />

<form action="${RouteHelper:generateURL("profile/edit")}" method="POST" id="teacher-form" class="form-horizontal">
    <input type="hidden" id="profilepage-usertype" name="profilepage-usertype" value="${user.type}"/>
    <div class="form-group">
        <label for="teacher-form-id" class="col-md-3 control-label">รหัส</label>
        <div class="col-md-9">
            <input type="text" class="form-control" name="teacher-form-id" id="teacher-form-id" placeholder="ID" value="${teacher.teacher_id}" data-lock="true">
        </div>
    </div>
    <div class="form-group">
        <label for="teacher-form-pnameth" class="col-md-3 control-label">คำนำหน้าชื่อ (ภาษาไทย)*</label>
        <div class="col-md-9">
            <input type="text" class="form-control" name="teacher-form-pnameth" id="teacher-form-pnameth" placeholder="Title (TH)" value="${teacher.pname_th}" data-empty="false">
        </div>
    </div>
    <div class="form-group">
        <label for="teacher-form-fnameth" class="col-md-3 control-label">ชื่อ (ภาษาไทย)*</label>
        <div class="col-md-9">
            <input type="text" class="form-control" name="teacher-form-fnameth" id="teacher-form-fnameth" placeholder="Name (TH)" value="${teacher.fname_th}" data-empty="false">
        </div>
    </div>
    <div class="form-group">
        <label for="teacher-form-lnameth" class="col-md-3 control-label">นามสกุล (ภาษาไทย)*</label>
        <div class="col-md-9">
            <input type="text" class="form-control" name="teacher-form-lnameth" id="teacher-form-lnameth" placeholder="Surname (TH)" value="${teacher.lname_th}" data-empty="false">
        </div>
    </div>
    <div class="form-group">
        <label for="teacher-form-pnameen" class="col-md-3 control-label">คำนำหน้าชื่อ (ภาษาอังกฤษ)</label>
        <div class="col-md-9">
            <input type="text" class="form-control" name="teacher-form-pnameen" id="teacher-form-pnameen" placeholder="Title (EN)" value="${teacher.pname_en}">
        </div>
    </div>
    <div class="form-group">
        <label for="teacher-form-fnameen" class="col-md-3 control-label">ชื่อ (ภาษาอังกฤษ)</label>
        <div class="col-md-9">
            <input type="text" class="form-control" name="teacher-form-fnameen" id="teacher-form-fnameen" placeholder="Name (EN)" value="${teacher.fname_en}">
        </div>
    </div>
    <div class="form-group">
        <label for="teacher-form-lnameen" class="col-md-3 control-label">นามสกุล (ภาษาอังกฤษ)</label>
        <div class="col-md-9">
            <input type="text" class="form-control" name="teacher-form-lnameen" id="teacher-form-lnameen" placeholder="Surname (EN)" value="${teacher.lname_en}}">
        </div>
    </div>
    <div class="form-group">
        <label for="teacher-form-email" class="col-md-3 control-label">อีเมลล์</label>
        <div class="col-md-9">
            <input type="text" class="form-control" name="teacher-form-email" id="teacher-form-email" placeholder="Email" value="${teacher.email}">
        </div>
    </div>
    <div class="form-group">
        <label for="teacher-form-phone" class="col-md-3 control-label">เบอร์โทรศัพท์</label>
        <div class="col-md-9">
            <input type="text" class="form-control" name="teacher-form-phone" id="teacher-form-phone" placeholder="Phone number" value="${teacher.phone}">
        </div>
    </div>
    <div class="form-group">
        <label for="teacher-form-workstatus" class="col-md-3 control-label">สถานะ</label>
        <div class="col-md-9">
            <select class="form-control" name="teacher-form-workstatus" id="teacher-form-workstatus" data-default="${teacher.work_status}">
                <option value="EMPLOYEE">พนักงาน</option>
                <option value="OFFICIAL">ข้าราชการ</option>
            </select>
        </div>
    </div>
    <c:if test="${editable}">
        <div class="form-group">
            <div class="col-sm-offset-3 col-sm-9">
                <button type="button" id="teacher-form-btn" class="btn btn-primary">กดเพื่อแก้ไขข้อมูล</button>
            </div>
        </div>
    </c:if>
</form>