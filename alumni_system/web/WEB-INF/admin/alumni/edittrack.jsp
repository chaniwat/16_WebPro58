<%@ page import="java.util.ArrayList" %>
<%@ include file="/WEB-INF/importlib.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    ArrayList<Track> tracks = (ArrayList<Track>) request.getAttribute("tracks");
    if(tracks != null) {
        for (Track track : tracks) {
            switch (track.getCurriculum().getDegree()) {
                case BACHELOR: pageContext.setAttribute("bachelor", track); break;
                case MASTER: pageContext.setAttribute("master", track); break;
                case DOCTORAL: pageContext.setAttribute("doctoral", track); break;
            }
        }
    }
%>

<template:page adminPage="${true}">

    <template:head title="Alumni System - Admin - Alumni track">

        <template:style />

    </template:head>

    <template:body>

        <template:navbar />

        <div class="container-admin">

            <div class="page-header" style="border-bottom: 0px;">
                <h1>
                    <c:if test="${requestScope.tracks == null}">
                        ไม่พบศิษย์เก่า
                    </c:if>
                    <c:if test="${requestScope.tracks != null}">
                        ข้อมูลการศึกษา
                    </c:if>
                </h1>

                <c:if test="${ResponseHelper:hasCodeInRequest()}">
                    <c:if test="${ResponseHelper:getRequestCode() == ResponseHelper.ALUMNI_TRACK_UPDATED_COMPLETE}">
                        <div class="alert alert-success alert-dismissible" role="alert">
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            ALUMNI_TRACK_UPDATE_COMPLETE
                        </div>
                    </c:if>
                </c:if>

                <ul class="nav nav-tabs" role="tablist">
                    <c:if test="${pageScope.bachelor != null}">
                        <li role="presentation" class="active"><a href="#bachelor" aria-controls="profile" role="tab" data-toggle="tab">Bachelor</a></li>
                    </c:if>
                    <c:if test="${pageScope.master != null}">
                        <li role="presentation" ${pageScope.bachelor == null ? 'class=\"active\"' : ''}><a href="#master" aria-controls="track" role="tab" data-toggle="tab">Master</a></li>
                    </c:if>
                    <c:if test="${pageScope.doctoral != null}">
                        <li role="presentation" ${pageScope.bachelor == null && pageScope.master == null ? 'class=\"active\"' : ''}><a href="#doctoral" aria-controls="setting" role="tab" data-toggle="tab">doctoral</a></li>
                    </c:if>
                </ul>
            </div>

            <div class="container">
                <c:if test="${requestScope.tracks != null}">
                    <div class="tab-content">
                        <c:if test="${pageScope.bachelor != null}">
                            <div role="tabpanel" class="tab-pane active" id="bachelor">
                                <template:editAlumniTrack track="${bachelor}" />
                            </div>
                        </c:if>
                        <c:if test="${pageScope.master != null}">
                            <div role="tabpanel" class="tab-pane" id="master">
                                <template:editAlumniTrack track="${master}" />
                            </div>
                        </c:if>
                        <c:if test="${pageScope.doctoral != null}">
                            <div role="tabpanel" class="tab-pane" id="doctoral">
                                <template:editAlumniTrack track="${doctoral}" />
                            </div>
                        </c:if>
                    </div>
                </c:if>
            </div>

        </div>

        <template:script />

    </template:body>

</template:page>