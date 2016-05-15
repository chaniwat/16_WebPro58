package com.alumnisystem.controller.admin.survey;

import com.alumnisystem.annotation.AuthGuard;
import com.alumnisystem.utility.ResponseHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by meranote on 4/20/2016 AD.
 */
@WebServlet(name = "FormBuilderServlet", urlPatterns = {"/admin/survey/create", "/admin/survey/create/"})
@AuthGuard
public class FormBuilderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        response.sendError(ResponseHelper.PAGE_NOT_FOUND);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        request.getRequestDispatcher("/WEB-INF/admin/survey/formbuilder.jsp").forward(request, response);
    }
}
