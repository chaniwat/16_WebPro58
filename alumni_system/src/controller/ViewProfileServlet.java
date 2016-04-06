package controller;

import annotation.auth.AuthGuard;
import model.Alumni;
import model.Staff;
import model.Teacher;
import model.User;
import model.auth.Authorization;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by meranote on 4/6/2016 AD.
 */
@WebServlet(name = "ViewProfileServlet", urlPatterns = {"/profile/*"})
@AuthGuard
public class ViewProfileServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        Authorization auth = Authorization.getAuthInstance(session);

        User user = null;

        if(request.getRequestURI().endsWith("/profile")) {
            user = auth.getCurrentUser();
        } else {
            // TODO get user by end of uri username or user_id
            user = auth.getCurrentUser();
        }

        request.setAttribute("user", user);
        switch (user.getType()) {
            case ALUMNI: request.setAttribute("user_detail", Alumni.getAlumniByUserId(user.getId())); break;
//            case TEACHER: request.setAttribute("user_detail", Teacher.getTeacherByUserId(user.getId())); break;
//            case STAFF: request.setAttribute("user_detail", Staff.getStaffByUserId(user.getId())); break;
        }

        request.getRequestDispatcher("/WEB-INF/profile.jsp").forward(request, response);
    }
}
