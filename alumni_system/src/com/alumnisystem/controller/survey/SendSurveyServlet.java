package com.alumnisystem.controller.survey;

import com.alumnisystem.annotation.AuthGuard;
import com.alumnisystem.factory.SurveyFactory;
import com.alumnisystem.model.Survey;
import com.alumnisystem.utility.RouteHelper;
import com.alumnisystem.utility.database.Database;
import com.alumnisystem.utility.database.SuperStatement;
import com.mysql.jdbc.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@WebServlet(name = "SendSurveyServlet", urlPatterns = {"/survey/send"})
@AuthGuard
public class SendSurveyServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        int survey_id = Integer.parseInt(request.getParameter("surveytarget"));
        Survey survey = new SurveyFactory().find(survey_id);

        Connection connection = Database.getConnection();
        SuperStatement statement = new SuperStatement(connection);
        ResultSet result;
        try {
            //language=MySQL
            String sql = "INSERT INTO `" + survey.schemafile + "`(id) VALUES (0)";
            statement.setStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.executeUpdate();

            result = statement.getStatement().getGeneratedKeys();
            int datakey = 0;
            if(result.next()) datakey = result.getInt(1);

            sql = "UPDATE `" + survey.schemafile + "` SET ";
            int i = 0;
            for(Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
                if(entry.getKey().equals("surveytarget")) continue;
                if(i++ > 0) sql += ", ";
                sql += "`" + entry.getKey() + "` = '" + entry.getValue()[0] + "' ";
            }
            sql += "WHERE id = " + datakey;

            statement.setStatement(sql);
            statement.executeUpdate();

            response.getWriter().println("<h1>Insert Complete!</h1>");
            response.getWriter().println("<h2>Thanks for doing the survey :D</h2>");
            response.getWriter().println("<a href='" + RouteHelper.generateHomeURL() + "'>Go to index</a>");
        } catch (SQLException ex) {
            ex.printStackTrace();
            return;
        }
    }

}
