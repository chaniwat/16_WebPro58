package com.alumnisystem.controller.admin.user;

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
import java.io.IOException;

@WebServlet(name = "AdminRemoveUserServlet", urlPatterns = {"/admin/user/remove/*"})
public class AdminRemoveUserServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        updateUserStatus(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        updateUserStatus(request, response);
    }

    private void updateUserStatus(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

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

        if (flag == ResponseHelper.NO_USER_MODEL_FOUND || flag == ResponseHelper.NOT_ENOUGH_PERMISSION) {
            ResponseHelper.pushSessionCode(flag);
            response.sendRedirect(RouteHelper.generateURL("admin/profile"));
            return;
        }
        if (ResponseHelper.hasCodeInSession()) ResponseHelper.pushRequestCode(ResponseHelper.pullSessionCode());

        new UserFactory().remove(user.getId());

        if(currentUser.getId() == user.getId()) {
            Authorization.doLogout();
            response.sendRedirect(RouteHelper.generateHomeURL());
            return;
        }

        ResponseHelper.pushSessionCode(ResponseHelper.REMOVE_USER_COMPLETE);
        response.sendRedirect(RouteHelper.generateURL("admin/profile"));
    }

    private User getUserByPath(HttpServletRequest request) {
        User user = null;

        if (request.getRequestURI().endsWith("admin/user/remove") || request.getRequestURI().endsWith("admin/user/remove/")) {
            user = Authorization.getCurrentUser();
        } else {
            String uri = request.getRequestURI();
            if (!uri.endsWith("/")) uri += "/";
            String[] splits = uri.split("/");

            if (splits[splits.length - 4].equals("admin") && splits[splits.length - 3].equals("user") && splits[splits.length - 2].equals("remove")) {
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
