package com.alumnisystem.controller.admin.user;

import com.alumnisystem.factory.StaffFactory;
import com.alumnisystem.factory.TeacherFactory;
import com.alumnisystem.factory.UserFactory;
import com.alumnisystem.factory.WorkSectionFactory;
import com.alumnisystem.model.Staff;
import com.alumnisystem.model.Teacher;
import com.alumnisystem.model.User;
import com.alumnisystem.model.Work;
import com.alumnisystem.utility.Authorization;
import com.alumnisystem.utility.ResponseHelper;
import com.alumnisystem.utility.RouteHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@WebServlet(name = "AdminAddNewUserServlet", urlPatterns = {"/admin/user/add"})
public class AdminAddNewUserServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        User.Type type = User.Type.valueOf(request.getParameter("usertype"));
        switch (type) {
            case TEACHER: doUpdateTeacher(request, response); return;
            case STAFF: doUpdateStaff(request, response); return;
        }
    }

    private void doUpdateStaff(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HashMap<String, String> params = RouteHelper.convertParamsToHashMap("staff-form-");

        if(params.get("staff-form-username") == null || params.get("staff-form-password") == null ||
                params.get("staff-form-copassword") == null || params.get("staff-form-pnameth") == null ||
                params.get("staff-form-fnameth") == null || params.get("staff-form-lnameth") == null) {
            ResponseHelper.pushSessionCode(ResponseHelper.FORM_INPUT_NOT_COMPLETE);
            response.sendRedirect(RouteHelper.generateURL("admin/user/add"));
            return;
        }

        if(!params.get("staff-form-password").equals(params.get("staff-form-copassword"))) {
            ResponseHelper.pushSessionCode(ResponseHelper.FORM_PASSWORD_NOT_MATCH);
            response.sendRedirect(RouteHelper.generateURL("admin/user/add"));
            return;
        }

        StaffFactory staffFactory = new StaffFactory();

        Staff staff = new Staff();
        staff.setPname_th(params.get("staff-form-pnameth"));
        staff.setFname_th(params.get("staff-form-fnameth"));
        staff.setLname_th(params.get("staff-form-lnameth"));
        staff.setPname_en(params.get("staff-form-pnameen"));
        staff.setFname_en(params.get("staff-form-fnameen"));
        staff.setLname_en(params.get("staff-form-lnameen"));
        staff.setEmail(params.get("staff-form-email"));
        staff.setPhone(params.get("staff-form-phone"));
        staff.setSection(new WorkSectionFactory().find(Integer.parseInt(params.get("staff-form-worksection"))));

        ArrayList<String> usernames = new ArrayList<>();
        usernames.add(params.get("staff-form-username"));
        staff.setUsernames(usernames);

        staffFactory.create(staff);

        new UserFactory().changeUserPassword(staff.getId(), params.get("staff-form-password"));

        ResponseHelper.pushSessionCode(ResponseHelper.ADD_NEW_STAFF_COMPLETE);
        response.sendRedirect(RouteHelper.generateURL("admin/user/add"));
    }

    private void doUpdateTeacher(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HashMap<String, String> params = RouteHelper.convertParamsToHashMap("teacher-form-");


        if(params.get("teacher-form-username") == null || params.get("teacher-form-password") == null ||
                params.get("teacher-form-copassword") == null || params.get("teacher-form-pnameth") == null ||
                params.get("teacher-form-fnameth") == null || params.get("teacher-form-lnameth") == null) {
            ResponseHelper.pushSessionCode(ResponseHelper.FORM_INPUT_NOT_COMPLETE);
            response.sendRedirect(RouteHelper.generateURL("admin/user/add"));
            return;
        }

        if(!params.get("teacher-form-password").equals(params.get("teacher-form-copassword"))) {
            ResponseHelper.pushSessionCode(ResponseHelper.FORM_PASSWORD_NOT_MATCH);
            response.sendRedirect(RouteHelper.generateURL("admin/user/add"));
            return;
        }

        TeacherFactory teacherFactory = new TeacherFactory();

        Teacher teacher = new Teacher();
        teacher.setPname_th(params.get("teacher-form-pnameth"));
        teacher.setFname_th(params.get("teacher-form-fnameth"));
        teacher.setLname_th(params.get("teacher-form-lnameth"));
        teacher.setPname_en(params.get("teacher-form-pnameen"));
        teacher.setFname_en(params.get("teacher-form-fnameen"));
        teacher.setLname_en(params.get("teacher-form-lnameen"));
        teacher.setEmail(params.get("teacher-form-email"));
        teacher.setPhone(params.get("teacher-form-phone"));
        teacher.setWork_status(Work.Status.valueOf(params.get("teacher-form-workstatus")));

        ArrayList<String> usernames = new ArrayList<>();
        usernames.add(params.get("teacher-form-username"));
        teacher.setUsernames(usernames);

        teacherFactory.create(teacher);

        new UserFactory().changeUserPassword(teacher.getId(), params.get("teacher-form-password"));

        ResponseHelper.pushSessionCode(ResponseHelper.ADD_NEW_TEACHER_COMPLETE);
        response.sendRedirect(RouteHelper.generateURL("admin/user/add"));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        if (ResponseHelper.hasCodeInSession()) ResponseHelper.pushRequestCode(ResponseHelper.pullSessionCode());

        request.getRequestDispatcher("/WEB-INF/admin/user/formnewuser.jsp").forward(request, response);
    }

}
