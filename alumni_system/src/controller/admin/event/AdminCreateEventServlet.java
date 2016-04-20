package controller.admin.event;

import model.utility.ResponseCodeUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by meranote on 4/20/2016 AD.
 */
@WebServlet(name = "AdminCreateEventServlet", urlPatterns = {"/admin/event/create", "/admin/event/create/"})
public class AdminCreateEventServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        response.sendError(ResponseCodeUtils.PAGE_NOT_FOUND);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        request.getRequestDispatcher("/WEB-INF/admin/event/formnewevent.jsp").forward(request, response);
    }
}
