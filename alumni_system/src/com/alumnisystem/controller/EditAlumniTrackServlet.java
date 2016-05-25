package com.alumnisystem.controller;

import com.alumnisystem.annotation.AuthGuard;
import com.alumnisystem.factory.AlumniFactory;
import com.alumnisystem.model.Alumni;
import com.alumnisystem.model.Curriculum;
import com.alumnisystem.model.Track;
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
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "EditAlumniTrackServlet", urlPatterns = {"/track/edit/*"})
@AuthGuard
public class EditAlumniTrackServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        if(!(request.getRequestURI().endsWith("/track/edit") || request.getRequestURI().endsWith("/track/edit"))) {
            response.sendError(ResponseHelper.PAGE_NOT_FOUND);
            return;
        }

        if(!isEditable(request)) {
            ResponseHelper.pushSessionCode(ResponseHelper.NOT_ENOUGH_PERMISSION);
            response.sendRedirect(RouteHelper.generateURL("profile"));
            return;
        }

        HashMap<String, String> params = RouteHelper.convertParamsToHashMap("");
        Alumni alumni = new AlumniFactory().findByStudentId(Integer.parseInt(params.get("student_id")));

        if(alumni == null) {
            ResponseHelper.pushSessionCode(ResponseHelper.NO_USER_MODEL_FOUND);
            response.sendRedirect(RouteHelper.generateURL("profile"));
            return;
        }

        Curriculum.Degree degree = Curriculum.Degree.valueOf(params.get("degree").toUpperCase());
        for(Track track : alumni.getTracks()) {
            if(track.getCurriculum().getDegree() == degree) {
                new AlumniFactory().updateAlumniTrackID(track, Integer.parseInt(params.get("trackid")));
                break;
            }
        }

        ResponseHelper.pushSessionCode(ResponseHelper.ALUMNI_TRACK_UPDATED_COMPLETE);
        response.sendRedirect(RouteHelper.generateURL((String) request.getSession().getAttribute("alumnitrack.view.current.path")));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        if(!isEditable(request)) {
            ResponseHelper.pushSessionCode(ResponseHelper.NOT_ENOUGH_PERMISSION);
            response.sendRedirect(RouteHelper.generateURL("profile"));
            return;
        }

        Alumni alumni = getAlumniByPath(request);

        if(alumni == null) {
            ResponseHelper.pushSessionCode(ResponseHelper.NO_USER_MODEL_FOUND);
            response.sendRedirect(RouteHelper.generateURL("profile"));
            return;
        }

        request.setAttribute("tracks", alumni.getTracks());

        if(ResponseHelper.hasCodeInSession()) {
            ResponseHelper.pushRequestCode(ResponseHelper.pullSessionCode());
        }
        request.getSession().setAttribute("alumnitrack.view.current.path", RouteHelper.getURINoContext());
        request.getRequestDispatcher("/WEB-INF/alumni/edittrack.jsp").forward(request, response);
    }

    private Alumni getAlumniByPath(HttpServletRequest request) {
        Alumni alumni = null;
        AlumniFactory alumniFactory = new AlumniFactory();

        try {
            if(request.getRequestURI().endsWith("/track/edit") || request.getRequestURI().endsWith("/track/edit/") ) {
                alumni = alumniFactory.findByUserId(Authorization.getCurrentUser().getId());
            } else {
                String uri = request.getRequestURI();
                if(!uri.endsWith("/")) uri += "/";
                String[] splits = uri.split("/");

                if(splits[splits.length - 3].equals("track") && splits[splits.length - 2].equals("edit")) {
                        alumni = alumniFactory.find(Integer.parseInt(splits[splits.length - 1]));
                }
            }
        } catch (Exception ignored) { }

        return alumni;
    }

    private boolean isEditable(HttpServletRequest request) {
        User currentUser = Authorization.getCurrentUser();
        if(!currentUser.isAdmin()) {
            if((request.getRequestURI().endsWith("/track/edit") || request.getRequestURI().endsWith("/track/edit")) && currentUser.getType() != User.Type.ALUMNI) {
                return false;
            }
        }
        return true;
    }

}
