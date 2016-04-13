package controller;

import annotation.auth.AuthGuard;
import model.*;
import model.auth.Authorization;
import model.utility.ResponseCodeUtils;
import model.utility.RouteUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

/**
 * Created by meranote on 4/7/2016 AD.
 */
@WebServlet(name = "EditProfileServlet", urlPatterns = {"/profile/edit"})
@AuthGuard(redirectback = false)
public class EditProfileServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        User.UserType type = User.UserType.valueOf(request.getParameter("profilepage-usertype"));
        switch (type) {
            case ALUMNI: doUpdateAlumni(request, response); return;
            case TEACHER: doUpdateTeacher(request, response); return;
            case STAFF: doUpdateStaff(request, response); return;
            case DEVELOPER: return;
        }
    }

    private void doUpdateStaff(HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, String> params = RouteUtils.convertParamsToHashMap(request, "staff-form-");

        // Check permission (prevent another alumni or teacher to edit staff's profile)
        Authorization auth = Authorization.getAuthInstance(request.getSession());
        User user = auth.getCurrentUser();

        if(params.get("staff-form-id") == null) {
            redirectToProfilePage(request, response, ResponseCodeUtils.FORM_INPUT_NOT_COMPLETE, true);
            return;
        }

        if(user.getType() != User.UserType.STAFF) {
            redirectToProfilePage(request, response, ResponseCodeUtils.NOT_ENOUGH_PERMISSION, true);
            return;
        }
        // End Check Permission

        if(params.get("staff-form-pnameth") == null ||
                params.get("staff-form-fnameth") == null || params.get("staff-form-lnameth") == null) {
            redirectToProfilePage(request, response, ResponseCodeUtils.FORM_INPUT_NOT_COMPLETE, true);
            return;
        }

        Staff staff = new Staff();
        staff.setStaff_id(Integer.parseInt(params.get("staff-form-id")));
        staff.setPname_th(params.get("staff-form-pnameth"));
        staff.setFname_th(params.get("staff-form-fnameth"));
        staff.setLname_th(params.get("staff-form-lnameth"));
        staff.setPname_en(params.get("staff-form-pnameen"));
        staff.setFname_en(params.get("staff-form-fnameen"));
        staff.setLname_en(params.get("staff-form-lnameen"));
        staff.setEmail(params.get("staff-form-email"));
        staff.setPhone(params.get("staff-form-phone"));
        staff.setSection(Work.Section.getSectionById(Integer.parseInt(params.get("staff-form-worksection"))));

        Staff.updateStaff(staff);

        redirectToProfilePage(request, response, ResponseCodeUtils.PROFILE_UPDATED_COMPLETE);
        return;
    }

    private void doUpdateTeacher(HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, String> params = RouteUtils.convertParamsToHashMap(request, "teacher-form-");

        // Check permission (prevent another alumni or teacher to edit another teacher's profile)
        Authorization auth = Authorization.getAuthInstance(request.getSession());
        User user = auth.getCurrentUser();

        if(params.get("teacher-form-id") == null) {
            redirectToProfilePage(request, response, ResponseCodeUtils.FORM_INPUT_NOT_COMPLETE, true);
            return;
        }
        int teacher_id = Integer.parseInt(params.get("teacher-form-id"));

        Teacher self = null;
        if(user.getType() == User.UserType.TEACHER) self = Teacher.getTeacherByUserId(user.getId());

        if((user.getType() == User.UserType.ALUMNI) ||
                (self != null && self.getTeacher_id() != teacher_id)) {
            redirectToProfilePage(request, response, ResponseCodeUtils.NOT_ENOUGH_PERMISSION, true);
            return;
        }
        // End Check Permission

        if(params.get("teacher-form-pnameth") == null ||
                params.get("teacher-form-fnameth") == null || params.get("teacher-form-lnameth") == null) {
            redirectToProfilePage(request, response, ResponseCodeUtils.FORM_INPUT_NOT_COMPLETE, true);
            return;
        }

        Teacher teacher = new Teacher();
        teacher.setTeacher_id(teacher_id);
        teacher.setPname_th(params.get("teacher-form-pnameth"));
        teacher.setFname_th(params.get("teacher-form-fnameth"));
        teacher.setLname_th(params.get("teacher-form-lnameth"));
        teacher.setPname_en(params.get("teacher-form-pnameen"));
        teacher.setFname_en(params.get("teacher-form-fnameen"));
        teacher.setLname_en(params.get("teacher-form-lnameen"));
        teacher.setEmail(params.get("teacher-form-email"));
        teacher.setPhone(params.get("teacher-form-phone"));
        teacher.setWork_status(Work.Status.valueOf(params.get("teacher-form-workstatus")));

        Teacher.updateTeacher(teacher);

        redirectToProfilePage(request, response, ResponseCodeUtils.PROFILE_UPDATED_COMPLETE);
        return;
    }

    private void doUpdateAlumni(HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, String> params = RouteUtils.convertParamsToHashMap(request, "alumni-form-");

        // Check permission (prevent another alumni or teacher to edit another alumni's profile)
        Authorization auth = Authorization.getAuthInstance(request.getSession());
        User user = auth.getCurrentUser();

        if(params.get("alumni-form-id") == null) {
            redirectToProfilePage(request, response, ResponseCodeUtils.FORM_INPUT_NOT_COMPLETE, true);
            return;
        }

        int alumni_id = Integer.parseInt(params.get("alumni-form-id"));

        Alumni self = null;
        if(user.getType() == User.UserType.ALUMNI) self = Alumni.getAlumniByUserId(user.getId());

        if((user.getType() == User.UserType.TEACHER) ||
                (self != null && self.getAlumni_id() != alumni_id)) {
            redirectToProfilePage(request, response, ResponseCodeUtils.NOT_ENOUGH_PERMISSION, true);
            return;
        }
        // End Check Permission

        if(params.get("alumni-form-pnameth") == null ||
                params.get("alumni-form-fnameth") == null || params.get("alumni-form-lnameth") == null) {
            redirectToProfilePage(request, response, ResponseCodeUtils.FORM_INPUT_NOT_COMPLETE, true);
            return;
        }

        Alumni alumni = new Alumni();
        alumni.setAlumni_id(alumni_id);
        alumni.setPname_th(params.get("alumni-form-pnameth"));
        alumni.setFname_th(params.get("alumni-form-fnameth"));
        alumni.setLname_th(params.get("alumni-form-lnameth"));
        alumni.setPname_en(params.get("alumni-form-pnameen"));
        alumni.setFname_en(params.get("alumni-form-fnameen"));
        alumni.setLname_en(params.get("alumni-form-lnameen"));
        alumni.setNickname(params.get("alumni-form-nickname"));

        if(params.get("alumni-form-birthdate-year") != null &&
                params.get("alumni-form-birthdate-month") != null &&
                params.get("alumni-form-birthdate-day") != null) {

            String birthdate = String.format("%04d", Integer.parseInt(params.get("alumni-form-birthdate-year"))) + "-"
                    + String.format("%02d", Integer.parseInt(params.get("alumni-form-birthdate-month"))) + "-"
                    + String.format("%02d", Integer.parseInt(params.get("alumni-form-birthdate-day")));
            try {
                alumni.setBirthdate(new SimpleDateFormat("yyyy-MM-dd").parse(birthdate));
            } catch (ParseException e) {
                e.printStackTrace();
            }

        } else {
            alumni.setBirthdate(null);
        }

        alumni.setEmail(params.get("alumni-form-email"));
        alumni.setPhone(params.get("alumni-form-phone"));
        alumni.setOccupation(params.get("alumni-form-occupation"));
        alumni.setWork_name(params.get("alumni-form-workname"));

        alumni.getAddress().setAddress(params.get("alumni-form-address"));
        alumni.getAddress().setDistrict(params.get("alumni-form-district"));
        alumni.getAddress().setAmphure(params.get("alumni-form-amphure"));

        if(params.get("alumni-form-province") != null) {
            alumni.getAddress().setProvince(Province.getProvinceByProvinceId(Integer.parseInt(params.get("alumni-form-province"))));
        } else {
            alumni.getAddress().setProvince(null);
        }

        alumni.getAddress().setZipcode(params.get("alumni-form-zipcode"));

        Alumni.updateAlumni(alumni);

        redirectToProfilePage(request, response, ResponseCodeUtils.PROFILE_UPDATED_COMPLETE);
        return;
    }

    private void redirectToProfilePage(HttpServletRequest request, HttpServletResponse response, int ErrorNumber) {
        redirectToProfilePage(request, response, ErrorNumber, false);
    }

    private void redirectToProfilePage(HttpServletRequest request, HttpServletResponse response, int ErrorNumber, boolean toSelf) {
        try {
            ResponseCodeUtils.pushSessionCode(request.getSession(), ErrorNumber);
            if(toSelf) response.sendRedirect(RouteUtils.generateURL(request, "profile"));
            else response.sendRedirect(RouteUtils.generateURL(request, (String) request.getSession().getAttribute("profile.view.current.path")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        request.getSession().setAttribute("profile.view.current.path", null);
    }

}
