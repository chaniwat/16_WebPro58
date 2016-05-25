<%@ tag import="com.alumnisystem.factory.AlumniFactory" %>
<%@ tag import="com.alumnisystem.model.Alumni" %>
<%@ tag import="com.alumnisystem.model.User" %>
<%@ tag import="java.text.SimpleDateFormat" %>
<%@ tag import="com.alumnisystem.factory.JobTypeFactory" %>
<%@ include file="/WEB-INF/tags/importlib.tag" %>
<%@tag pageEncoding="UTF-8" body-content="empty" %>

<%@attribute name="user" required="true" type="com.alumnisystem.model.User" rtexprvalue="true" %>

<%
    AlumniFactory alumniFactory = new AlumniFactory();

    Alumni alumni = alumniFactory.findByUserId(user.getId());
    User currentUser = Authorization.getCurrentUser();

    boolean editable = (currentUser.isAdmin() ||
            (currentUser.getType() == User.Type.ALUMNI && alumni.getAlumni_id() == alumniFactory.findByUserId(currentUser.getId()).getAlumni_id()));

    int bDay = 0, bMonth = 0, bYear = 0;
    if(alumni.getBirthdate() != null) {
        bDay = Integer.parseInt(new SimpleDateFormat("dd").format(alumni.getBirthdate()));
        bMonth = Integer.parseInt(new SimpleDateFormat("MM").format(alumni.getBirthdate()));
        bYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(alumni.getBirthdate()));
    }

    request.setAttribute("alumni", alumni);
    request.setAttribute("editable", editable);
    request.setAttribute("bDay", bDay);
    request.setAttribute("bMonth", bMonth);
    request.setAttribute("bYear", bYear);
    request.setAttribute("jobTypes", new JobTypeFactory().all());
%>

<h2>ประวัติส่วนตัว</h2>

<hr />

<form action="${RouteHelper:generateURL("profile/edit")}" method="POST" id="alumni-form" class="form-horizontal">
    <input type="hidden" id="profilepage-usertype" name="profilepage-usertype" value="${user.type}"/>
    <input type="hidden" id="alumni-form-id" name="alumni-form-id" value="${alumni.alumni_id}"/>
    <div class="form-group">
        <label for="alumni-form-pnameth" class="col-md-3 control-label">คำนำหน้าชื่อ (ภาษาไทย)*</label>
        <div class="col-md-9">
            <input type="text" class="form-control" name="alumni-form-pnameth" id="alumni-form-pnameth" placeholder="Title (TH)" value="${alumni.pname_th}" data-empty="false">
        </div>
    </div>
    <div class="form-group">
        <label for="alumni-form-fnameth" class="col-md-3 control-label">ชื่อ (ภาษาไทย)*</label>
        <div class="col-md-9">
            <input type="text" class="form-control" name="alumni-form-fnameth" id="alumni-form-fnameth" placeholder="Name (TH)" value="${alumni.fname_th}" data-empty="false">
        </div>
    </div>
    <div class="form-group">
        <label for="alumni-form-lnameth" class="col-md-3 control-label">นามสกุล (ภาษาไทย)*</label>
        <div class="col-md-9">
            <input type="text" class="form-control" name="alumni-form-lnameth" id="alumni-form-lnameth" placeholder="Surname (TH)" value="${alumni.lname_th}" data-empty="false">
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
    <c:if test="${editable}">
        <div class="form-group">
            <label for="alumni-form-birthdate" class="col-md-3 control-label">วันเกิด</label>
            <div class="col-md-9" id="alumni-form-birthdate">
                <div class="row">
                    <div class="col-md-3">
                        <select class="form-control" name="alumni-form-birthdate-year" id="alumni-form-birthdate-year" data-default="${bYear == 0 ? "null" : bYear}">
                            <option value="null">Year</option>
                        </select>
                    </div>
                    <div class="col-md-3">
                        <select class="form-control" name="alumni-form-birthdate-month" id="alumni-form-birthdate-month" data-default="${bMonth == 0 ? "null" : bMonth}">
                            <option value="null">Month</option>
                        </select>
                    </div>
                    <div class="col-md-3">
                        <select class="form-control" name="alumni-form-birthdate-day" id="alumni-form-birthdate-day" data-default="${bDay == 0 ? "null" : bDay}">
                            <option value="null">Day</option>
                        </select>
                    </div>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label for="alumni-form-address" class="col-md-3 control-label">ที่อยู่</label>
            <div class="col-md-9">
                <textarea class="form-control" name="alumni-form-address" id="alumni-form-address" placeholder="Address">${alumni.address}</textarea>
            </div>
        </div>
        <div class="form-group">
            <label for="alumni-form-district" class="col-md-3 control-label">ตำบล/แขวง</label>
            <div class="col-md-9">
                <input type="text" class="form-control" name="alumni-form-district" id="alumni-form-district" placeholder="District" value="${alumni.district}">
            </div>
        </div>
        <div class="form-group">
            <label for="alumni-form-amphure" class="col-md-3 control-label">อำเภอ/เขต</label>
            <div class="col-md-9">
                <input type="text" class="form-control" name="alumni-form-amphure" id="alumni-form-amphure" placeholder="Amphure" value="${alumni.amphure}">
            </div>
        </div>
        <div class="form-group">
            <label for="alumni-form-province" class="col-md-3 control-label">จังหวัด</label>
            <div class="col-md-9">
                <input type="text" class="form-control" name="alumni-form-province" id="alumni-form-province" placeholder="Province" value="${alumni.province}">
            </div>
        </div>
        <div class="form-group">
            <label for="alumni-form-zipcode" class="col-md-3 control-label">รหัสไปรษณีย์</label>
            <div class="col-md-9">
                <input type="text" class="form-control" name="alumni-form-zipcode" id="alumni-form-zipcode" placeholder="Zipcode" value="${alumni.zipcode}">
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
    </c:if>
    <div class="form-group">
        <label for="alumni-form-jobtype" class="col-md-3 control-label">ประเภทสายงาน</label>
        <div class="col-md-9">
            <select class="form-control" name="alumni-form-jobtype" id="alumni-form-jobtype" ${alumni.job != null ? 'data-default=\"'.concat(alumni.job.jobType.id).concat('\"') : ""}>
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
            <select class="form-control" name="alumni-form-jobname" id="alumni-form-jobname" ${alumni.job != null ? 'data-default=\"'.concat(alumni.job.id).concat('\"') : ""}>
                <option value="null">โปรดเลือก</option>
                <option value="0">อื่นๆ</option>
            </select><br />
            <input type="text" class="form-control" name="alumni-form-jobnameother" id="alumni-form-jobnameother" placeholder="Position - อื่นๆ" data-lock="true">
        </div>
    </div>
    <div class="form-group">
        <label for="alumni-form-workname" class="col-md-3 control-label">ชื่อบริษัทที่ทำงาน</label>
        <div class="col-md-9">
            <input type="text" class="form-control" name="alumni-form-workname" id="alumni-form-workname" placeholder="Company name" value="${alumni.work_name}">
        </div>
    </div>
    <c:if test="${editable}">
        <div class="form-group">
            <div class="col-sm-offset-3 col-sm-9">
                <button type="button" id="alumni-form-btn" class="btn btn-primary">กดเพื่อแก้ไขข้อมูล</button>
            </div>
        </div>
    </c:if>
</form>
