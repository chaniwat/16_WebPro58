package auth;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by meranote on 2/9/2016 AD.
 */
@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();

        if(session != null && session.getAttribute("usr_error") != null) {
            if(session.getAttribute("usr_error").equals("err_not_login_401")) {
                request.setAttribute("error", "err_not_login_401");
            }
        }

        getServletContext().getRequestDispatcher("/WEB-INF/auth/login.jsp").forward(request, response);
    }
}
