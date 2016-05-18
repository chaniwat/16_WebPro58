<%@ include file="/WEB-INF/importlib.jsp" %>
<%@ page import="com.alumnisystem.utility.database.Database" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Connection connection = Database.getConnection();

    String sql;
    PreparedStatement stmt;
    ResultSet result;
%>

<template:page adminPage="${true}">

    <template:head title="Alumni System - Admin - Home">

        <template:style />

    </template:head>

    <template:body>

        <template:navbar />

        <div class="container-admin">

            <div class="page-header">
                <h1>ภาพรวมของระบบศิษย์เก่า</h1>
            </div>

            <div class="row">
                <div class="col-md-3 col-sm-6 col-xs-12">
                    <div class="info-box bg-aqua">
                        <span class="info-box-icon"><i class="ion ion-android-person-add"></i></span>

                        <div class="info-box-content">
                            <span class="info-box-text">จำนวนสมาชิกในระบบ</span>
                            <span class="info-box-number">
                                <%
                                    sql = "SELECT COUNT(*) AS \"count\" FROM user";
                                    stmt = connection.prepareStatement(sql);

                                    result = stmt.executeQuery();
                                    result.next();

                                    out.println(result.getInt("count"));
                                %>
                            </span>
                        </div>
                        <!-- /.info-box-content -->
                    </div>
                    <!-- /.info-box -->
                </div>
                <!-- /.col -->
                <div class="col-md-3 col-sm-6 col-xs-12">
                    <div class="info-box bg-red">
                        <span class="info-box-icon"><i class="ion ion-person-stalker"></i></span>

                        <div class="info-box-content">
                            <span class="info-box-text">จำนวนศิษย์เก่า</span>
                            <span class="info-box-number">
                                <%
                                    sql = "SELECT COUNT(*) AS \"count\" FROM alumni";
                                    stmt = connection.prepareStatement(sql);

                                    result = stmt.executeQuery();
                                    result.next();

                                    out.println(result.getInt("count"));
                                %>
                            </span>
                        </div>
                        <!-- /.info-box-content -->
                    </div>
                    <!-- /.info-box -->
                </div>
                <!-- /.col -->

                <!-- fix for small devices only -->
                <div class="clearfix visible-sm-block"></div>

                <div class="col-md-3 col-sm-6 col-xs-12">
                    <div class="info-box bg-green">
                        <span class="info-box-icon"><i class="ion ion-person"></i></span>

                        <div class="info-box-content">
                            <span class="info-box-text">จำนวนอาจารย์</span>
                            <span class="info-box-number">
                                <%
                                    sql = "SELECT COUNT(*) AS \"count\" FROM teacher";
                                    stmt = connection.prepareStatement(sql);

                                    result = stmt.executeQuery();
                                    result.next();

                                    out.println(result.getInt("count"));
                                %>
                            </span>
                        </div>
                        <!-- /.info-box-content -->
                    </div>
                    <!-- /.info-box -->
                </div>
                <!-- /.col -->
                <div class="col-md-3 col-sm-6 col-xs-12">
                    <div class="info-box bg-yellow">
                        <span class="info-box-icon"><i class="ion ion-ios-body"></i></span>

                        <div class="info-box-content">
                            <span class="info-box-text">จำนวนเจ้าหน้าที่/พนักงาน</span>
                            <span class="info-box-number">
                                <%
                                    sql = "SELECT COUNT(*) AS \"count\" FROM staff";
                                    stmt = connection.prepareStatement(sql);

                                    result = stmt.executeQuery();
                                    result.next();

                                    out.println(result.getInt("count"));
                                %>
                            </span>
                        </div>
                        <!-- /.info-box-content -->
                    </div>
                    <!-- /.info-box -->
                </div>
                <!-- /.col -->
            </div>

            <div class="row">
                <div class="col-md-offset-4 col-md-4">
                    <div class="box box-solid box-info">
                        <div class="box-header with-border">
                            <h3 class="box-title">ข้อมูลศิษย์เก่า</h3>
                            <div class="box-tools pull-right">
                                <button class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
                            </div><!-- /.box-tools -->
                        </div><!-- /.box-header -->
                        <div class="box-body">
                            <div class="chart">
                                <canvas id="alumniChart" width="720" height="500"></canvas>
                            </div>
                        </div><!-- /.box-body -->
                    </div>
                </div>
            </div>

        </div>
        
        <template:script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.0.2/Chart.min.js"></script>
            <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/holder/2.9.3/holder.min.js"></script>
        </template:script>

    </template:body>

</template:page>