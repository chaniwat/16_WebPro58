package com.alumnisystem.controller;

import com.alumnisystem.utility.ResponseHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "EditAlumniTrackServlet", urlPatterns = {"/track/edit"})
public class EditAlumniTrackServlet extends HttpServlet {

    // TODO edit alumni track page and form

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        response.sendError(ResponseHelper.PAGE_NOT_FOUND);
        return;
    }

}
