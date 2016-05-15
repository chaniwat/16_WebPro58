package com.alumnisystem.controller;

import com.alumnisystem.annotation.AuthGuard;
import com.alumnisystem.factory.*;
import com.alumnisystem.model.*;
import com.alumnisystem.utility.Authorization;
import com.alumnisystem.utility.ResponseHelper;
import com.alumnisystem.utility.RouteHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

@WebServlet(name = "EditProfileServlet", urlPatterns = {"/profile/edit"})
@AuthGuard(redirectback = false)
public class EditProfileServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        User.Type type = User.Type.valueOf(request.getParameter("profilepage-usertype"));
        switch (type) {
            case ALUMNI: doUpdateAlumni(request, response); return;
            case TEACHER: doUpdateTeacher(request, response); return;
            case STAFF: doUpdateStaff(request, response); return;
        }
    }

    private void doUpdateStaff(HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, String> params = RouteHelper.convertParamsToHashMap("staff-form-");

        // Check permission (prevent another alumni or teacher to edit staff's profile)\
        User user = Authorization.getCurrentUser();

        if(params.get("staff-form-id") == null) {
            redirectToProfilePage(request, response, ResponseHelper.FORM_INPUT_NOT_COMPLETE, true);
            return;
        }
        int staff_id = Integer.parseInt(params.get("staff-form-id"));

        Staff self = null;
        if(user.getType() == User.Type.TEACHER) self = new StaffFactory().findByUserId(user.getId());

        if(!((self != null && staff_id != self.getStaff_id()) || !Authorization.getCurrentUser().isAdmin())) {
            redirectToProfilePage(request, response, ResponseHelper.NOT_ENOUGH_PERMISSION, true);
            return;
        }
        // End Check Permission

        if(params.get("staff-form-pnameth") == null ||
                params.get("staff-form-fnameth") == null || params.get("staff-form-lnameth") == null) {
            redirectToProfilePage(request, response, ResponseHelper.FORM_INPUT_NOT_COMPLETE, true);
            return;
        }

        StaffFactory staffFactory = new StaffFactory();

        Staff staff = new Staff();
        staff.setStaff_id(staff_id);
        staff.setId(staffFactory.find(staff_id).getId());
        staff.setPname_th(params.get("staff-form-pnameth"));
        staff.setFname_th(params.get("staff-form-fnameth"));
        staff.setLname_th(params.get("staff-form-lnameth"));
        staff.setPname_en(params.get("staff-form-pnameen"));
        staff.setFname_en(params.get("staff-form-fnameen"));
        staff.setLname_en(params.get("staff-form-lnameen"));
        staff.setEmail(params.get("staff-form-email"));
        staff.setPhone(params.get("staff-form-phone"));
        staff.setSection(new WorkSectionFactory().find(Integer.parseInt(params.get("staff-form-worksection"))));

        staffFactory.update(staff);

        redirectToProfilePage(request, response, ResponseHelper.PROFILE_UPDATED_COMPLETE);
    }

    private void doUpdateTeacher(HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, String> params = RouteHelper.convertParamsToHashMap("teacher-form-");

        // Check permission (prevent another alumni or teacher to edit another teacher's profile)
        User user = Authorization.getCurrentUser();

        if(params.get("teacher-form-id") == null) {
            redirectToProfilePage(request, response, ResponseHelper.FORM_INPUT_NOT_COMPLETE, true);
            return;
        }
        int teacher_id = Integer.parseInt(params.get("teacher-form-id"));

        Teacher self = null;
        if(user.getType() == User.Type.TEACHER) self = new TeacherFactory().findByUserId(user.getId());

        if(!((self != null && teacher_id != self.getTeacher_id()) || !user.isAdmin())) {
            redirectToProfilePage(request, response, ResponseHelper.NOT_ENOUGH_PERMISSION, true);
            return;
        }
        // End Check Permission

        if(params.get("teacher-form-pnameth") == null ||
                params.get("teacher-form-fnameth") == null || params.get("teacher-form-lnameth") == null) {
            redirectToProfilePage(request, response, ResponseHelper.FORM_INPUT_NOT_COMPLETE, true);
            return;
        }

        TeacherFactory teacherFactory = new TeacherFactory();

        Teacher teacher = new Teacher();
        teacher.setTeacher_id(teacher_id);
        teacher.setId(teacherFactory.find(teacher_id).getId());
        teacher.setPname_th(params.get("teacher-form-pnameth"));
        teacher.setFname_th(params.get("teacher-form-fnameth"));
        teacher.setLname_th(params.get("teacher-form-lnameth"));
        teacher.setPname_en(params.get("teacher-form-pnameen"));
        teacher.setFname_en(params.get("teacher-form-fnameen"));
        teacher.setLname_en(params.get("teacher-form-lnameen"));
        teacher.setEmail(params.get("teacher-form-email"));
        teacher.setPhone(params.get("teacher-form-phone"));
        teacher.setWork_status(Work.Status.valueOf(params.get("teacher-form-workstatus")));

        teacherFactory.update(teacher);

        redirectToProfilePage(request, response, ResponseHelper.PROFILE_UPDATED_COMPLETE);
    }

    private void doUpdateAlumni(HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, String> params = RouteHelper.convertParamsToHashMap("alumni-form-");

        // Check permission (prevent another alumni or teacher to edit another alumni's profile)
        User user = Authorization.getCurrentUser();

        if(params.get("alumni-form-id") == null) {
            redirectToProfilePage(request, response, ResponseHelper.FORM_INPUT_NOT_COMPLETE, true);
            return;
        }

        int alumni_id = Integer.parseInt(params.get("alumni-form-id"));

        Alumni self = null;
        if(user.getType() == User.Type.ALUMNI) self = new AlumniFactory().findByUserId(user.getId());

        if(!((self != null && alumni_id == self.getAlumni_id()) || user.isAdmin())) {
            redirectToProfilePage(request, response, ResponseHelper.NOT_ENOUGH_PERMISSION, true);
            return;
        }
        // End Check Permission

        if(params.get("alumni-form-pnameth") == null ||
                params.get("alumni-form-fnameth") == null || params.get("alumni-form-lnameth") == null) {
            redirectToProfilePage(request, response, ResponseHelper.FORM_INPUT_NOT_COMPLETE, true);
            return;
        }

        AlumniFactory alumniFactory = new AlumniFactory();

        Alumni alumni = new Alumni();
        alumni.setAlumni_id(alumni_id);
        alumni.setId(alumniFactory.find(alumni_id).getId());
        alumni.setPname_th(params.get("alumni-form-pnameth"));
        alumni.setFname_th(params.get("alumni-form-fnameth"));
        alumni.setLname_th(params.get("alumni-form-lnameth"));
        alumni.setPname_en(params.get("alumni-form-pnameen"));
        alumni.setFname_en(params.get("alumni-form-fnameen"));
        alumni.setLname_en(params.get("alumni-form-lnameen"));
        alumni.setNickname(params.get("alumni-form-nickname"));

        if(!params.get("alumni-form-birthdate-year").equals("null") &&
                !params.get("alumni-form-birthdate-month").equals("null") &&
                !params.get("alumni-form-birthdate-day").equals("null")) {

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

        // FIXME set job
        alumni.setJob(null);

        alumni.setWork_name(params.get("alumni-form-workname"));

        alumni.setAddress(params.get("alumni-form-address"));
        alumni.setDistrict(params.get("alumni-form-district"));
        alumni.setAmphure(params.get("alumni-form-amphure"));
        alumni.setProvince(params.get("alumni-form-province"));
        alumni.setZipcode(params.get("alumni-form-zipcode"));

        alumniFactory.update(alumni);

        redirectToProfilePage(request, response, ResponseHelper.PROFILE_UPDATED_COMPLETE);
    }

    private void redirectToProfilePage(HttpServletRequest request, HttpServletResponse response, int ErrorNumber) {
        redirectToProfilePage(request, response, ErrorNumber, false);
    }

    private void redirectToProfilePage(HttpServletRequest request, HttpServletResponse response, int ErrorNumber, boolean toSelf) {
        try {
            ResponseHelper.pushSessionCode(ErrorNumber);
            if(toSelf) response.sendRedirect(RouteHelper.generateURL("profile"));
            else response.sendRedirect(RouteHelper.generateURL((String) request.getSession().getAttribute("profile.view.current.path")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        request.getSession().setAttribute("profile.view.current.path", null);
    }

}
