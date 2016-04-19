package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Created by meranote on 4/19/2016 AD.
 */
@WebFilter(filterName = "AdminCheckFilter")
public class AdminCheckFilter implements Filter {

    public void init(FilterConfig config) throws ServletException {

    }

    public void destroy() { }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        chain.doFilter(req, resp);
    }

}
