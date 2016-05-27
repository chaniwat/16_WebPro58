package com.alumnisystem.controller.survey;

import com.alumnisystem.annotation.AuthGuard;
import com.alumnisystem.factory.SurveyFactory;
import com.alumnisystem.model.Survey;
import com.alumnisystem.utility.ResponseHelper;
import com.alumnisystem.utility.RouteHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ViewSurveyServlet", urlPatterns = {"/survey/*"})
@AuthGuard
public class ViewSurveyServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        int flag = ResponseHelper.NO_ERR;

        Survey survey;
        if((survey = getSurveyByPath(request)) == null) {
            flag = ResponseHelper.NO_USER_MODEL_FOUND;
        } else {
            request.setAttribute("survey", survey);
        }

        if (flag != ResponseHelper.NO_ERR) {
            ResponseHelper.pushSessionCode(flag);
            response.sendRedirect(RouteHelper.generateURL(""));
            return;
        }

        request.getRequestDispatcher("/WEB-INF/survey.jsp").forward(request, response);
    }

    private Survey getSurveyByPath(HttpServletRequest request) {
        Survey survey = null;

        if(request.getRequestURI().endsWith("/survey") || request.getRequestURI().endsWith("/survey/") ) {
            return null;
        } else {
            String uri = request.getRequestURI();
            if(!uri.endsWith("/")) uri += "/";
            String[] splits = uri.split("/");

            if(splits[splits.length - 2].equals("survey")) {
                try {
                    survey = new SurveyFactory().find(Integer.parseInt(splits[splits.length - 1]));
                } catch (Exception ex) {
                    try {
                        survey = new SurveyFactory().findBySchema(splits[splits.length - 1]);
                    } catch (Exception ex2) {
                        survey = null;
                    }
                }
            }
        }

        return survey;
    }

}
