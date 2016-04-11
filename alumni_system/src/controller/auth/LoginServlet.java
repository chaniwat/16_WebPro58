package controller.auth;

import model.auth.Authorization;
import model.utility.ResponseCodeUtils;
import model.utility.RouteUtils;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * Created by meranote on 2/9/2016 AD.
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login/*"})
public class LoginServlet extends GenericServlet {

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        Authorization authorization = Authorization.getAuthInstance(session);

        // if user already login, go to index
        if(authorization.isLogin()) {
            response.sendRedirect(RouteUtils.generateHomeURL(request));
            return;
        }

        String method = request.getMethod();
        if(method == "GET") {
            if(ResponseCodeUtils.hasCodeInSession(session)) {
                ResponseCodeUtils.pushRequestCode(request, ResponseCodeUtils.pullSessionCode(session));
            }
        } else if(method == "POST") {
            String username = request.getParameter("username"),
                    password = request.getParameter("password");

            if(authorization.doLogin(username, password)) {
                response.sendRedirect(RouteUtils.generateURL(request, RouteUtils.pullLastPathURL(session)));
                return;
            } else {
                ResponseCodeUtils.pushRequestCode(request, ResponseCodeUtils.BAD_LOGIN);
            }
        } else {
            ResourceBundle lStrings = ResourceBundle.getBundle("javax.servlet.http.LocalStrings");
            String errMsg1 = lStrings.getString("http.method_not_implemented");
            Object[] errArgs = new Object[]{method};
            errMsg1 = MessageFormat.format(errMsg1, errArgs);
            response.sendError(501, errMsg1);
            return;
        }

        getServletContext().getRequestDispatcher("/WEB-INF/auth/login.jsp").forward(request, response);
    }

}
