package com.alumnisystem.controller.admin.alumni;

import com.alumnisystem.factory.AlumniFactory;
import com.alumnisystem.model.Curriculum;
import com.alumnisystem.utility.ResponseHelper;
import com.alumnisystem.utility.RouteHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by meranote on 4/20/2016 AD.
 */
@WebServlet(name = "AdminViewAlumniServlet", urlPatterns = {"/admin/alumni/*"})
public class AdminViewAlumniServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        response.sendError(ResponseHelper.PAGE_NOT_FOUND);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String uriNoContext = RouteHelper.getURINoContext();
        uriNoContext = uriNoContext.substring("admin/alumni/".length());

        String[] uriSlashSplit = uriNoContext.split("/");
        int uriSlashSplitLength = uriSlashSplit.length;

        if(uriSlashSplitLength == 1) {
            if(Arrays.asList("bachelor", "master", "doctoral").contains(uriSlashSplit[0])) {
                request.setAttribute("degree", uriSlashSplit[0]);
                request.setAttribute("alumnis", new AlumniFactory().getAlumniWithinDegree(Curriculum.Degree.valueOf(uriSlashSplit[0].toUpperCase())));
                request.getRequestDispatcher("/WEB-INF/admin/alumni/viewalumni.jsp").forward(request, response);
                return;
            }
        }

        response.sendError(ResponseHelper.PAGE_NOT_FOUND);
    }
}
