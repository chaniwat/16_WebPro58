package com.alumnisystem.controller;

import com.alumnisystem.annotation.AuthGuard;
import com.alumnisystem.database.Database;
import com.alumnisystem.exception.UserNotFound;
import com.alumnisystem.factory.UserFactory;
import com.alumnisystem.model.User;
import com.alumnisystem.utility.Authorization;
import com.alumnisystem.utility.ResponseCodeUtils;
import com.alumnisystem.utility.RouteUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "ViewProfileServlet", urlPatterns = {"/profile/*"})
@AuthGuard
public class ViewProfileServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        if(!request.getRequestURI().endsWith("/profile") && !request.getRequestURI().endsWith("/profile/") ) {
            if (ResponseCodeUtils.hasCodeInSession(session) && ResponseCodeUtils.getSessionCode(session) == ResponseCodeUtils.NOT_ENOUGH_PERMISSION) {
                response.sendRedirect(RouteUtils.generateURL(request, "profile"));
                return;
            }
        }

        User user;
        if((user = getUserByPath(request)) == null) {
            ResponseCodeUtils.pushSessionCode(session, ResponseCodeUtils.NO_USER_MODEL_FOUND);
            response.sendRedirect(RouteUtils.generateURL(request, "profile"));
            return;
        } else request.setAttribute("user", user);

        if (ResponseCodeUtils.hasCodeInSession(session)) ResponseCodeUtils.pushRequestCode(request, ResponseCodeUtils.pullSessionCode(session));

        session.setAttribute("profile.view.current.path", RouteUtils.getURINoContext(request));
        request.getRequestDispatcher("/WEB-INF/profile.jsp").forward(request, response);
    }

    private User getUserByPath(HttpServletRequest request) {
        Authorization auth = Authorization.getAuthInstance(request);
        User user = null;

        if(request.getRequestURI().endsWith("/profile") || request.getRequestURI().endsWith("/profile/") ) {
            user = auth.getCurrentUser();
        } else {
            String uri = request.getRequestURI();
            if(!uri.endsWith("/")) uri += "/";
            String[] splits = uri.split("/");

            if(splits[splits.length - 2].equals("profile")) {
                try {
                    user = new UserFactory(Database.getConnection(request)).find(Integer.parseInt(splits[splits.length - 1]));
                } catch (Exception ex) {
                    try {
                        user = new UserFactory(Database.getConnection(request)).findByUsername(splits[splits.length - 1]);
                    } catch (UserNotFound ex2) {
                        user = null;
                    }
                }
            }
        }

        return user;
    }

}