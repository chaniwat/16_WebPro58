<%@ page import="com.alumnisystem.factory.CurriculumFactory" %>
<%@ page import="java.util.ArrayList" %>
<%@ include file="/WEB-INF/importlib.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    pageContext.setAttribute("curricula", new CurriculumFactory().all());
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

                <form class="form-inline" onsubmit="javascript:;" style="text-align: center">
                    <div class="form-group">
                        <label for="alumni-name">รหัสศิษย์เก่า : </label>
                        <input type="text" class="form-control" id="user-id" placeholder="user-id" style="width: 120px; text-align: center;" value="${requestScope.alumni.alumni_id != 0 ? requestScope.alumni.alumni_id : 'ยังไม่ได้ระบุ'}" disabled>
                    </div>
                    <span style="display: inline-block; margin-left: 30px"></span>
                    <div class="form-group">
                        <label for="alumni-name">ชื่อศิษย์เก่า : </label>
                        <input type="text" class="form-control" id="alumni-name" placeholder="alumni-name" style="width: 350px; text-align: center;" value="${requestScope.alumni.pname_th}${requestScope.alumni.fname_th} ${requestScope.alumni.lname_th}" disabled>
                    </div>
                </form>

                <hr />

                <form action="${RouteHelper:generateURL("admin/alumni/add")}" method="POST" id="alumni-track-form" class="form-horizontal">
                    <input type="hidden" name="mode" value="FINISH">
                    <input type="hidden" name="alumni-id" value="${requestScope.alumni.alumni_id}">
                    <div class="form-group">
                        <label for="alumni-track-degree" class="col-md-3 control-label">ระดับการศึกษา</label>
                        <div class="col-md-9">
                            <select class="form-control" name="alumni-track-degree" id="alumni-track-degree" data-empty="false">
                                <option value="">โปรดเลือก</option>
                                <c:if test="${requestScope.alumni.tracks.size() == 0}">
                                    <option value="BACHELOR">ปริญญาตรี</option>
                                    <option value="MASTER">ปริญญาโท</option>
                                    <option value="DOCTORAL">ปริญญาเอก</option>
                                </c:if>
                                <c:if test="${requestScope.alumni.tracks.size() > 0}">
                                    <%
                                        ArrayList<Track> tracks = ((Alumni) request.getAttribute("alumni")).getTracks();
                                        for(Track track : tracks) {
                                            pageContext.setAttribute("have" + track.getCurriculum().getDegree().toString().toLowerCase(), true);
                                        }
                                    %>
                                    <c:if test="${pageScope.havebachelor == null}">
                                        <option value="BACHELOR">ปริญญาตรี</option>
                                    </c:if>
                                    <c:if test="${pageScope.havemaster == null}">
                                        <option value="MASTER">ปริญญาโท</option>
                                    </c:if>
                                    <c:if test="${pageScope.havedoctoral == null}">
                                        <option value="DOCTORAL">ปริญญาเอก</option>
                                    </c:if>
                                </c:if>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="alumni-track-curriculumid" class="col-md-3 control-label">หลักสูตร</label>
                        <div class="col-md-9">
                            <select class="form-control" name="alumni-track-curriculumid" id="alumni-track-curriculumid" data-empty="false">
                                <option value="">โปรดเลือก</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="alumni-track-trackid" class="col-md-3 control-label">แขนงวิชา</label>
                        <div class="col-md-9">
                            <select class="form-control" name="alumni-track-trackid" id="alumni-track-trackid" data-empty="false">
                                <option value="">โปรดเลือก</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="alumni-track-student_id" class="col-md-3 control-label">รหัสนักศึกษา</label>
                        <div class="col-md-9">
                            <input type="text" class="form-control" name="alumni-track-student_id" id="alumni-track-student_id" placeholder="Student id" data-empty="false" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="alumni-track-generation" class="col-md-3 control-label">รุ่น</label>
                        <div class="col-md-9">
                            <input type="number" class="form-control" name="alumni-track-generation" id="alumni-track-generation" placeholder="Generation" data-empty="false" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="alumni-track-starteduyear" class="col-md-3 control-label">ปีที่เข้าการศึกษา</label>
                        <div class="col-md-9">
                            <input type="text" class="form-control" name="alumni-track-starteduyear" id="alumni-track-starteduyear" placeholder="Start year" data-empty="false" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="alumni-track-endeduyear" class="col-md-3 control-label">ปีที่จบการศึกษา</label>
                        <div class="col-md-9">
                            <div class="input-group">
                                <input type="text" class="form-control" name="alumni-track-endeduyear" id="alumni-track-endeduyear" placeholder="End year" data-empty="false" />
                                <span class="input-group-btn">
                                    <button type="button" class="btn btn-info" id="alumni-track-endeduyear-calbtn">คำนวนอัตโนมัติ</button>
                                </span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-3 col-sm-9">
                            <button type="submit" id="alumni-track-form-btn" class="btn btn-success">สร้าง/เพิ่ม »</button>
                        </div>
                    </div>
                </form>

            </div>

        </div>

        <template:script />

    </template:body>

</template:page>
