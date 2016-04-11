package tag.template.page.profile;

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

/**
 * Created by meranote on 4/10/2016 AD.
 */
public class AlumniTrack extends SimpleTagSupport {

    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void doTag() throws JspException, IOException {
        HttpServletRequest request = (HttpServletRequest)((PageContext) getJspContext()).getRequest();
        HttpSession session = request.getSession();

        Alumni alumni = Alumni.getAlumniByUserId(user.getId());
        User currentUser = Authorization.getAuthInstance(session).getCurrentUser();

        boolean editable = !((currentUser.getType() == User.UserType.TEACHER) ||
                (currentUser.getType() == User.UserType.ALUMNI && Alumni.getAlumniByUserId(currentUser.getId()).getAlumni_id() != alumni.getAlumni_id()));

        JspWriter out = getJspContext().getOut();
        out.println(
                "<table class=\"table table-bordered\">\n" +
                "<thead>\n" +
                "<tr>\n" +
                "<th>รหัสนักศึกษา</th>\n" +
                "<th>รุ่น</th>\n" +
                "<th>แขนงวิชา</th>\n" +
                "<th>หลักสูตร</th>\n" +
                "<th>ปีที่เข้าการศึกษา</th>\n" +
                "<th>ปีที่จบการศึกษา</th>\n" +
                "</tr>\n" +
                "</thead>\n" +
                "<tbody>"
        );
        for(Alumni.Track track : alumni.getTracks()) {
            out.println(
                    "<tr>\n" +
                    "<td>" + track.getStudent_id() + "</td>\n" +
                    "<td>" + track.getGeneration() + "</td>\n" +
                    "<td>" + track.getTrack().getName_th() + "</td>\n" +
                    "<td>" + track.getTrack().getCurriculum().getName_th() + "</td>\n" +
                    "<td>" + track.getStarteduyear() + "</td>\n" +
                    "<td>" + track.getEndeduyear() + "</td>\n" +
                    "</tr>"
            );
        }
        out.println(
                "</tbody>\n" +
                "</table>"
        );
        if(editable) {
            out.println("<a href=\"" + RouteUtils.generateURL(request, "track/edit") + "\" class=\"btn btn-primary\">แก้ไขข้อมูล</a>");
        }
    }

}
