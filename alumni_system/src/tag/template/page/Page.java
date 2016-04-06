package tag.template.page;

import model.User;
import model.auth.Authorization;
import model.utility.RouteUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;
import java.io.StringWriter;

/**
 * Created by meranote on 4/5/2016 AD.
 */
public class Page extends BodyTagSupport {

    private JspWriter out;
    private HttpServletRequest request;
    private HttpSession session;
    private Authorization auth;

    private String title;

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int doStartTag() throws JspException {
        out = pageContext.getOut();
        request = (HttpServletRequest) pageContext.getRequest();
        session = request.getSession();
        auth = Authorization.getAuthInstance(session);

        try {
            out.println(
                    "<!DOCTYPE html>\n" +
                            "<html>\n" +
                            "<head>\n" +
                            "<title>" + title + "</title>\n" +
                            "<meta charset='UTF-8' />\n" +
                            "<meta http-equiv='X-UA-Compatible' content='IE=edge' />\n" +
                            "<meta name='viewport' content='width=device-width, initial-scale=1' />\n" +
                            "\n" +
                            "<link href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css' rel='stylesheet' />\n" +
                            "<link href='https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css' rel='stylesheet' />\n" +
                            "<link href='" + RouteUtils.generateURL(request, "assets/fonts/ahtiti.css") + "' rel='stylesheet' />\n" +
                            "<link href='" + RouteUtils.generateURL(request, "assets/css/style.css") + "' rel='stylesheet' />\n" +
                            "</head>\n" +
                            "\n" +
                            "<body class='fixed-navbar'>\n" +
                            "\n" +
                            "<nav class='navbar navbar-fixed-top'>\n" +
                            "<div class='container'>\n" +
                            "\n" +
                            "<div class='navbar-header'>\n" +
                            "<button type='button' class='navbar-toggle collapsed' data-toggle='collapse' data-target='#navbar' aria-expanded='false' aria-controls='navbar'>\n" +
                            "<span class='sr-only'>Toggle navigation</span>\n" +
                            "<span class='icon-bar'></span>\n" +
                            "<span class='icon-bar'></span>\n" +
                            "<span class='icon-bar'></span>\n" +
                            "</button>\n" +
                            "<a class='navbar-brand' href='" + RouteUtils.generateHomeURL(request) + "'>\n" +
                            "<img src='" + RouteUtils.generateURL(request, "assets/images/itlogo-navbar.png") + "' width='40' height='40' />\n" +
                            "<span>Alumni System</span>\n" +
                            "</a>\n" +
                            "</div>\n" +
                            "\n" +
                            "<div id='navbar' class='navbar-collapse collapse'>\n" +
                            "<ul class='nav navbar-nav'>\n" +
                            "<li><a href='" + RouteUtils.generateHomeURL(request) + "'>หน้าแรก</a></li>\n" +
                            "</ul>\n" +
                            "<ul class='nav navbar-nav navbar-right'>\n"
            );


            if (auth.isLogin()) {
                User user = auth.getCurrentUser();
                out.println(
                        "<li><p class='navbar-text'>ยินดีต้อนรับ, " + user.getUsername() + "</p></li>\n" +
                                "<li class='dropdown'>\n" +
                                "<a href='#' class='dropdown-toggle' data-toggle='dropdown' role='button' aria-haspopup='true' aria-expanded='false'><span class='glyphicon glyphicon-triangle-bottom'></span></a>\n" +
                                "<ul class='dropdown-menu'>\n" +
                                "<li><a href='javascript:;'>โปรไฟล์</a></li>\n" +
                                "<li role='separator' class='divider'></li>\n" +
                                "<li><a href='" + RouteUtils.generateURL(request, "logout") + "'>ออกจากระบบ</a></li>\n" +
                                "</ul>\n" +
                                "</li>\n"
                );
            } else {
                out.println("<li><a href='" + RouteUtils.generateURL(request, "login") + "'>เข้าสู่ระบบ</a></li>\n");
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

            out.print(
                "<script src='https://code.jquery.com/jquery-2.2.0.min.js'></script>\n" +
                        "<script src='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js'></script>\n" +
                        "<script src='" + RouteUtils.generateURL(request, "assets/js/script.js") + "'></script>\n" +
                        "</body>\n" +
                        "</html>"
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        return EVAL_PAGE;
    }
}
