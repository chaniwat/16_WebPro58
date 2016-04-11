package tag.template.page.profile;

import model.Address;
import model.Alumni;
import model.User;
import model.auth.Authorization;
import model.utility.RouteUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by meranote on 4/10/2016 AD.
 */
public class AlumniProfile extends SimpleTagSupport {

    private User user;
    private Alumni alumni;

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void doTag() throws JspException, IOException {
        HttpServletRequest request = (HttpServletRequest)((PageContext) getJspContext()).getRequest();
        HttpSession session = request.getSession();

        Alumni alumni = Alumni.getAlumniByUserId(user.getId());
        User currentUser = Authorization.getAuthInstance(session).getCurrentUser();
        ArrayList<Address.Province> provinces = Address.Province.getAllProvince();

        boolean editable = !((currentUser.getType() == User.UserType.TEACHER) ||
                (currentUser.getType() == User.UserType.ALUMNI && Alumni.getAlumniByUserId(currentUser.getId()).getStudent_id() != alumni.getStudent_id()));

        int bDay = 0, bMonth = 0, bYear = 0;
        if(alumni.getBirthdate() != null) {
            bDay = Integer.parseInt(new SimpleDateFormat("dd").format(alumni.getBirthdate()));
            bMonth = Integer.parseInt(new SimpleDateFormat("MM").format(alumni.getBirthdate()));
            bYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(alumni.getBirthdate()));
        }

