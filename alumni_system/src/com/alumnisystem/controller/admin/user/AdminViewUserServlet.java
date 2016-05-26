package com.alumnisystem.controller.admin.user;

import com.alumnisystem.factory.StaffFactory;
import com.alumnisystem.factory.UserFactory;
import com.alumnisystem.model.User;
import com.alumnisystem.utility.ResponseHelper;
import com.alumnisystem.utility.RouteHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@WebServlet(name = "AdminViewUserServlet", urlPatterns = {"/admin/user/*"})
public class AdminViewUserServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String uriNoContext = RouteHelper.getURINoContext();
        uriNoContext = uriNoContext.substring("admin/user/".length());

        String[] uriSlashSplit = uriNoContext.split("/");
        int uriSlashSplitLength = uriSlashSplit.length;

        if(uriSlashSplitLength == 1) {
            if(Arrays.asList("alumni", "teacher", "staff").contains(uriSlashSplit[0])) {
                request.setAttribute("type", uriSlashSplit[0]);
                request.getRequestDispatcher("/WEB-INF/admin/user/viewuser.jsp").forward(request, response);
                return;
            }
        }

        response.sendError(ResponseHelper.PAGE_NOT_FOUND);
    }

}
