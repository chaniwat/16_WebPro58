package controller.auth;

import model.auth.Authorization;
import model.utility.ResponseCodeUtils;
import model.utility.RouteUtils;

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
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        Authorization authorization = Authorization.getAuthInstance(session);

        String username = request.getParameter("username"),
                password = request.getParameter("password");

        // if user already login, go to index
        if(authorization.isLogin()) {
            response.sendRedirect(RouteUtils.generateHomeURL(request));
            return;
        }

        if(authorization.doLogin(username, password)) {
            response.sendRedirect(RouteUtils.generateURL(request, RouteUtils.pullLastPathURL(session)));
            return;
        } else {
            ResponseCodeUtils.pushRequestCode(request, ResponseCodeUtils.BAD_LOGIN);
            getServletContext().getRequestDispatcher("/WEB-INF/auth/login.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        Authorization authorization = Authorization.getAuthInstance(session);

        // if user already login, go to index
        if(authorization.isLogin()) {
            response.sendRedirect(RouteUtils.generateHomeURL(request));
            return;
        }

        if(ResponseCodeUtils.hasCodeInSession(session)) {
            ResponseCodeUtils.pushRequestCode(request, ResponseCodeUtils.pullSessionCode(session));
        }

        getServletContext().getRequestDispatcher("/WEB-INF/auth/login.jsp").forward(request, response);
    }

}
