package tag.template.page.profile;

import model.Staff;
import model.User;
import model.Work;
import model.auth.Authorization;
import model.utility.RouteUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by meranote on 4/10/2016 AD.
 */
public class StaffProfile extends SimpleTagSupport {

    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void doTag() throws JspException, IOException {
        HttpServletRequest request = (HttpServletRequest)((PageContext) getJspContext()).getRequest();
        HttpSession session = request.getSession();

        Staff staff = Staff.getStaffByUserId(user.getId());
        User currentUser = Authorization.getAuthInstance(session).getCurrentUser();

        boolean editable = currentUser.getType() == User.UserType.STAFF;

        ArrayList<Work.Section> sections = Work.Section.getAllSection();

        JspWriter out = getJspContext().getOut();
        out.println(
                "<h2>ประวัติส่วนตัว</h2>\n" +
                "<form action=\"" + RouteUtils.generateURL(request, "profile/edit") + "\" method=\"POST\" id=\"staff-form\" class=\"form-horizontal\">\n" +
                "<input type=\"hidden\" id=\"profilepage-usertype\" name=\"profilepage-usertype\" value=\"" + user.getType() + "\"/>\n" +
                "<div class=\"form-group\">\n" +
                "<label for=\"staff-form-id\" class=\"col-md-3 control-label\">รหัส</label>\n" +
                "<div class=\"col-md-9\">\n" +
                "<input type=\"text\" class=\"form-control\" name=\"staff-form-id\" id=\"staff-form-id\" placeholder=\"ID\" value=\"" + staff.getStaff_id() + "\" data-lock=\"true\">\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"form-group\">\n" +
                "<label for=\"staff-form-pnameth\" class=\"col-md-3 control-label\">คำนำหน้าชื่อ (ภาษาไทย)*</label>\n" +
                "<div class=\"col-md-9\">\n" +
                "<input type=\"text\" class=\"form-control\" name=\"staff-form-pnameth\" id=\"staff-form-pnameth\" placeholder=\"Title (TH)\" value=\"" + (staff.getPname_th() == null ? "" : staff.getPname_th()) + "\" data-empty=\"false\">\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"form-group\">\n" +
                "<label for=\"staff-form-fnameth\" class=\"col-md-3 control-label\">ชื่อ (ภาษาไทย)*</label>\n" +
                "<div class=\"col-md-9\">\n" +
                "<input type=\"text\" class=\"form-control\" name=\"staff-form-fnameth\" id=\"staff-form-fnameth\" placeholder=\"Name (TH)\" value=\"" + (staff.getFname_th() == null ? "" : staff.getFname_th()) + "\" data-empty=\"false\">\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"form-group\">\n" +
                "<label for=\"staff-form-lnameth\" class=\"col-md-3 control-label\">นามสกุล (ภาษาไทย)*</label>\n" +
                "<div class=\"col-md-9\">\n" +
                "<input type=\"text\" class=\"form-control\" name=\"staff-form-lnameth\" id=\"staff-form-lnameth\" placeholder=\"Surname (TH)\" value=\"" + (staff.getLname_th() == null ? "" : staff.getLname_th()) + "\" data-empty=\"false\">\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"form-group\">\n" +
                "<label for=\"staff-form-pnameen\" class=\"col-md-3 control-label\">คำนำหน้าชื่อ (ภาษาอังกฤษ)</label>\n" +
                "<div class=\"col-md-9\">\n" +
                "<input type=\"text\" class=\"form-control\" name=\"staff-form-pnameen\" id=\"staff-form-pnameen\" placeholder=\"Title (EN)\" value=\"" + (staff.getPname_en() == null ? "" : staff.getPname_en()) + "\">\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"form-group\">\n" +
                "<label for=\"staff-form-fnameen\" class=\"col-md-3 control-label\">ชื่อ (ภาษาอังกฤษ)</label>\n" +
                "<div class=\"col-md-9\">\n" +
                "<input type=\"text\" class=\"form-control\" name=\"staff-form-fnameen\" id=\"staff-form-fnameen\" placeholder=\"Name (EN)\" value=\"" + (staff.getFname_en() == null ? "" : staff.getFname_en()) + "\">\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"form-group\">\n" +
                "<label for=\"staff-form-lnameen\" class=\"col-md-3 control-label\">นามสกุล (ภาษาอังกฤษ)</label>\n" +
                "<div class=\"col-md-9\">\n" +
                "<input type=\"text\" class=\"form-control\" name=\"staff-form-lnameen\" id=\"staff-form-lnameen\" placeholder=\"Surname (EN)\" value=\"" + (staff.getLname_en() == null ? "" : staff.getLname_en()) + "\">\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"form-group\">\n" +
                "<label for=\"staff-form-email\" class=\"col-md-3 control-label\">อีเมลล์</label>\n" +
                "<div class=\"col-md-9\">\n" +
                "<input type=\"text\" class=\"form-control\" name=\"staff-form-email\" id=\"staff-form-email\" placeholder=\"Email\" value=\"" + (staff.getEmail() == null ? "" : staff.getEmail()) + "\">\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"form-group\">\n" +
                "<label for=\"staff-form-phone\" class=\"col-md-3 control-label\">เบอร์โทรศัพท์</label>\n" +
                "<div class=\"col-md-9\">\n" +
                "<input type=\"text\" class=\"form-control\" name=\"staff-form-phone\" id=\"staff-form-phone\" placeholder=\"Phone number\" value=\"" + (staff.getPhone() == null ? "" : staff.getPhone()) + "\">\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"form-group\">\n" +
                "<label for=\"staff-form-worksection\" class=\"col-md-3 control-label\">สถานะ</label>\n" +
                "<div class=\"col-md-9\">\n" +
                "<select class=\"form-control\" name=\"staff-form-worksection\" id=\"staff-form-worksection\" data-default=\"" + staff.getSection().getSection_id() + "\">"
        );
        for(Work.Section section : sections) {
            out.println("<option value=\"" + section.getSection_id() + "\">" + section.getName_th() + "</option>");
        }
        out.println("</select>\n" +
                "</div>\n" +
                "</div>"
        );
        if(editable) {
            out.println(
                    "<div class=\"form-group\">\n" +
                    "<div class=\"col-sm-offset-3 col-sm-9\">\n" +
                    "<button type=\"button\" id=\"staff-form-btn\" class=\"btn btn-primary\">แก้ไข</button>\n" +
                    "</div>\n" +
                    "</div>"
            );
        }
        out.println("</form>");
    }

}
