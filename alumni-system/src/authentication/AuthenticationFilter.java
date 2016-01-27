package authentication;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Meranote on 1/27/2016.
 */
@WebFilter(filterName = "AuthenticationFilter")
public class AuthenticationFilter implements Filter {

    private FilterConfig filterConfig;

    public void destroy() {
        this.filterConfig = null;
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        if(filterConfig == null) return;

        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;

        String uri = request.getRequestURI();
        HttpSession session = request.getSession();

        if((session == null || session.getAttribute("user") == null || (int)session.getAttribute("user") == 0) && !(uri.endsWith("login"))) {
            response.sendRedirect("login?res_code=2");
            return;
        } else if(!(session == null || session.getAttribute("user") == null || (int)session.getAttribute("user") == 0) && uri.endsWith("login")) {
            response.sendRedirect("./");
            return;
        }

        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {
        this.filterConfig = config;
    }

}
