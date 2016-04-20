package controller.admin;

import annotation.auth.AuthGuard;
import model.User;
import model.auth.Authorization;
import model.utility.ResponseCodeUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by meranote on 4/11/2016 AD.
 */
@WebServlet(name = "AdminHomeServlet", urlPatterns = {"/admin", "/admin/"})
@AuthGuard
public class AdminHomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        request.getRequestDispatcher("/WEB-INF/admin/homepage.jsp").forward(request, response);
    }

}
