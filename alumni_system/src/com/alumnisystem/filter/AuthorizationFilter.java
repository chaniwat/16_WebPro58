package com.alumnisystem.filter;

import com.alumnisystem.annotation.AuthGuard;
import com.alumnisystem.utility.Authorization;
import com.alumnisystem.utility.ResponseHelper;
import com.alumnisystem.utility.RouteHelper;
import org.reflections.Reflections;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.TreeMap;

/**
 * Authorization check
 */
@WebFilter(filterName = "AuthorizationFilter")
public class AuthorizationFilter implements Filter {

    public void init(FilterConfig config) throws ServletException {}

    public void destroy() {}

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletResponse response = (HttpServletResponse)resp;
        String uriNoContext = RouteHelper.getURINoContext();

        /** Bypass index and static resource (assets) */
        if(RouteHelper.isRequestStaticResource() || uriNoContext.equals("")) {
            chain.doFilter(req, resp);
            return;
        }

        TreeMap<String, Class<?>> matchPath = new TreeMap<>();
        for (Class c : new Reflections().getTypesAnnotatedWith(WebServlet.class)) {
            for (String uriPattern : ((WebServlet) c.getAnnotation(WebServlet.class)).urlPatterns()) {
                if(RouteHelper.checkMatchPatternPath(uriPattern, "/" + uriNoContext)) {
                    matchPath.put(uriPattern, c);
                }
            }
        }

        AuthGuard authGuardAnnotation = null;
        if(matchPath.size() > 0) {
            Class c = matchPath.get(matchPath.lastKey());
            authGuardAnnotation = (AuthGuard) c.getAnnotation(AuthGuard.class);
        }

        if(authGuardAnnotation != null) {
            if(!Authorization.isLogin()) {
                if(authGuardAnnotation.redirectback()) {
                    RouteHelper.pushLastPathURL(uriNoContext);
                }
                ResponseHelper.pushSessionCode(ResponseHelper.UNAUTHORIZED);
                response.sendRedirect(RouteHelper.generateURL("login"));
                return;
            }
        }

        chain.doFilter(req, resp);
    }

}
