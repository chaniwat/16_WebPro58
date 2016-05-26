<%@include file="/WEB-INF/tags/importlib.tag"%>
<%@tag pageEncoding="UTF-8" body-content="empty" %>

<nav class="navbar navbar-fixed-top">
        <div class="<%= RouteHelper.isAdminPage() ? "" : "container" %>">

        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="<%= (RouteHelper.isAdminPage() ? RouteHelper.generateURL("admin") : RouteHelper.generateHomeURL()) %>">
                <img src="${RouteHelper:generateURL("assets/images/itlogo-navbar.png")}" width="40" height="40" />
                <span>Alumni System <%= RouteHelper.isAdminPage() ? "- Admin" : "" %></span>
            </a>
        </div>

        <div id="navbar" class="navbar-collapse collapse">
            <c:if test="${RouteHelper:isAdminPage()}">

                <ul class="nav navbar-nav">
                    <li><a href="${RouteHelper:generateURL("admin")}">หน้าแรก</a></li>
                    <li><a href="${RouteHelper:generateURL("admin/user")}" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">ผู้ใช้<span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="${RouteHelper:generateURL("admin/user/alumni")}">ศิษย์เก่า</a></li>
                            <li><a href="${RouteHelper:generateURL("admin/user/teacher")}">อาจารย์</a></li>
                            <li><a href="${RouteHelper:generateURL("admin/user/staff")}">เจ้าหน้าที่/ผู้ดูแลระบบ</a></li>
                            <li role="separator" class="divider"></li>
                            <li><a href="${RouteHelper:generateURL("admin/user/add")}">เพิ่มผู้ใช้</a></li>
                        </ul>
                    </li>
                    <li><a href="${RouteHelper:generateURL("admin/alumni")}" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">ข้อมูลศิษย์เก่า<span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="${RouteHelper:generateURL("admin/alumni/bachelor")}">ปริญญาตรี</a></li>
                            <li><a href="${RouteHelper:generateURL("admin/alumni/master")}">ปริญญาโท</a></li>
                            <li><a href="${RouteHelper:generateURL("admin/alumni/doctoral")}">ปริญญาเอก</a></li>
                            <li role="separator" class="divider"></li>
                            <li><a href="${RouteHelper:generateURL("admin/alumni/add")}">เพิ่มศิษย์เก่า</a></li>
                        </ul>
                    </li>
                    <li><a href="${RouteHelper:generateURL("admin/survey")}" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">แบบสอบถาม/สำรวจ<span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="${RouteHelper:generateURL("admin/survey/all")}">ดูแบบสอบถามทั้งหมด</a></li>
                            <li role="separator" class="divider"></li>
                            <li><a href="${RouteHelper:generateURL("admin/survey/create")}">สร้างแบบสอบถาม</a></li>
                        </ul>
                    </li>
                    <li><a href="${RouteHelper:generateURL("admin/event")}" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">ข่าว/กิจกรรม<span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="${RouteHelper:generateURL("admin/event/all")}">ดูข่าว/กิจกรรมทั้งหมด</a></li>
                            <li role="separator" class="divider"></li>
                            <li><a href="${RouteHelper:generateURL("admin/event/create")}">สร้างข่าว/กิจกรรมใหม่</a></li>
                        </ul>
                    </li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <c:if test="${Authorization:isLogin()}">
                        <c:set var="user" scope="page" value="${Authorization:getCurrentUser()}" />
                        <li><p class="navbar-text">ยินดีต้อนรับผู้ดูแลระบบ, ${pageScope.user.fname_th}</p></li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><span class="glyphicon glyphicon-triangle-bottom"></span></a>
                            <ul class="dropdown-menu">
                                <li><a href="${RouteHelper:generateHomeURL()}">กลับหน้าแรก</a></li>
                                <li role="separator" class="divider"></li>
                                <li><a href="${RouteHelper:generateURL("logout")}">ออกจากระบบ</a></li>
                            </ul>
                        </li>
                    </c:if>
                    <c:if test="${!Authorization:isLogin()}">
                        <li><a href="${RouteHelper:generateURL("login")}">เข้าสู่ระบบ</a></li>
                    </c:if>
                </ul>
            </c:if>
            <c:if test="${!RouteHelper:isAdminPage()}">
                <ul class="nav navbar-nav">
                    <li><a href="${RouteHelper:generateHomeURL()}">หน้าแรก</a></li>
                    <li><a href="${RouteHelper:generateURL("event")}">ข่าวสารและกิจกรรม</a></li>
                    <li><a href="${RouteHelper:generateURL("alumni")}" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">ข้อมูลศิษย์เก่า <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="${RouteHelper:generateURL("alumni/bachelor")}">ปริญญาตรี</a></li>
                            <li><a href="${RouteHelper:generateURL("alumni/master")}">ปริญญาโท</a></li>
                            <li><a href="${RouteHelper:generateURL("alumni/doctoral")}">ปริญญาเอก</a></li>
                        </ul>
                    </li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <c:if test="${Authorization:isLogin()}">
                        <c:set var="user" scope="page" value="${Authorization:getCurrentUser()}" />
                        <li><p class="navbar-text">ยินดีต้อนรับ, ${pageScope.user.fname_th}</p></li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><span class="glyphicon glyphicon-triangle-bottom"></span></a>
                            <ul class="dropdown-menu">
                                <li><a href="${RouteHelper:generateURL("profile")}">โปรไฟล์</a></li>
                                <c:if test="${pageScope.user.isAdmin()}">
                                    <li><a href="${RouteHelper:generateURL("admin")}">เข้าระบบแอดมิน</a></li>
                                </c:if>
                                <li role="separator" class="divider"></li>
                                <li><a href="${RouteHelper:generateURL("logout")}">ออกจากระบบ</a></li>
                            </ul>
                        </li>
                    </c:if>
                    <c:if test="${!Authorization:isLogin()}">
                        <li><a href="${RouteHelper:generateURL("login")}">เข้าสู่ระบบ</a></li>
                    </c:if>
                </ul>
            </c:if>
        </div>

    </div>
</nav>