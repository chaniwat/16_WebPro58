package com.alumnisystem.controller.admin.survey;

import com.alumnisystem.annotation.AuthGuard;
import com.alumnisystem.factory.SurveyFactory;
import com.alumnisystem.model.Survey;
import com.alumnisystem.utility.FileHelper;
import com.alumnisystem.utility.ResponseHelper;
import com.alumnisystem.utility.database.Database;
import com.alumnisystem.utility.database.SuperStatement;
import com.mysql.jdbc.Statement;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@WebServlet(name = "FormBuilderServlet", urlPatterns = {"/admin/survey/create", "/admin/survey/create/"})
@AuthGuard
public class FormBuilderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        Document doc = Jsoup.parseBodyFragment(request.getParameter("survey-schema"));
        Elements elements = doc.select("input[type=text]");

        ArrayList<String> columnTextList = new ArrayList<>();
        for (Element element : elements) {
            columnTextList.add(element.id());
        }

        elements = doc.select("textarea");
        for (Element element : elements) {
            columnTextList.add(element.id());
        }

        String schemaname = new SurveyFactory().generateSchemaString(request.getParameter("survey-form-title"));

        Survey survey = new Survey();
        survey.name = request.getParameter("survey-form-title");
        survey.description = request.getParameter("survey-form-description");
        survey.schemafile = schemaname;

        Connection connection = Database.getConnection();
        SuperStatement statement = new SuperStatement(connection);

        if(columnTextList.size() <= 0) {
            response.getWriter().print("<h1>NO INPUT INSERT</h1>");
            return;
        }

        //language=MySQL
        String sql = "INSERT INTO survey VALUES (0, ?, ?, CURRENT_TIMESTAMP, 0, ?)";
        try {
            statement.setStatement(sql, Statement.RETURN_GENERATED_KEYS)
                    .setString(survey.name)
                    .setString(survey.description)
                    .setString(survey.schemafile);

            statement.executeUpdate();

            ResultSet resultSet = statement.getStatement().getGeneratedKeys();

            if(resultSet.next()) survey.id = resultSet.getInt(1);

            sql = "CREATE TABLE `" + survey.schemafile + "` (id INT NOT NULL PRIMARY KEY AUTO_INCREMENT";
            for(String elemId : columnTextList) {
                sql += ", `" + elemId + "`" + " TEXT";
            }
            sql += ")";

            System.out.println(sql);

            statement.setStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        FileHelper.saveSurveySchema(schemaname, request.getParameter("survey-schema"));

        request.setAttribute("schema", schemaname);
        ResponseHelper.pushRequestCode(ResponseHelper.ADD_NEW_SURVEY_COMPLETE);
        request.getRequestDispatcher("/WEB-INF/admin/survey/formbuilder.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        request.getRequestDispatcher("/WEB-INF/admin/survey/formbuilder.jsp").forward(request, response);
    }

}
