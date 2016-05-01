package com.alumnisystem.filter;

import com.alumnisystem.model.User;
import com.alumnisystem.utility.Authorization;
import com.alumnisystem.utility.ResponseCodeUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Admin panel authorization check
 */
@WebFilter(filterName = "AdminCheckFilter")
public class AdminCheckFilter implements Filter {

    public void init(FilterConfig config) throws ServletException { }

    public void destroy() { }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;
        HttpSession session = request.getSession();

        request.getSession().setAttribute("errorfromadmin", true);

        Authorization authorization = Authorization.getAuthInstance(request);

//        if(!authorization.isLogin()) {
//            ResponseCodeUtils.pushSessionCode(session, ResponseCodeUtils.UNAUTHORIZED);
//            response.sendRedirect(RouteUtils.generateURL(request, "admin"));
//            return;
//        }

        User currentUser = authorization.getCurrentUser();
        if(!currentUser.isAdmin()) {
            response.sendError(ResponseCodeUtils.UNAUTHORIZED);
            return;
        }

        chain.doFilter(req, resp);
    }

}
