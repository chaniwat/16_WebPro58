package com.alumnisystem.ajax;

import com.alumnisystem.factory.CurriculumFactory;
import com.alumnisystem.factory.TrackFactory;
import com.alumnisystem.model.Curriculum;
import com.alumnisystem.model.Track;
import com.alumnisystem.utility.RouteHelper;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "FetchTrackServlet", urlPatterns = {"/ajax/track"})
public class FetchTrackServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        if(RouteHelper.isAjaxRequest()) {
            if(request.getParameter("curriculum_id") != null) {
                try {
                    ArrayList<Track> tracks = new TrackFactory().findAllByCurriculumID(Integer.parseInt(request.getParameter("curriculum_id")));

                    response.getWriter().print(new Gson().toJson(tracks));
                    return;
                } catch (IllegalArgumentException ignored) { }
            }
        }

        super.doGet(request, response);
    }

}
