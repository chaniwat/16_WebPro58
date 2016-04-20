package controller.admin.alumni;

import model.Alumni;
import model.Curriculum;
import model.utility.ResponseCodeUtils;
import model.utility.RouteUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by meranote on 4/20/2016 AD.
 */
@WebServlet(name = "AdminViewAlumniServlet", urlPatterns = {"/admin/alumni/*"})
public class AdminViewAlumniServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        response.sendError(ResponseCodeUtils.PAGE_NOT_FOUND);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String uriNoContext = RouteUtils.getURINoContext(request);
        uriNoContext = uriNoContext.substring("admin/alumni/".length());

        String[] uriSlashSplit = uriNoContext.split("/");
        int uriSlashSplitLength = uriSlashSplit.length;

        if(uriSlashSplitLength == 1) {
            if(Arrays.asList("bachelor", "master", "doctoral").contains(uriSlashSplit[0])) {
                request.setAttribute("degree", uriSlashSplit[0]);
                request.setAttribute("alumnis", Alumni.getAlumniWithinDegree(Curriculum.Degree.valueOf(uriSlashSplit[0].toUpperCase())));
                request.getRequestDispatcher("/WEB-INF/admin/alumni/viewalumni.jsp").forward(request, response);
                return;
            }
        }

        response.sendError(ResponseCodeUtils.PAGE_NOT_FOUND);
    }
}
