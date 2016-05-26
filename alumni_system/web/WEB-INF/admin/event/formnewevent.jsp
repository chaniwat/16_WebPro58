<%@ include file="/WEB-INF/importlib.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<template:page adminPage="${true}">

    <template:head title="Alumni System - Admin - New event">

        <template:style>
            <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap3-wysiwyg/0.3.3/bootstrap3-wysihtml5.min.css" rel="stylesheet" />
        </template:style>

    </template:head>

    <template:body>

        <template:navbar />

        <%-- need to cover all of it, for used in modal --%>
        <form action="${RouteHelper:generateURL("admin/event/create")}" method="POST" id="event-form">

            <div class="container-admin">

                <div class="page-header">
                    <h1>เพิ่มกิจกรรม/ข่าวสารใหม่</h1>
                </div>

                <div class="form-group">
                    <label for="event-form-title" class="control-label">หัวเรื่อง</label>
                    <input type="text" class="form-control" name="event-form-title" id="event-form-title" placeholder="Title" data-empty="false">
                </div>
                <div class="form-group">
                    <label for="event-form-description" class="control-label">คำอธิบาย</label>
                    <input type="text" class="form-control" name="event-form-description" id="event-form-description" placeholder="Description">
                </div>

                <div class="form-group">
                    <label for="event-form-detail">รายละเอียด</label>
                    <textarea class="textarea" name="event-form-detail" id="event-form-detail" style="width: 100%; height: 600px; font-size: 14px; line-height: 18px; border: 1px solid rgb(221, 221, 221); padding: 10px;" placeholder="Place some text here"></textarea>
                </div>

                <div class="form-group">
                    <button type="button" class="btn btn-success btn-lg" data-toggle="modal" data-target="#newsModal">สร้างข่าวใหม่</button>
                </div>
            </div>

            <div class="modal fade" id="newsModal" tabindex="-1" role="dialog" aria-labelledby="newsModalLabel">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title" id="newsModalLabel">Alert</h4>
                        </div>
                        <div class="modal-body">
                            <h2>ยืนยันการสร้างข่าว</h2>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-danger" data-dismiss="modal">ยกเลิก</button>
                            <button type="submit" class="btn btn-success">ยืนยัน</button>
                        </div>
                    </div>
                </div>
            </div>

        </form>

        <template:script>
            <script src="${RouteHelper:generateURL("assets/js/lib/bootstrap3-wysihtml5.all.min.js")}"></script>
        </template:script>

    </template:body>

</template:page>