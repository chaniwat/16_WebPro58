package com.alumnisystem.controller;

import com.alumnisystem.factory.AlumniFactory;
import com.alumnisystem.model.Alumni;
import com.alumnisystem.model.Curriculum;
import com.alumnisystem.utility.ResponseHelper;
import com.alumnisystem.utility.RouteHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

@WebServlet(name = "ViewAlumniServlet", urlPatterns = {"/alumni/*"})
public class ViewAlumniServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String uriNoContext = RouteHelper.getURINoContext();
        uriNoContext = uriNoContext.substring("alumni/".length());

        String[] uriSlashSplit = uriNoContext.split("/");
        int uriSlashSplitLength = uriSlashSplit.length;

        if(uriSlashSplitLength == 1 && uriNoContext.equals("")) {
            request.getRequestDispatcher("/WEB-INF/alumni/selectdegree.jsp").forward(request, response);
            return;
        } else if(uriSlashSplitLength == 1) {
            if(Arrays.asList("bachelor", "master", "doctoral").contains(uriSlashSplit[0])) {
                request.setAttribute("degree", uriSlashSplit[0]);
                request.setAttribute("generations", new AlumniFactory().getGenerationList(Curriculum.Degree.valueOf(uriSlashSplit[0].toUpperCase())));
                request.getRequestDispatcher("/WEB-INF/alumni/selectgeneration.jsp").forward(request, response);
                return;
            }
        } else if(uriSlashSplitLength == 2) {
            if(Arrays.asList("bachelor", "master", "doctoral").contains(uriSlashSplit[0])) {
                if(uriSlashSplit[1].equals("all")) {
                    ArrayList<Alumni> alumnis = new AlumniFactory().getAlumniWithinDegree(Curriculum.Degree.valueOf(uriSlashSplit[0].toUpperCase()));
                    request.setAttribute("alumnis", alumnis);
                    request.setAttribute("degree", uriSlashSplit[0]);
                    request.getRequestDispatcher("/WEB-INF/alumni/showalumni.jsp").forward(request, response);
                    return;
                } else {
                    try {
                        int generation = Integer.parseInt(uriSlashSplit[1]);
                        ArrayList<Alumni> alumnis = new AlumniFactory().getAlumniWithinDegreeAndGeneration(Curriculum.Degree.valueOf(uriSlashSplit[0].toUpperCase()), generation);
                        request.setAttribute("alumnis", alumnis);
                        request.setAttribute("degree", uriSlashSplit[0]);
                        request.setAttribute("generation", generation);
                        request.getRequestDispatcher("/WEB-INF/alumni/showalumni.jsp").forward(request, response);
                        return;
                    } catch (NumberFormatException ex) { }
                }
            }
        }

        response.sendError(ResponseHelper.PAGE_NOT_FOUND);
    }

}
