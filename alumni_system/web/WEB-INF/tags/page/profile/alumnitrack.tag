<%@ tag import="com.alumnisystem.factory.AlumniFactory" %>
<%@ tag import="com.alumnisystem.model.Alumni" %>
<%@ tag import="com.alumnisystem.model.User" %>
<%@ include file="/WEB-INF/tags/importlib.tag" %>
<%@tag pageEncoding="UTF-8" body-content="empty" %>

<%@attribute name="user" required="true" type="com.alumnisystem.model.User" rtexprvalue="true" %>

<%
    AlumniFactory alumniFactory = new AlumniFactory();

    Alumni alumni = alumniFactory.findByUserId(user.getId());
    User currentUser = Authorization.getCurrentUser();

    boolean editable = (currentUser.isAdmin() ||
            (currentUser.getType() == User.Type.ALUMNI && alumni.getAlumni_id() == alumniFactory.findByUserId(currentUser.getId()).getAlumni_id()));

    request.setAttribute("alumni", alumni);
    request.setAttribute("editable", editable);
%>

<table class="table table-bordered">

    <thead>
        <tr>
            <th>รหัสนักศึกษา</th>
            <th>รุ่น</th>
            <th>แขนงวิชา</th>
            <th>หลักสูตร</th>
            <th>ปีที่เข้าการศึกษา</th>
            <th>ปีที่จบการศึกษา</th>
        </tr>
    </thead>

    <tbody>
        <c:forEach var="track" items="${alumni.tracks}">
            <tr>
                <td>${track.student_id}</td>
                <td>${track.generation}</td>
                <td>${track.name_th}</td>
                <td>${track.curriculum.cname_th}</td>
                <td>${track.starteduyear}</td>
                <td>${track.endeduyear}</td>
            </tr>
        </c:forEach>
    </tbody>

</table>

<a href="${RouteHelper:generateURL("track/edit")}" class="btn btn-primary">แก้ไขข้อมูล</a>