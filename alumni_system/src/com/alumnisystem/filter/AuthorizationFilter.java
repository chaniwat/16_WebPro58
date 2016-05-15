package com.alumnisystem.filter;

import com.alumnisystem.annotation.AuthGuard;
import com.alumnisystem.utility.Authorization;
import com.alumnisystem.utility.ResponseHelper;
import com.alumnisystem.utility.RouteHelper;
import org.reflections.Reflections;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;
import java.util.TreeMap;

/**
 * Authorization check
 */
@WebFilter(filterName = "AuthorizationFilter")
public class AuthorizationFilter implements Filter {

    public void init(FilterConfig config) throws ServletException {}

    public void destroy() {}

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
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

        String uriNoContext = RouteHelper.getURINoContext();

        /**
         * Bypass index
         */
        if(uriNoContext.equals("")) {
            chain.doFilter(req, resp);
            return;
        }

        Reflections reflections = new Reflections();
        Set<Class<?>> servlets = reflections.getTypesAnnotatedWith(javax.servlet.annotation.WebServlet.class);
        TreeMap<String, Class<?>> matchPath = new TreeMap<>();
        for (Class c : servlets) {
            javax.servlet.annotation.WebServlet servletAnnotation =
                    (javax.servlet.annotation.WebServlet) c.getAnnotation(javax.servlet.annotation.WebServlet.class);

            for (String uriPattern : servletAnnotation.urlPatterns()) {
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
                if(authGuardAnnotation.redirectback()) RouteHelper.pushLastPathURL(uriNoContext);
                ResponseHelper.pushSessionCode(ResponseHelper.UNAUTHORIZED);
                response.sendRedirect(RouteHelper.generateURL("login"));
                return;
            }
        }

        chain.doFilter(req, resp);
    }

}
