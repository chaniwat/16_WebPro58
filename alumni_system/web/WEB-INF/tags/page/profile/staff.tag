<%@ tag import="com.alumnisystem.factory.StaffFactory" %>
<%@ tag import="com.alumnisystem.model.Staff" %>
<%@ tag import="com.alumnisystem.model.User" %>
<%@ tag import="com.alumnisystem.model.Work" %>
<%@ tag import="java.util.ArrayList" %>
<%@ tag import="com.alumnisystem.factory.WorkSectionFactory" %>
<%@ include file="/WEB-INF/tags/importlib.tag" %>
<%@tag pageEncoding="UTF-8" body-content="empty" %>

<%@attribute name="user" required="true" type="com.alumnisystem.model.User" rtexprvalue="true" %>

<%
    StaffFactory staffFactory = new StaffFactory();

    Staff staff = staffFactory.findByUserId(user.getId());
    User currentUser = Authorization.getCurrentUser();

    boolean editable = (currentUser.isAdmin() ||
            (currentUser.getType() == User.Type.STAFF && staff.getStaff_id() == staffFactory.findByUserId(currentUser.getId()).getStaff_id()));

    ArrayList<Work.Section> sections = new WorkSectionFactory().all();

    request.setAttribute("staff", staff);
    request.setAttribute("editable", editable);
    request.setAttribute("sections", sections);
%>

<h2>ประวัติส่วนตัว</h2>

<hr />

<form action="${RouteHelper:generateURL("profile/edit")}" method="POST" id="staff-form" class="form-horizontal">
    <input type="hidden" id="profilepage-usertype" name="profilepage-usertype" value="${user.type}"/>
    <div class="form-group">
        <label for="staff-form-id" class="col-md-3 control-label">รหัส</label>
        <div class="col-md-9">
            <input type="text" class="form-control" name="staff-form-id" id="staff-form-id" placeholder="ID" value="${staff.staff_id}" data-lock="true">
        </div>
    </div>
    <div class="form-group">
        <label for="staff-form-pnameth" class="col-md-3 control-label">คำนำหน้าชื่อ (ภาษาไทย)*</label>
        <div class="col-md-9">
            <input type="text" class="form-control" name="staff-form-pnameth" id="staff-form-pnameth" placeholder="Title (TH)" value="${staff.pname_th}" data-empty="false">
        </div>
    </div>
    <div class="form-group">
        <label for="staff-form-fnameth" class="col-md-3 control-label">ชื่อ (ภาษาไทย)*</label>
        <div class="col-md-9">
            <input type="text" class="form-control" name="staff-form-fnameth" id="staff-form-fnameth" placeholder="Name (TH)" value="${staff.fname_th}" data-empty="false">
        </div>
    </div>
    <div class="form-group">
        <label for="staff-form-lnameth" class="col-md-3 control-label">นามสกุล (ภาษาไทย)*</label>
        <div class="col-md-9">
            <input type="text" class="form-control" name="staff-form-lnameth" id="staff-form-lnameth" placeholder="Surname (TH)" value="${staff.lname_th}" data-empty="false">
        </div>
    </div>
    <div class="form-group">
        <label for="staff-form-pnameen" class="col-md-3 control-label">คำนำหน้าชื่อ (ภาษาอังกฤษ)</label>
        <div class="col-md-9">
            <input type="text" class="form-control" name="staff-form-pnameen" id="staff-form-pnameen" placeholder="Title (EN)" value="${staff.pname_en}">
        </div>
    </div>
    <div class="form-group">
        <label for="staff-form-fnameen" class="col-md-3 control-label">ชื่อ (ภาษาอังกฤษ)</label>
        <div class="col-md-9">
            <input type="text" class="form-control" name="staff-form-fnameen" id="staff-form-fnameen" placeholder="Name (EN)" value="${staff.fname_en}">
        </div>
    </div>
    <div class="form-group">
        <label for="staff-form-lnameen" class="col-md-3 control-label">นามสกุล (ภาษาอังกฤษ)</label>
        <div class="col-md-9">
            <input type="text" class="form-control" name="staff-form-lnameen" id="staff-form-lnameen" placeholder="Surname (EN)" value="${staff.lname_en}">
        </div>
    </div>
    <div class="form-group">
        <label for="staff-form-email" class="col-md-3 control-label">อีเมลล์</label>
        <div class="col-md-9">
            <input type="text" class="form-control" name="staff-form-email" id="staff-form-email" placeholder="Email" value="${staff.email}">
        </div>
    </div>
    <div class="form-group">
        <label for="staff-form-phone" class="col-md-3 control-label">เบอร์โทรศัพท์</label>
        <div class="col-md-9">
            <input type="text" class="form-control" name="staff-form-phone" id="staff-form-phone" placeholder="Phone number" value="${staff.phone}">
        </div>
    </div>
    <div class="form-group">
        <label for="staff-form-worksection" class="col-md-3 control-label">ฝ่าย</label>
        <div class="col-md-9">
            <select class="form-control" name="staff-form-worksection" id="staff-form-worksection" data-default="${staff.section.id}">
                <c:forEach var="section" items="${sections}">
                <option value="${section.id}">${section.name_th}</option>
                </c:forEach>
            </select>
        </div>
    </div>
    <c:if test="${editable}">
        <div class="form-group">
            <div class="col-sm-offset-3 col-sm-9">
                <button type="button" id="staff-form-btn" class="btn btn-primary">กดเพื่อแก้ไขข้อมูล</button>
            </div>
        </div>
    </c:if>
</form>