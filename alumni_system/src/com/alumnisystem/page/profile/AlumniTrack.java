package com.alumnisystem.page.profile;

import com.alumnisystem.factory.AlumniFactory;
import com.alumnisystem.model.Alumni;
import com.alumnisystem.model.Track;
import com.alumnisystem.model.User;
import com.alumnisystem.utility.Authorization;
import com.alumnisystem.utility.RouteHelper;

import javax.servlet.http.HttpServletRequest;
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

        AlumniFactory alumniFactory = new AlumniFactory();

        Alumni alumni = alumniFactory.findByUserId(user.getId());
        User currentUser = Authorization.getCurrentUser();

        boolean editable = (currentUser.isAdmin() ||
                (currentUser.getType() == User.Type.ALUMNI && alumni.getAlumni_id() == alumniFactory.findByUserId(currentUser.getId()).getAlumni_id()));

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
        for(Track track : alumni.getTracks()) {
            out.println(
                    "<tr>\n" +
                    "<td>" + track.getStudent_id() + "</td>\n" +
                    "<td>" + track.getGeneration() + "</td>\n" +
                    "<td>" + track.getName_th() + "</td>\n" +
                    "<td>" + track.getCurriculum().getCname_th() + "</td>\n" +
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
            out.println("<a href=\"" + RouteHelper.generateURL("track/edit") + "\" class=\"btn btn-primary\">แก้ไขข้อมูล</a>");
        }
    }

}
