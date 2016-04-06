package filter;

import model.auth.Authorization;
import model.utility.ErrorUtils;
import model.utility.RouteUtils;
import org.reflections.Reflections;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by meranote on 2/9/2016 AD.
 */
@WebFilter(filterName = "AuthorizationFilter", urlPatterns = {"/*"})
public class AuthorizationFilter implements Filter {

    private HashMap<String, String> authGuardConfig;

    public void init(FilterConfig config) throws ServletException {
        this.authGuardConfig = new HashMap<>();
        this.authGuardConfig.put("user", "login");
        this.authGuardConfig.put("admin", "admin/login");
    }

    public void destroy() {}

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;

        String uri = request.getRequestURI();
        HttpSession session = request.getSession();

        Authorization authorization;
        if((authorization = (Authorization) session.getAttribute("auth")) == null) {
            authorization = new Authorization(session);
            session.setAttribute("auth", authorization);
        }

        /**
         * Bypass resource (assets)
         */
        if(uri.contains("/assets/")) {
            chain.doFilter(req, resp);
            return;
        }

        String[] uriSplit = uri.split("/");
        String uriNoContext = "";
        for(int i = 2; i < uriSplit.length; i++) {
            if(i == uriSplit.length - 1) uriNoContext += uriSplit[i];
            else uriNoContext += uriSplit[i] + "/";
        }

        String authGuardSession = null;

        Reflections reflections = new Reflections();
        Set<Class<?>> servlets = reflections.getTypesAnnotatedWith(javax.servlet.annotation.WebServlet.class);
        boolean flag = false;
        for (Class c : servlets) {
            javax.servlet.annotation.WebServlet servletAnnotation =
                    (javax.servlet.annotation.WebServlet) c.getAnnotation(javax.servlet.annotation.WebServlet.class);

            for (String uriPattern : servletAnnotation.urlPatterns()) {
                if(uriPattern.equals("/" + uriNoContext)) {
                    annotation.auth.AuthGuard authGuardAnnotation =
                            (annotation.auth.AuthGuard) c.getAnnotation(annotation.auth.AuthGuard.class);
                    if(authGuardAnnotation != null) {
                        authGuardSession = authGuardAnnotation.guard();
                    }

                    flag = true;
                    break;
                }
            }

            if(flag) break;
        }

        if(authGuardSession != null) {
            authorization.setGuard(authGuardSession);

            if(!authorization.isLogin()) {
                RouteUtils.pushLastPathURL(session, uriNoContext);
                ErrorUtils.setSessionError(session, ErrorUtils.UNAUTHORIZED);
                response.sendRedirect(RouteUtils.generateURL(request, "login"));
                return;
            }
        } else {
            authorization.setGuard(Authorization.DEFAULTGUARD);
        }

        chain.doFilter(req, resp);
    }

}
