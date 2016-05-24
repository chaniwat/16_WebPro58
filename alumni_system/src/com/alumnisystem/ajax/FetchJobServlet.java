package com.alumnisystem.ajax;

import com.alumnisystem.factory.JobFactory;
import com.alumnisystem.model.Job;
import com.alumnisystem.utility.RouteHelper;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "FetchJobServlet", urlPatterns = {"/ajax/job"})
public class FetchJobServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        if(RouteHelper.isAjaxRequest()) {
            if(request.getParameter("jobtype_id") != null) {
                if(request.getParameter("jobtype_id").equals("0")) return;

                ArrayList<Job> jobs = new JobFactory().findAllByJobTypeID(Integer.parseInt(request.getParameter("jobtype_id")));

                response.getWriter().print(new Gson().toJson(jobs));
            }


        } else {
            super.doGet(request, response);
        }
    }

}
