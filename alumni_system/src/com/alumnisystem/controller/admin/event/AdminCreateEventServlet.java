package com.alumnisystem.controller.admin.event;

import com.alumnisystem.factory.EventFactory;
import com.alumnisystem.model.Event;
import com.alumnisystem.utility.RouteHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AdminCreateEventServlet", urlPatterns = {"/admin/event/create", "/admin/event/create/"})
public class AdminCreateEventServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        Event event = new Event();

        event.setTitle(request.getParameter("event-form-title"));
        event.setDescription(request.getParameter("event-form-description"));
        event.setDetail(request.getParameter("event-form-detail"));

        event = new EventFactory().create(event);

        response.sendRedirect(RouteHelper.generateURL("news.jsp?event_id=" + event.getId()));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        request.getRequestDispatcher("/WEB-INF/admin/event/formnewevent.jsp").forward(request, response);
    }

}
