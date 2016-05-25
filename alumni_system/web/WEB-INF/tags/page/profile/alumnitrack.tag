<%@ tag import="com.alumnisystem.factory.AlumniFactory" %>
<%@ tag import="com.alumnisystem.model.Alumni" %>
<%@ tag import="com.alumnisystem.model.User" %>
<%@ tag import="com.alumnisystem.model.Track" %>
<%@ tag import="com.alumnisystem.exception.AlumniNotFound" %>
<%@ include file="/WEB-INF/tags/importlib.tag" %>
<%@tag pageEncoding="UTF-8" body-content="empty" %>

<%@attribute name="user" required="true" type="com.alumnisystem.model.User" rtexprvalue="true" %>

<%
    AlumniFactory alumniFactory = new AlumniFactory();

    Alumni alumni = alumniFactory.findByUserId(user.getId());
    User currentUser = Authorization.getCurrentUser();

    boolean self = false;
    if(currentUser.getType() == User.Type.ALUMNI) {
        try {
            Alumni currentAlumni = alumniFactory.findByUserId(currentUser.getId());
            self = currentUser.getType() == User.Type.ALUMNI && alumni.getAlumni_id() == currentAlumni.getAlumni_id();
        } catch (AlumniNotFound ignored) { }
    }

    boolean editable = (currentUser.isAdmin() || self);
    for(Track track : alumni.getTracks()) {
        request.setAttribute(track.getCurriculum().getDegree().toString().toLowerCase(), track);
    }

    request.setAttribute("editable", editable);
    request.setAttribute("self", self);
%>

<table class="table table-bordered">

    <thead>
        <tr>
            <th>รหัสนักศึกษา</th>
            <th>รุ่น</th>
            <th>แขนงวิชา</th>
            <th>หลักสูตร</th>
            <th>ระดับการศึกษา</th>
            <th>ปีที่เข้าการศึกษา</th>
            <th>ปีที่จบการศึกษา</th>
        </tr>
    </thead>

    <tbody>
        <c:if test="${requestScope.bachelor != null}">
            <tr>
                <td>${bachelor.student_id}</td>
                <td>${bachelor.generation}</td>
                <td>${bachelor.name_th}</td>
                <td>${bachelor.curriculum.cname_th}</td>
                <td>${bachelor.curriculum.degree.nameTH}</td>
                <td>${bachelor.starteduyear}</td>
                <td>${bachelor.endeduyear}</td>
            </tr>
        </c:if>
        <c:if test="${requestScope.master != null}">
            <tr>
                <td>${master.student_id}</td>
                <td>${master.generation}</td>
                <td>${master.name_th}</td>
                <td>${master.curriculum.cname_th}</td>
                <td>${master.curriculum.degree.nameTH}</td>
                <td>${master.starteduyear}</td>
                <td>${master.endeduyear}</td>
            </tr>
        </c:if>
        <c:if test="${requestScope.doctoral != null}">
            <tr>
                <td>${doctoral.student_id}</td>
                <td>${doctoral.generation}</td>
                <td>${doctoral.name_th}</td>
                <td>${doctoral.curriculum.cname_th}</td>
                <td>${doctoral.curriculum.degree.nameTH}</td>
                <td>${doctoral.starteduyear}</td>
                <td>${doctoral.endeduyear}</td>
            </tr>
        </c:if>
    </tbody>

</table>

<c:if test="${self}">
    <a href="${RouteHelper:generateURL("track/edit")}" class="btn btn-primary">แก้ไขข้อมูล</a>
</c:if>
<c:if test="${!self}">
    <a href="${RouteHelper:generateURL("track/edit")}/${alumni.alumni_id}" class="btn btn-primary">แก้ไขข้อมูล</a>
</c:if>