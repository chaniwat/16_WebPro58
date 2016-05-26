package com.alumnisystem.controller.admin.event;

import com.alumnisystem.utility.ResponseHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AdminShowAllEventServlet", urlPatterns = {"/admin/event/*"})
public class AdminShowAllEventServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        try {
            if(request.getRequestURI().endsWith("/admin/event/all") || request.getRequestURI().endsWith("/admin/event/all/") ) {
                request.getRequestDispatcher("/WEB-INF/admin/event/viewallevent.jsp").forward(request, response);
                return;
            } else {
//                String uri = request.getRequestURI();
//                if(!uri.endsWith("/")) uri += "/";
//                String[] splits = uri.split("/");
//
//                if(splits[splits.length - 4].equals("admin") && splits[splits.length - 3].equals("track") && splits[splits.length - 2].equals("edit")) {
//                    alumni = alumniFactory.find(Integer.parseInt(splits[splits.length - 1]));
//                }
            }
        } catch (Exception ignored) { }

        response.sendError(ResponseHelper.PAGE_NOT_FOUND);
    }

}
