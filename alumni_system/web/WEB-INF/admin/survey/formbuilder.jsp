<%@ include file="/WEB-INF/importlib.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<template:page adminPage="${true}">

    <template:head title="Alumni System - Admin - Survey Form Builder">

        <template:style>
            <link href="${RouteHelper:generateURL("assets/css/bootstrap-form-builder.css")}" rel="stylesheet">
        </template:style>

    </template:head>

    <style>
        body, html {
            height: auto;
        }
    </style>

    <template:body>

        <template:navbar />

            <div class="container-admin">

                <div class="page-header" style="border-bottom: 0px;">
                    <h1>สร้างฟอร์มแบบสอบถาม</h1>

                    <c:if test="${ResponseHelper:hasCodeInRequest()}">
                        <c:if test="${ResponseHelper:getRequestCode() == ResponseHelper.ADD_NEW_SURVEY_COMPLETE}">
                            <div class="alert alert-danger alert-dismissible" role="alert">
                                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                ADD_NEW_SURVEY_COMPLETE
                            </div>
                        </c:if>
                    </c:if>

                    <ul class="nav nav-tabs" role="tablist">
                        <li role="presentation" class="active"><a href="#formdetail" aria-controls="formdetail" role="tab" data-toggle="tab">Form Detail</a></li>
                        <li role="presentation"><a href="#formbuilder" aria-controls="formbuilder" role="tab" data-toggle="tab">Form Builder</a></li>
                    </ul>
                </div>

                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane active" id="formdetail">
                        <div class="container">
                            <form action="${RouteHelper:generateURL("admin/survey/create")}" method="POST" id="survey-form">
                                <input type="hidden" name="survey-schema" id="survey-schema" />

                                <div class="form-group">
                                    <label for="survey-form-title" class="control-label">หัวเรื่อง</label>
                                    <input type="text" class="form-control" name="survey-form-title" id="survey-form-title" placeholder="Name" data-empty="false">
                                </div>
                                <div class="form-group">
                                    <label for="survey-form-description" class="control-label">คำอธิบาย</label>
                                    <input type="text" class="form-control" name="survey-form-description" id="survey-form-description" placeholder="Description">
                                </div>

                                <div class="form-group">
                                    <button type="button" class="btn btn-success" data-toggle="modal" data-target="#alertModal">สร้างฟอร์ม</button>
                                </div>

                                <div class="modal fade" id="alertModal" tabindex="-1" role="dialog" aria-labelledby="alertModalLabel">
                                    <div class="modal-dialog" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                                <h4 class="modal-title" id="alertModalLabel">Alert</h4>
                                            </div>
                                            <div class="modal-body">
                                                <h2>ยืนยันการสร้างฟอร์ม</h2>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-danger" data-dismiss="modal">ยกเลิก</button>
                                                <button type="submit" class="btn btn-success">ยืนยัน</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div role="tabpanel" class="tab-pane" id="formbuilder">

                            <div class="row clearfix">
                                <!-- Building Form. -->
                                <div class="col-md-6">
                                    <div class="clearfix">
                                        <div id="build">
                                            <form id="target" class="form-horizontal">

                                            </form>
                                        </div>
                                    </div>
                                </div>
                                <!-- / Building Form. -->

                                <!-- Components -->
                                <div class="col-md-6">
                                    <ul class="nav nav-tabs" id="formtabs">
                                        <!-- Tab nav -->
                                    </ul>
                                    <form class="form-horizontal" id="components">
                                        <fieldset>
                                            <div class="tab-content">
                                                <!-- Tabs of snippets go here -->
                                            </div>
                                        </fieldset>
                                    </form>
                                </div>
                                <!-- / Components -->
                            </div>

                            <div class="row clearfix">
                                <div class="col-md-12">
                                    <hr>
                                    Bootstrap-Form-Builder : Created By Adam Moore (<a href="http://twitter.com/minikomi" >@minikomi</a>).<br/>
                                    Modified By Chaniwat Seangchai (<a href="http://twitter.com/meranotemajimo" >@meranotemajimo</a>).<br/>
                                </div>
                            </div>

                    </div>
                </div>
            </div>

        </form>

        <template:script skipBootstrap="${true}">
            <script data-main="${RouteHelper:generateURL("assets/js/bootstrap-form-builder.min.js")}" src="${RouteHelper:generateURL("assets/js/lib/require.js")}"></script>
        </template:script>

    </template:body>


</template:page>