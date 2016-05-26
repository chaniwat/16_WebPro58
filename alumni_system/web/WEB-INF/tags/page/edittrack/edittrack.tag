<%@ tag import="com.alumnisystem.factory.TrackFactory" %>
<%@ include file="/WEB-INF/tags/importlib.tag" %>
<%@tag pageEncoding="UTF-8" body-content="empty" %>

<%@attribute name="track" type="com.alumnisystem.model.Track" rtexprvalue="true" required="true" %>

<%
    request.setAttribute("curTracks", new TrackFactory().findAllByCurriculumID(track.getCurriculum().getId()));
%>

<c:set var="trackformname" value="${track.curriculum.degree.toString().toLowerCase()}-form" />
<h2>ระดับ${track.curriculum.degree.nameTH}</h2>

<hr />

<form action="${RouteHelper:isAdminPage() ? (RouteHelper:generateURL("admin/track/edit")) : (RouteHelper:generateURL("track/edit"))}" method="POST" id="${trackformname}" class="form-horizontal">
    <input type="hidden" name="degree" value="${track.curriculum.degree.toString().toLowerCase()}" />
    <div class="form-group">
        <label for="student_id" class="col-md-3 control-label">รหัสนักศึกษา</label>
        <div class="col-md-9">
            <input type="text" class="form-control" name="student_id" id="student_id" placeholder="Student id" value="${track.student_id}" data-lock="true" />
        </div>
    </div>
    <div class="form-group">
        <label for="generation" class="col-md-3 control-label">รุ่น</label>
        <div class="col-md-9">
            <input type="text" class="form-control" name="generation" id="generation" placeholder="Generation" value="${track.generation}" data-lock="true" />
        </div>
    </div>
    <div class="form-group">
        <label for="${trackformname}-trackid" class="col-md-3 control-label">แขนงวิชา</label>
        <div class="col-md-9">
            <select class="form-control" name="trackid" id="${trackformname}-trackid" data-default="${track.id}">
                <c:forEach var="curTrack" items="${curTracks}">
                    <option value="${curTrack.id}">${curTrack.name_th}</option>
                </c:forEach>
            </select>
        </div>
    </div>
    <div class="form-group">
        <label for="cname_th" class="col-md-3 control-label">หลักสูตร</label>
        <div class="col-md-9">
            <input type="text" class="form-control" name="cname_th" id="cname_th" placeholder="Curriculum Name" value="${track.curriculum.cname_th}" data-lock="true" />
        </div>
    </div>
    <div class="form-group">
        <label for="cyear" class="col-md-3 control-label">ปี</label>
        <div class="col-md-9">
            <input type="text" class="form-control" name="cyear" id="cyear" placeholder="Year" value="${track.curriculum.cyear}" data-lock="true" />
        </div>
    </div>
    <div class="form-group">
        <label for="sname_th" class="col-md-3 control-label">สาขาวิชา</label>
        <div class="col-md-9">
            <input type="text" class="form-control" name="sname_th" id="sname_th" placeholder="Subject Name" value="${track.curriculum.sname_th}" data-lock="true" />
        </div>
    </div>
    <div class="form-group">
        <label for="starteduyear" class="col-md-3 control-label">ปีที่เข้าการศึกษา</label>
        <div class="col-md-9">
            <input type="text" class="form-control" name="starteduyear" id="starteduyear" placeholder="Start year" value="${track.starteduyear}" data-lock="true" />
        </div>
    </div>
    <div class="form-group">
        <label for="endeduyear" class="col-md-3 control-label">ปีที่จบการศึกษา</label>
        <div class="col-md-9">
            <input type="text" class="form-control" name="endeduyear" id="endeduyear" placeholder="End year" value="${track.endeduyear}" data-lock="true" />
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-offset-3 col-sm-9">
            <button type="button" id="${trackformname}-btn" class="btn btn-primary">กดเพื่อแก้ไขข้อมูล</button>
        </div>
    </div>
</form>