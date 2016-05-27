package com.alumnisystem.controller.admin.profile;

import com.alumnisystem.annotation.AuthGuard;
import com.alumnisystem.exception.UserNotFound;
import com.alumnisystem.factory.UserFactory;
import com.alumnisystem.model.User;
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

@WebServlet(name = "AdminViewProfileServlet", urlPatterns = {"/admin/profile/*"})
@AuthGuard
public class AdminViewProfileServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();

        int flag = ResponseHelper.NO_ERR;

        User user;
        if((user = getUserByPath(request)) == null) {
            flag = ResponseHelper.NO_USER_MODEL_FOUND;
        } else {
            request.setAttribute("user", user);
        }

        User currentUser = Authorization.getCurrentUser();
        if(!currentUser.isAdmin()) {
            flag = ResponseHelper.NOT_ENOUGH_PERMISSION;
        }

        if (flag != ResponseHelper.NO_ERR) {
            ResponseHelper.pushSessionCode(flag);
            response.sendRedirect(RouteHelper.generateURL("admin/profile"));
            return;
        }
        if (ResponseHelper.hasCodeInSession()) ResponseHelper.pushRequestCode(ResponseHelper.pullSessionCode());

        session.setAttribute("profile.view.current.path", RouteHelper.getURINoContext());
        request.getRequestDispatcher("/WEB-INF/admin/profile.jsp").forward(request, response);
    }

    private User getUserByPath(HttpServletRequest request) {
        User user = null;

        if(request.getRequestURI().endsWith("/admin/profile") || request.getRequestURI().endsWith("/admin/profile/") ) {
            user = Authorization.getCurrentUser();
        } else {
            String uri = request.getRequestURI();
            if(!uri.endsWith("/")) uri += "/";
            String[] splits = uri.split("/");

            if(splits[splits.length - 3].equals("admin") && splits[splits.length - 2].equals("profile")) {
                try {
                    user = new UserFactory().find(Integer.parseInt(splits[splits.length - 1]));
                } catch (Exception ex) {
                    try {
                        user = new UserFactory().findByUsername(splits[splits.length - 1]);
                    } catch (UserNotFound ex2) {
                        user = null;
                    }
                }
            }
        }

        return user;
    }

}
