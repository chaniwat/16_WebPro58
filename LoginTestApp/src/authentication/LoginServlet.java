package authentication;

import member.MemberUtility;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Meranote on 1/27/2016.
 */
@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username"),
                password = request.getParameter("password");

        if(MemberUtility.doLogin(username, password)) {
            HttpSession session = request.getSession();
            session.setAttribute("user", MemberUtility.getUserId(username));
            session.setMaxInactiveInterval(60*60);
            response.sendRedirect("./");
        } else {
            request.setAttribute("res_code", 1);
            getServletContext().getRequestDispatcher("/WEB-INF/JSP/loginform.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        if(request.getParameter("res_code") != null) {
            request.setAttribute("res_code", Integer.parseInt(request.getParameter("res_code")));
        }

        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/WEB-INF/JSP/loginform.jsp");
        requestDispatcher.forward(request, response);
    }
}
