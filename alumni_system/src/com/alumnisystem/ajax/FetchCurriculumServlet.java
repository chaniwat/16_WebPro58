package com.alumnisystem.ajax;

import com.alumnisystem.factory.CurriculumFactory;
import com.alumnisystem.model.Curriculum;
import com.alumnisystem.utility.RouteHelper;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "FetchCurriculumServlet", urlPatterns = {"/ajax/curriculum"})
public class FetchCurriculumServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        if(RouteHelper.isAjaxRequest()) {
            if(request.getParameter("degree") != null) {
                try {
                    Curriculum.Degree degree = Curriculum.Degree.valueOf(request.getParameter("degree"));

                    ArrayList<Curriculum> curricula = new CurriculumFactory().findAllByDegree(degree);

                    response.getWriter().print(new Gson().toJson(curricula));
                    return;
                } catch (IllegalArgumentException ignored) { }
            }

        }

        super.doGet(request, response);
    }

}
