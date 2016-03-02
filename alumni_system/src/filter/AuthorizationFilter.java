package filter;

import utility.helper.ErrorHelper;
import utility.helper.RouteHelper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by meranote on 2/9/2016 AD.
 */
@WebFilter(filterName = "AuthorizationFilter", urlPatterns = {"/*"})
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
         * Bypass resource (assets)
         */
        if(uri.contains("/assets/")) {
            chain.doFilter(req, resp);
            return;
        }

        if((session == null || session.getAttribute("user") == null || (int)session.getAttribute("user") == 0) && !(uri.endsWith("doLogin"))) {
            ErrorHelper.setSessionError(session, ErrorHelper.ERR_NO_LOGIN_401);
            response.sendRedirect(RouteHelper.generateURL(request, "doLogin"));
            return;
        } else if(!(session == null || session.getAttribute("user") == null || (int)session.getAttribute("user") == 0) && uri.endsWith("doLogin")) {
            response.sendRedirect(RouteHelper.generateURL(request, ""));
            return;
        }

        chain.doFilter(req, resp);
    }

}
