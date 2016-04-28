package com.alumnisystem.filter;

import model.User;
import model.auth.Authorization;
import model.utility.ResponseCodeUtils;
import model.utility.RouteUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by meranote on 4/19/2016 AD.
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

        Authorization authorization = Authorization.getAuthInstance(session);

        if(!authorization.isLogin()) {
            ResponseCodeUtils.pushSessionCode(session, ResponseCodeUtils.UNAUTHORIZED);
            response.sendRedirect(RouteUtils.generateURL(request, "admin"));
            return;
        }

        User currentUser = authorization.getCurrentUser();
        if(currentUser.getType() != User.UserType.STAFF && (currentUser.getType() == User.UserType.TEACHER && currentUser.getId() != 56)) {
            response.sendError(ResponseCodeUtils.UNAUTHORIZED);
            return;
        }

        chain.doFilter(req, resp);
    }

}
