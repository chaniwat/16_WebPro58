<%@ include file="/WEB-INF/importlib.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<template:page adminPage="${true}">

    <template:head title="Alumni System - Admin - Add New Alumni">

        <template:style />

    </template:head>

    <template:body>

        <template:navbar />

        <div class="container-admin">

            <div class="page-header">
                <h1>เพิ่มศิษย์เก่า</h1>

                <c:if test="${ResponseHelper:hasCodeInRequest()}">
                    <c:if test="${ResponseHelper:getRequestCode() == ResponseHelper.FORM_INPUT_NOT_COMPLETE}">
                        <div class="alert alert-danger alert-dismissible" role="alert">
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            FORM_INPUT_NOT_COMPLETE
                        </div>
                    </c:if>
                    <c:if test="${ResponseHelper:getRequestCode() == ResponseHelper.ADD_NEW_ALUMNI_COMPLETE}">
                        <div class="alert alert-success alert-dismissible" role="alert">
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            ADD_NEW_ALUMNI_COMPLETE
                        </div>
                    </c:if>
                    <c:if test="${ResponseHelper:getRequestCode() == ResponseHelper.ADD_NEW_ALUMNITRACK_COMPLETE}">
                        <div class="alert alert-success alert-dismissible" role="alert">
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            ADD_NEW_ALUMNITRACK_COMPLETE
                        </div>
                    </c:if>
                </c:if>
            </div>

            <div class="container">

                <form action="${RouteHelper:generateURL("admin/alumni/add")}" method="POST" id="alumni-find-form" class="form-horizontal">
                    <input type="hidden" name="mode" value="FIND">
                    <div class="form-group">
                        <label for="alumni-find-form-pnameth" class="col-md-3 control-label">คำนำหน้าชื่อ (ภาษาไทย)</label>
                        <div class="col-md-9">
                            <input type="text" class="form-control" name="alumni-find-form-pnameth" id="alumni-find-form-pnameth" placeholder="Title (TH)" value="" data-empty="false">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="alumni-find-form-fnameth" class="col-md-3 control-label">ชื่อ (ภาษาไทย)</label>
                        <div class="col-md-9">
                            <input type="text" class="form-control" name="alumni-find-form-fnameth" id="alumni-find-form-fnameth" placeholder="Name (TH)" value="" data-empty="false">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="alumni-find-form-lnameth" class="col-md-3 control-label">นามสกุล (ภาษาไทย)</label>
                        <div class="col-md-9">
                            <input type="text" class="form-control" name="alumni-find-form-lnameth" id="alumni-find-form-lnameth" placeholder="Surname (TH)" value="" data-empty="false">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-3 col-sm-9">
                            <button type="submit" id="alumni-find-form-btn" class="btn btn-primary">ขั้นตอนต่อไป »</button>
                        </div>
                    </div>
                </form>

            </div>

        </div>

        <template:script />

    </template:body>

</template:page>