        JspWriter out = getJspContext().getOut();
        out.println(
                "<h2>ประวัติส่วนตัว</h2>\n" +
                "<form action=\"" + RouteUtils.generateURL(request, "profile/edit") + "\" method=\"POST\" id=\"alumni-form\" class=\"form-horizontal\">\n" +
                "<input type=\"hidden\" id=\"profilepage-usertype\" name=\"usertype\" value=\"" + user.getType() + "\"/>\n" +
                "<div class=\"form-group\">\n" +
                "<label for=\"alumni-form-stuid\" class=\"col-md-3 control-label\">รหัสนักศักษา</label>\n" +
                "<div class=\"col-md-9\">\n" +
                "<input type=\"text\" class=\"form-control\" name=\"alumni-form-stuid\" id=\"alumni-form-stuid\" placeholder=\"Student ID\" value=\"" + (alumni.getStudent_id() == 0 ? "" : alumni.getStudent_id()) + "\" data-lock=\"true\">\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"form-group\">\n" +
                "<label for=\"alumni-form-pnameth\" class=\"col-md-3 control-label\">คำนำหน้าชื่อ (ภาษาไทย)</label>\n" +
                "<div class=\"col-md-9\">\n" +
                "<input type=\"text\" class=\"form-control\" name=\"alumni-form-pnameth\" id=\"alumni-form-pnameth\" placeholder=\"Title (TH)\" value=\"" + (alumni.getPname_th() == null ? "" : alumni.getPname_th()) + "\">\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"form-group\">\n" +
                "<label for=\"alumni-form-fnameth\" class=\"col-md-3 control-label\">ชื่อ (ภาษาไทย)</label>\n" +
                "<div class=\"col-md-9\">\n" +
                "<input type=\"text\" class=\"form-control\" name=\"alumni-form-fnameth\" id=\"alumni-form-fnameth\" placeholder=\"Name (TH)\" value=\"" + (alumni.getFname_th() == null ? "" : alumni.getFname_th()) + "\">\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"form-group\">\n" +
                "<label for=\"alumni-form-lnameth\" class=\"col-md-3 control-label\">นามสกุล (ภาษาไทย)</label>\n" +
                "<div class=\"col-md-9\">\n" +
                "<input type=\"text\" class=\"form-control\" name=\"alumni-form-lnameth\" id=\"alumni-form-lnameth\" placeholder=\"Surname (TH)\" value=\"" + (alumni.getLname_th() == null ? "" : alumni.getLname_th()) + "\">\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"form-group\">\n" +
                "<label for=\"alumni-form-pnameen\" class=\"col-md-3 control-label\">คำนำหน้าชื่อ (ภาษาอังกฤษ)</label>\n" +
                "<div class=\"col-md-9\">\n" +
                "<input type=\"text\" class=\"form-control\" name=\"alumni-form-pnameen\" id=\"alumni-form-pnameen\" placeholder=\"Title (EN)\" value=\"" + (alumni.getPname_en() == null ? "" : alumni.getPname_en()) + "\">\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"form-group\">\n" +
                "<label for=\"alumni-form-fnameen\" class=\"col-md-3 control-label\">ชื่อ (ภาษาอังกฤษ)</label>\n" +
                "<div class=\"col-md-9\">\n" +
                "<input type=\"text\" class=\"form-control\" name=\"alumni-form-fnameen\" id=\"alumni-form-fnameen\" placeholder=\"Name (EN)\" value=\"" + (alumni.getFname_en() == null ? "" : alumni.getFname_en()) + "\">\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"form-group\">\n" +
                "<label for=\"alumni-form-lnameen\" class=\"col-md-3 control-label\">นามสกุล (ภาษาอังกฤษ)</label>\n" +
                "<div class=\"col-md-9\">\n" +
                "<input type=\"text\" class=\"form-control\" name=\"alumni-form-lnameen\" id=\"alumni-form-lnameen\" placeholder=\"Surname (EN)\" value=\"" + (alumni.getLname_en() == null ? "" : alumni.getLname_en()) + "\">\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"form-group\">\n" +
                "<label for=\"alumni-form-nickname\" class=\"col-md-3 control-label\">ชื่อเล่น</label>\n" +
                "<div class=\"col-md-9\">\n" +
                "<input type=\"text\" class=\"form-control\" name=\"alumni-form-nickname\" id=\"alumni-form-nickname\" placeholder=\"Nickname\" value=\"" + (alumni.getNickname() == null ? "" : alumni.getNickname()) + "\">\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"form-group\">\n" +
                "<label for=\"alumni-form-birthdate\" class=\"col-md-3 control-label\">วันเกิด</label>\n" +
                "<div class=\"col-md-9\" id=\"alumni-form-birthdate\">\n" +
                "<div class=\"row\">\n" +
                "<div class=\"col-md-3\">\n" +
                "<select class=\"form-control\" name=\"alumni-form-birthdate-year\" id=\"alumni-form-birthdate-year\" data-default=\"" + (bYear == 0 ? "null" : bYear) + "\">\n" +
                "<option value=\"null\">Year</option>\n" +
                "</select>\n" +
                "</div>\n" +
                "<div class=\"col-md-3\">\n" +
                "<select class=\"form-control\" name=\"alumni-form-birthdate-month\" id=\"alumni-form-birthdate-month\" data-default=\"" + (bMonth == 0 ? "null" : bMonth) + "\">\n" +
                "<option value=\"null\">Month</option>\n" +
                "</select>\n" +
                "</div>\n" +
                "<div class=\"col-md-3\">\n" +
                "<select class=\"form-control\" name=\"alumni-form-birthdate-day\" id=\"alumni-form-birthdate-day\" data-default=\"" + (bDay == 0 ? "null" : bDay) + "\">\n" +
                "<option value=\"null\">Day</option>\n" +
                "</select>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"form-group\">\n" +
                "<label for=\"alumni-form-address\" class=\"col-md-3 control-label\">ที่อยู่</label>\n" +
                "<div class=\"col-md-9\">\n" +
                "<textarea class=\"form-control\" name=\"alumni-form-address\" id=\"alumni-form-address\" placeholder=\"Address\">" + (alumni.getAddress().getAddress() == null ? "" : alumni.getAddress().getAddress()) + "</textarea>\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"form-group\">\n" +
                "<label for=\"alumni-form-district\" class=\"col-md-3 control-label\">ตำบล/แขวง</label>\n" +
                "<div class=\"col-md-9\">\n" +
                "<input type=\"text\" class=\"form-control\" name=\"alumni-form-district\" id=\"alumni-form-district\" placeholder=\"District\" value=\"" + (alumni.getAddress().getDistrict() == null ? "" : alumni.getAddress().getDistrict()) + "\">\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"form-group\">\n" +
                "<label for=\"alumni-form-amphure\" class=\"col-md-3 control-label\">อำเภอ/เขต</label>\n" +
                "<div class=\"col-md-9\">\n" +
                "<input type=\"text\" class=\"form-control\" name=\"alumni-form-amphure\" id=\"alumni-form-amphure\" placeholder=\"Amphure\" value=\"" + (alumni.getAddress().getAmphure() == null ? "" : alumni.getAddress().getAmphure()) + "\">\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"form-group\">\n" +
                "<label for=\"alumni-form-province\" class=\"col-md-3 control-label\">จังหวัด</label>\n" +
                "<div class=\"col-md-9\">"
        );
        if(alumni.getAddress().getProvince() == null) {
            out.println("<select class=\"form-control\" name=\"alumni-form-province\" id=\"alumni-form-province\">");
        } else {
            out.println("<select class=\"form-control\" name=\"alumni-form-province\" id=\"alumni-form-province\" data-default=\"" + alumni.getAddress().getProvince().getProvince_id() + "\">");
        }
        out.println("<option value=\"null\">Province</option>");
        for(Address.Province province : provinces) {
            out.println("<option value=\"" + province.getProvince_id() + "\">" + province.getName_th() + "</option>");
        }
        out.println(
                "</select>\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"form-group\">\n" +
                "<label for=\"alumni-form-zipcode\" class=\"col-md-3 control-label\">รหัสไปรษณีย์</label>\n" +
                "<div class=\"col-md-9\">\n" +
                "<input type=\"text\" class=\"form-control\" name=\"alumni-form-zipcode\" id=\"alumni-form-zipcode\" placeholder=\"Zipcode\" value=\"" + (alumni.getAddress().getZipcode() == null ? "" : alumni.getAddress().getZipcode()) + "\">\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"form-group\">\n" +
                "<label for=\"alumni-form-email\" class=\"col-md-3 control-label\">อีเมลล์</label>\n" +
                "<div class=\"col-md-9\">\n" +
                "<input type=\"text\" class=\"form-control\" name=\"alumni-form-email\" id=\"alumni-form-email\" placeholder=\"Email\" value=\"" + (alumni.getEmail() == null ? "" : alumni.getEmail()) + "\">\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"form-group\">\n" +
                "<label for=\"alumni-form-phone\" class=\"col-md-3 control-label\">เบอร์โทรศัพท์</label>\n" +
                "<div class=\"col-md-9\">\n" +
                "<input type=\"text\" class=\"form-control\" name=\"alumni-form-phone\" id=\"alumni-form-phone\" placeholder=\"Phone number\" value=\"" + (alumni.getPhone() == null ? "" : alumni.getPhone()) + "\">\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"form-group\">\n" +
                "<label for=\"alumni-form-occupation\" class=\"col-md-3 control-label\">อาชีพ</label>\n" +
                "<div class=\"col-md-9\">\n" +
                "<input type=\"text\" class=\"form-control\" name=\"alumni-form-occupation\" id=\"alumni-form-occupation\" placeholder=\"Occupation\" value=\"" + (alumni.getOccupation() == null ? "" : alumni.getOccupation()) + "\">\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"form-group\">\n" +
                "<label for=\"alumni-form-workname\" class=\"col-md-3 control-label\">ชื่อบริษัท</label>\n" +
                "<div class=\"col-md-9\">\n" +
                "<input type=\"text\" class=\"form-control\" name=\"alumni-form-workname\" id=\"alumni-form-workname\" placeholder=\"Company name\" value=\"" + (alumni.getWork_name() == null ? "" : alumni.getWork_name()) + "\"b>\n" +
                "</div>\n" +
                "</div>"
        );
        if(editable) {
            out.println(
                    "<div class=\"form-group\">\n" +
                    "<div class=\"col-sm-offset-3 col-sm-9\">\n" +
                    "<button type=\"button\" id=\"alumni-form-btn\" class=\"btn btn-primary\">แก้ไข</button>\n" +
                    "</div>\n" +
                    "</div>"
            );
        }
        out.println("</form>");
    }
}
