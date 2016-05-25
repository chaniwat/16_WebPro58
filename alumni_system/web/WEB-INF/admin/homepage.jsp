<%@ include file="/WEB-INF/importlib.jsp" %>
<%@ page import="com.alumnisystem.utility.database.Database" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Connection connection = Database.getConnection();

    String sql = "SELECT\n" +
            "  (SELECT COUNT(*) FROM user) AS 'user_count',\n" +
            "  (SELECT COUNT(*) FROM alumni) AS 'alumni_count',\n" +
            "  (SELECT COUNT(*) FROM teacher) AS 'teacher_count',\n" +
            "  (SELECT COUNT(*) FROM staff) AS 'staff_count',\n" +
            "  (SELECT COUNT(*) FROM alumni_track\n" +
            "    JOIN track ON alumni_track.track_id = track.id\n" +
            "    JOIN curriculum ON track.curriculum_id = curriculum.id\n" +
            "  WHERE curriculum.degree = 'BACHELOR') AS 'bachelor_count',\n" +
            "  (SELECT COUNT(*) FROM alumni_track\n" +
            "    JOIN track ON alumni_track.track_id = track.id\n" +
            "    JOIN curriculum ON track.curriculum_id = curriculum.id\n" +
            "  WHERE curriculum.degree = 'MASTER') AS 'master_count',\n" +
            "  (SELECT COUNT(*) FROM alumni_track\n" +
            "    JOIN track ON alumni_track.track_id = track.id\n" +
            "    JOIN curriculum ON track.curriculum_id = curriculum.id\n" +
            "  WHERE curriculum.degree = 'DOCTORAL') AS 'doctoral_count';";

    ResultSet result = null;
    try {
        result = connection.prepareStatement(sql).executeQuery();

        if(result.next()) {
            pageContext.setAttribute("usercount", result.getInt("user_count"));
            pageContext.setAttribute("alumnicount", result.getInt("alumni_count"));
            pageContext.setAttribute("teachercount", result.getInt("teacher_count"));
            pageContext.setAttribute("staffcount", result.getInt("staff_count"));
            pageContext.setAttribute("bachelorcount", result.getInt("bachelor_count"));
            pageContext.setAttribute("mastercount", result.getInt("master_count"));
            pageContext.setAttribute("doctoralcount", result.getInt("doctoral_count"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
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
                            <span class="info-box-number">${usercount}</span>
                        </div>
                    </div>
                </div>

                <div class="col-md-3 col-sm-6 col-xs-12">
                    <div class="info-box bg-red">
                        <span class="info-box-icon"><i class="ion ion-person-stalker"></i></span>

                        <div class="info-box-content">
                            <span class="info-box-text">จำนวนศิษย์เก่า</span>
                            <span class="info-box-number">${alumnicount}</span>
                        </div>
                    </div>
                </div>

                <!-- fix for small devices only -->
                <div class="clearfix visible-sm-block"></div>

                <div class="col-md-3 col-sm-6 col-xs-12">
                    <div class="info-box bg-green">
                        <span class="info-box-icon"><i class="ion ion-person"></i></span>

                        <div class="info-box-content">
                            <span class="info-box-text">จำนวนอาจารย์</span>
                            <span class="info-box-number">${teachercount}</span>
                        </div>
                    </div>
                </div>

                <div class="col-md-3 col-sm-6 col-xs-12">
                    <div class="info-box bg-yellow">
                        <span class="info-box-icon"><i class="ion ion-ios-body"></i></span>

                        <div class="info-box-content">
                            <span class="info-box-text">จำนวนเจ้าหน้าที่/พนักงาน</span>
                            <span class="info-box-number">${staffcount}</span>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-md-offset-4 col-md-4">
                    <div class="box box-solid box-info">
                        <div class="box-header with-border">
                            <h3 class="box-title">ข้อมูลศิษย์เก่า</h3>
                            <div class="box-tools pull-right">
                                <button class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
                            </div>
                        </div>
                        <div class="box-body">
                            <div class="chart">
                                <canvas id="alumniChart" width="720" height="500"></canvas>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
        
        <template:script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.0.2/Chart.min.js"></script>
            <script>
                var data = {
                    labels: ["ปริญญาตรี", "ปริญญาโท", "ปริญญาเอก"],
                    datasets: [
                        {
                            label: "จำนวนศิษย์เก่า",
                            backgroundColor: "rgba(16, 171, 234, 1)",
                            borderWidth: 0,
                            hoverBackgroundColor: "rgba(89, 211, 234, 1)",
                            data: [${bachelorcount}, ${mastercount}, ${doctoralcount}],
                            yAxisID: "y-axis-0"
                        }
                    ]
                };
            </script>
        </template:script>

    </template:body>

</template:page>