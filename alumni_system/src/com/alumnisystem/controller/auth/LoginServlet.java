package com.alumnisystem.controller.auth;

import com.alumnisystem.utility.Authorization;
import com.alumnisystem.utility.ResponseHelper;
import com.alumnisystem.utility.RouteHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        if(Authorization.isLogin()) {
            response.sendRedirect(RouteHelper.generateHomeURL());
            return;
        }

        if(Authorization.doLogin(request.getParameter("username"), request.getParameter("password"))) {
            response.sendRedirect(RouteHelper.generateURL(RouteHelper.pullLastPathURL()));
            return;
        } else {
            ResponseHelper.pushRequestCode(ResponseHelper.BAD_LOGIN);
        }

        getServletContext().getRequestDispatcher("/WEB-INF/auth/login.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        if(Authorization.isLogin()) {
            response.sendRedirect(RouteHelper.generateHomeURL());
            return;
        }

        if(ResponseHelper.hasCodeInSession()) {
            ResponseHelper.pushRequestCode(ResponseHelper.pullSessionCode());
        }

        getServletContext().getRequestDispatcher("/WEB-INF/auth/login.jsp").forward(request, response);
    }

}
