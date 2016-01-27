package pageController;

import member.MemberUtility;
import member.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Meranote on 1/27/2016.
 */
@WebServlet(name = "HomeServlet", urlPatterns = "/")
public class HomeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        User user = MemberUtility.getUser((int)request.getSession().getAttribute("user"));
        request.setAttribute("user_name", user.getName());
        String usertype;
        switch(user.getType()) {
            case 1: usertype = "BOSS"; break;
            case 2: usertype = "ADMIN"; break;
            default: usertype = "USER"; break;
        }
        request.setAttribute("user_type", usertype);

        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/WEB-INF/index.jsp");
        requestDispatcher.forward(request, response);
    }
}
