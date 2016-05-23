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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Authorization check
 */
@WebFilter(filterName = "AuthorizationFilter")
public class AuthorizationFilter implements Filter {

    private static HashMap<String, AuthGuard> guardURL;

    public void init(FilterConfig config) throws ServletException {
        /** Pre-Mapping AuthGuard */
        guardURL = new HashMap<>();

        for (Class c : new Reflections().getTypesAnnotatedWith(WebServlet.class)) {
            AuthGuard authGuardAnnotation = (AuthGuard) c.getAnnotation(AuthGuard.class);
            if(authGuardAnnotation != null) {
                for (String uriPattern : ((WebServlet) c.getAnnotation(WebServlet.class)).urlPatterns()) {
                    guardURL.put(uriPattern, authGuardAnnotation);
                }
            }
        }
    }

    public void destroy() {}

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletResponse response = (HttpServletResponse)resp;
        String uriNoContext = RouteHelper.getURINoContext();

        /** Bypass index and static resource (assets) */
        if(RouteHelper.isRequestStaticResource() || uriNoContext.equals("")) {
            chain.doFilter(req, resp);
            return;
        }

        TreeMap<String, AuthGuard> matchPath = new TreeMap<>();

        for(Map.Entry<String, AuthGuard> entry : guardURL.entrySet()) {
            if(RouteHelper.checkMatchPatternPath(entry.getKey(), "/" + uriNoContext)) {
                matchPath.put(entry.getKey(), entry.getValue());
            }
        }

        AuthGuard authGuardAnnotation = null;
        if(matchPath.size() > 0) {
            authGuardAnnotation = matchPath.get(matchPath.lastKey());
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
