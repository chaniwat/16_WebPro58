package controller;

import model.utility.ResponseCodeUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by meranote on 4/11/2016 AD.
 */
@WebServlet(name = "ViewAllAlumniServlet", urlPatterns = {"/alumni/all"})
public class ViewAllAlumniServlet extends HttpServlet {

    // TODO view all alumni


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        response.sendError(ResponseCodeUtils.PAGE_NOT_FOUND);
        return;
    }

}
