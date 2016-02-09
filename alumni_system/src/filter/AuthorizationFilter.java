package filter;

import utility.RouteHelper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by meranote on 2/9/2016 AD.
 */
@WebFilter(filterName = "AuthorizationFilter")
public class AuthorizationFilter implements Filter {

    private FilterConfig filterConfig;

    public void init(FilterConfig config) throws ServletException {
        this.filterConfig = config;
    }

    public void destroy() {
        this.filterConfig = null;
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        if(filterConfig == null) return;

        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;

        String uri = request.getRequestURI();
        HttpSession session = request.getSession();

        /**
         * Bypass resource
         */
        if(uri.contains("/assets/")) {
            chain.doFilter(req, resp);
            return;
        }

        /**
         * Authorization check
         */
        if((session == null || session.getAttribute("user") == null || (int)session.getAttribute("user") == 0) && !(uri.endsWith("login"))) {
            response.sendRedirect(RouteHelper.generateURL(request, "login"));
            return;
        } else if(!(session == null || session.getAttribute("user") == null || (int)session.getAttribute("user") == 0) && uri.endsWith("login")) {
            response.sendRedirect(RouteHelper.generateURL(request, ""));
            return;
        }

        chain.doFilter(req, resp);
    }

}
