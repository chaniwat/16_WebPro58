<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="template" uri="/WEB-INF/tlds/TemplateTag.tld" %>

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

                <form action="" method="POST" id="alumni-find-form" class="form-horizontal" onsubmit="javascript:;">
                    <input type="hidden" id="alumni-form-id" name="alumni-form-id" value="0">
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
                            <button type="button" id="alumni-find-form-btn" class="btn btn-primary">ขั้นตอนต่อไป »</button>
                        </div>
                    </div>
                </form>

            </div>

        </div>

        <template:script />

    </template:body>

</template:page>
