package com.alumnisystem.filter;

import com.alumnisystem.model.User;
import com.alumnisystem.utility.Authorization;
import com.alumnisystem.utility.ResponseHelper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

        request.getSession().setAttribute("route.errorFromAdmin", true);

        User currentUser = Authorization.getCurrentUser();
        if(!currentUser.isAdmin()) {
            response.sendError(ResponseHelper.UNAUTHORIZED);
            return;
        }

        chain.doFilter(req, resp);
    }

}
