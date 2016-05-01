package com.alumnisystem.controller.auth;

import com.alumnisystem.utility.Authorization;
import com.alumnisystem.utility.RouteUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by meranote on 2/10/2016 AD.
 */
@WebServlet(name = "LogoutServlet", urlPatterns = "/logout")
public class LogoutServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        Authorization authorization = Authorization.getAuthInstance(request);

        if(authorization.isLogin()) {
            authorization.doLogout();
        }

        response.sendRedirect(RouteUtils.generateHomeURL(request));
    }
}
