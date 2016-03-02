package auth;

import utility.helper.ErrorHelper;
import utility.helper.RouteHelper;

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
@WebServlet(name = "LoginServlet", urlPatterns = "/doLogin")
public class LoginServlet extends HttpServlet {

    private Authorization authorization = new Authorization();

    @Override
    public void init() {

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username"),
                password = request.getParameter("password");

        int userid;
        if((userid = authorization.doLogin(username, password)) > 0) {
            HttpSession session = request.getSession();

            session.setAttribute("user", userid);
            session.setMaxInactiveInterval(60*60);

            response.sendRedirect(RouteHelper.generateURL(request, ""));
            return;
        } else {
            ErrorHelper.setRequestError(request, ErrorHelper.ERR_BAD_LOGIN);
            getServletContext().getRequestDispatcher("/WEB-INF/auth/login.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();

        if(session != null && ErrorHelper.hasSessionError(session)) {
            ErrorHelper.setRequestErrorFromSession(session, request);
            ErrorHelper.clearError(session);
        }

        getServletContext().getRequestDispatcher("/WEB-INF/auth/login.jsp").forward(request, response);
    }

}
