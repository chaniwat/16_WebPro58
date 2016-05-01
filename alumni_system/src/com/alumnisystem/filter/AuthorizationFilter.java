package com.alumnisystem.filter;

import com.alumnisystem.annotation.AuthGuard;
import com.alumnisystem.database.Database;
import com.alumnisystem.utility.Authorization;
import com.alumnisystem.utility.ResponseCodeUtils;
import com.alumnisystem.utility.RouteUtils;
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

    private FilterConfig config = null;

    public void init(FilterConfig config) throws ServletException {
        this.config = config;
    }

    public void destroy() {}

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;

        String uri = request.getRequestURI();
        HttpSession session = request.getSession();

        Authorization authorization = new Authorization(Database.getConnection(request), session);
        request.setAttribute("auth", authorization);

        /**
         * Bypass resource (assets)
         */
        if(uri.contains("/assets/")) {
            chain.doFilter(req, resp);
            return;
        }

        String uriNoContext = RouteUtils.getURINoContext(request);

        /**
         * Bypass index
         */
        if(uriNoContext.equals("")) {
            chain.doFilter(req, resp);
            return;
        }

        String authGuardSession = null;

        Reflections reflections = new Reflections();
        Set<Class<?>> servlets = reflections.getTypesAnnotatedWith(javax.servlet.annotation.WebServlet.class);
        TreeMap<String, Class<?>> matchPath = new TreeMap<>();
        for (Class c : servlets) {
            javax.servlet.annotation.WebServlet servletAnnotation =
                    (javax.servlet.annotation.WebServlet) c.getAnnotation(javax.servlet.annotation.WebServlet.class);

            for (String uriPattern : servletAnnotation.urlPatterns()) {
                if(checkMatchPath(uriPattern, "/" + uriNoContext)) {
                    matchPath.put(uriPattern, c);
                }
            }
        }

        AuthGuard authGuardAnnotation = null;
        if(matchPath.size() > 0) {
            Class c = matchPath.get(matchPath.lastKey());
            authGuardAnnotation = (AuthGuard) c.getAnnotation(AuthGuard.class);
            if(authGuardAnnotation != null) {
                authGuardSession = authGuardAnnotation.guard();
            }
        }

        if(authGuardSession != null) {
            if(!authorization.isLogin()) {
                if(authGuardAnnotation.redirectback()) RouteUtils.pushLastPathURL(session, uriNoContext);
                ResponseCodeUtils.pushSessionCode(session, ResponseCodeUtils.UNAUTHORIZED);
                response.sendRedirect(RouteUtils.generateURL(request, "login"));
                return;
            }
        }

        chain.doFilter(req, resp);
    }

    private boolean checkMatchPath(String uri, String currentUri) {
        if(uri.charAt(uri.length() - 1) != '/' && uri.charAt(uri.length() - 1) != '*') uri += "/";

        if(currentUri.length() == 0)
            if(uri.equals("") || uri.equals("*")) return false;
            else return false;
        if(currentUri.length() == uri.length() && currentUri.equals(uri)) return true;

        int c;
        for(c = 0; c < uri.length(); c++)
            if(c < currentUri.length() && uri.charAt(c) != currentUri.charAt(c))
                if(c == uri.length() - 1 && String.format("%c%c", uri.charAt(c - 1), uri.charAt(c)).equals("/*")) return true;
                else return false;
            else if(c >= currentUri.length()) break;

        if(currentUri.length() - 1 > c) return false;
        else if(uri.length() > currentUri.length())
            if(c == uri.length() - 1 && String.format("%c%c", uri.charAt(c - 1), uri.charAt(c)).equals("/*")) return true;
            else return false;
        else return true;
    }

}