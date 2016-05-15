package com.alumnisystem.page;

import com.alumnisystem.model.User;
import com.alumnisystem.utility.Authorization;
import com.alumnisystem.utility.RouteHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;

/**
 * Created by meranote on 4/5/2016 AD.
 */
public class PageAdmin extends BodyTagSupport {

    private JspWriter out;
    private HttpServletRequest request;

    private String title;
    private boolean skipBootstrapJsLoad = false;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSkipBootstrapJsLoad(boolean skipBootstrapJsLoad) {
        this.skipBootstrapJsLoad = skipBootstrapJsLoad;
    }

    @Override
    public int doStartTag() throws JspException {
        out = pageContext.getOut();
        request = (HttpServletRequest) pageContext.getRequest();

        try {
            out.println(
                    "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "<title>" + title + "</title>\n" +
                    "<meta charset='UTF-8' />\n" +
                    "<meta http-equiv='X-UA-Compatible' content='IE=edge' />\n" +
                    "<meta name='viewport' content='width=device-width, initial-scale=1' />\n" +
//                    "<link href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css' rel='stylesheet' />\n" +
                    "<link href='" + RouteHelper.generateURL("assets/css/bootstrap.min.theme.css") + "' rel='stylesheet' />\n" +
                    "<link href='https://cdnjs.cloudflare.com/ajax/libs/admin-lte/2.3.3/css/AdminLTE.min.css' rel='stylesheet' />\n" +
                    "<link href='https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css' rel='stylesheet' />\n" +
                    "<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css\">\n" +
                    "<link rel=\"stylesheet\" type=\"text/css\" href=\"https://cdn.datatables.net/t/bs/dt-1.10.11,fc-3.2.1,fh-3.1.1,r-2.0.2,rr-1.1.1,sc-1.4.1,se-1.1.2/datatables.min.css\"/>\n" +
                    "<link href='" + RouteHelper.generateURL("assets/fonts/ahtiti.css") + "' rel='stylesheet' />\n" +
                    "<link href='" + RouteHelper.generateURL("assets/css/style.css") + "' rel='stylesheet' />\n" +
                    "</head>\n" +
                    "\n" +
                    "<body class='fixed-navbar admin-page'>\n" +
                    "\n" +
                    "<sitedata contextPath=\"" + request.getContextPath() + "\"></sitedata>\n" +
                    "\n" +
                    "<nav class='navbar navbar-fixed-top'>\n" +
                    "<div>\n" +
                    "\n" +
                    "<div class='navbar-header'>\n" +
                    "<button type='button' class='navbar-toggle collapsed' data-toggle='collapse' data-target='#navbar' aria-expanded='false' aria-controls='navbar'>\n" +
                    "<span class='sr-only'>Toggle navigation</span>\n" +
                    "<span class='icon-bar'></span>\n" +
                    "<span class='icon-bar'></span>\n" +
                    "<span class='icon-bar'></span>\n" +
                    "</button>\n" +
                    "<a class='navbar-brand' href='" + RouteHelper.generateURL("admin") + "'>\n" +
                    "<img src='" + RouteHelper.generateURL("assets/images/itlogo-navbar.png") + "' width='40' height='40' />\n" +
                    "<span>Alumni System - Admin</span>\n" +
                    "</a>\n" +
                    "</div>\n" +
                    "\n" +
                    "<div id='navbar' class='navbar-collapse collapse'>\n" +
                    "<ul class='nav navbar-nav'>\n" +
                    "<li><a href='" + RouteHelper.generateURL("admin") + "'>หน้าแรก</a></li>\n" +
                    "<li><a href='" + RouteHelper.generateURL("admin/user") + "' class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-haspopup=\"true\" aria-expanded=\"false\">ผู้ใช้ <span class=\"caret\"></span></a>\n" +
                    "<ul class=\"dropdown-menu\">\n" +
                    "<li><a href='" + RouteHelper.generateURL("admin/user/alumni") + "'>ศิษย์เก่า</a></li>\n" +
                    "<li><a href='" + RouteHelper.generateURL("admin/user/teacher") + "'>อาจารย์</a></li>\n" +
                    "<li><a href='" + RouteHelper.generateURL("admin/user/staff") + "'>เจ้าหน้าที่/ผู้ดูแลระบบ</a></li>\n" +
                    "<li role=\"separator\" class=\"divider\"></li>\n" +
                    "<li><a href='" + RouteHelper.generateURL("admin/user/add") + "'>เพิ่มผู้ใช้</a></li>\n" +
                    "</ul>\n" +
                    "</li>\n" +
                    "<li><a href='" + RouteHelper.generateURL("admin/alumni") + "' class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-haspopup=\"true\" aria-expanded=\"false\">ข้อมูลศิษย์เก่า <span class=\"caret\"></span></a>\n" +
                    "<ul class=\"dropdown-menu\">\n" +
                    "<li><a href='" + RouteHelper.generateURL("admin/alumni/bachelor") + "'>ปริญญาตรี</a></li>\n" +
                    "<li><a href='" + RouteHelper.generateURL("admin/alumni/master") + "'>ปริญญาโท</a></li>\n" +
                    "<li><a href='" + RouteHelper.generateURL("admin/alumni/doctoral") + "'>ปริญญาเอก</a></li>\n" +
                    "<li role=\"separator\" class=\"divider\"></li>\n" +
                    "<li><a href='" + RouteHelper.generateURL("admin/alumni/add") + "'>เพิ่มศิษย์เก่า</a></li>\n" +
                    "</ul>\n" +
                    "</li>\n" +
                    "<li><a href='" + RouteHelper.generateURL("admin/survey") + "' class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-haspopup=\"true\" aria-expanded=\"false\">แบบสอบถาม/สำรวจ <span class=\"caret\"></span></a>\n" +
                    "<ul class=\"dropdown-menu\">\n" +
                    "<li><a href='" + RouteHelper.generateURL("admin/survey/create") + "'>สร้างแบบสอบถาม</a></li>\n" +
                    "</ul>\n" +
                    "</li>\n" +
                    "<li><a href='" + RouteHelper.generateURL("admin/event") + "' class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-haspopup=\"true\" aria-expanded=\"false\">ข่าว/กิจกรรม <span class=\"caret\"></span></a>\n" +
                    "<ul class=\"dropdown-menu\">\n" +
                    "<li><a href='" + RouteHelper.generateURL("admin/event/create") + "'>สร้างข่าว/กิจกรรมใหม่</a></li>\n" +
                    "</ul>\n" +
                    "</li>\n" +
                    "</ul>\n" +
                    "<ul class='nav navbar-nav navbar-right'>\n"
            );

            if (Authorization.isLogin()) {
                User user = Authorization.getCurrentUser();
                out.println(
                        "<li><p class='navbar-text'>ยินดีต้อนรับผู้ดูแลระบบ, " + user.getFname_th() + "</p></li>\n" +
                        "<li class='dropdown'>\n" +
                        "<a href='#' class='dropdown-toggle' data-toggle='dropdown' role='button' aria-haspopup='true' aria-expanded='false'><span class='glyphicon glyphicon-triangle-bottom'></span></a>\n" +
                        "<ul class='dropdown-menu'>\n" +
                        "<li><a href='" + RouteHelper.generateHomeURL() + "'>กลับหน้าแรก</a></li>"
                );
                out.println(
                        "<li role='separator' class='divider'></li>\n" +
                        "<li><a href='" + RouteHelper.generateURL("logout") + "'>ออกจากระบบ</a></li>\n" +
                        "</ul>\n" +
                        "</li>"
                );
            } else {
                out.println("<li><a href='" + RouteHelper.generateURL("login") + "'>เข้าสู่ระบบ</a></li>\n");
            }

            out.println(
                    "</ul>\n" +
                    "</div>\n" +
                    "\n" +
                    "</div>\n" +
                    "</nav>"
            );
        }  catch (IOException e) {
            e.printStackTrace();
        }

        return EVAL_BODY_BUFFERED;
    }

    @Override
    public int doEndTag() throws JspException {
        try {
            pageContext.getOut().print(bodyContent.getString());

            out.println("<script src='https://code.jquery.com/jquery-2.2.0.min.js'></script>");
            if(!skipBootstrapJsLoad) {
                out.println("" +
                        "<script src='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js'></script>\n" +
                        "<script src='https://cdnjs.cloudflare.com/ajax/libs/admin-lte/2.3.3/js/app.min.js'></script>"
                );
            }
            out.print(
                    "<script src='https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.0.2/Chart.min.js'></script>\n" +
                    "<script type=\"text/javascript\" src=\"https://cdnjs.cloudflare.com/ajax/libs/holder/2.9.3/holder.min.js\"></script>\n" +
                    "<script type=\"text/javascript\" src=\"https://cdn.datatables.net/t/bs/dt-1.10.11,fc-3.2.1,fh-3.1.1,r-2.0.2,rr-1.1.1,sc-1.4.1,se-1.1.2/datatables.min.js\"></script>\n" +
                    "<script src='" + RouteHelper.generateURL("assets/js/script.js") + "'></script>\n" +
                    "</body>\n" +
                    "</html>"
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        return EVAL_PAGE;
    }

}
