package com.alumnisystem.utility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;

/**
 * Route Helper
 */
public class RouteHelper {

    private static final String[] STATIC_RESOURCE = {"/assets/", ".js", ".css", ".jpg", ".png", ".gif"};

    private static ThreadLocal<HttpServletRequest> requestThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<HttpSession> sessionThreadLocal = new ThreadLocal<>();

    private RouteHelper() {}

    public static void setThreadLocal(HttpServletRequest request, HttpSession session) {
        requestThreadLocal.set(request);
        sessionThreadLocal.set(session);
    }

    /**
     * Generate the path
     * @param urlPath url (with no "/" at start)
     */
    public static String generateURL(String urlPath) {
        String uri = requestThreadLocal.get().getScheme() + "://" +
                requestThreadLocal.get().getServerName() +
                ("http".equals(requestThreadLocal.get().getScheme()) && requestThreadLocal.get().getServerPort() == 80 || "https".equals(requestThreadLocal.get().getScheme()) && requestThreadLocal.get().getServerPort() == 443 ? "" : ":" + requestThreadLocal.get().getServerPort() ) +
                requestThreadLocal.get().getContextPath();

        return uri + "/" + urlPath;
    }

    /**
     * Generate home path
     */
    public static String generateHomeURL() {
        return generateURL("");
    }

    /**
     * Pull last user path url from session
     * @return
     */
    public static String pullLastPathURL() {
        String tmp = (String) sessionThreadLocal.get().getAttribute("route.lastpathurl");
        sessionThreadLocal.get().setAttribute("route.lastpathurl", "");
        return tmp != null ? tmp : "";
    }

    /**
     * Push last user path url to session
     * @param lastPathUrl
     */
    public static void pushLastPathURL(String lastPathUrl) {
        sessionThreadLocal.get().setAttribute("route.lastpathurl", lastPathUrl);
    }

    public static HashMap<String, String> convertParamsToHashMap(String prefix) {
        Enumeration<String> paramsName = requestThreadLocal.get().getParameterNames();
        HashMap<String, String> params = new HashMap<>();

        while (paramsName.hasMoreElements()) {
            String paramName = paramsName.nextElement();
            if(!paramName.startsWith(prefix)) {
                continue;
            } else {
                if(requestThreadLocal.get().getParameter(paramName).trim().equals("") || requestThreadLocal.get().getParameter(paramName).equals("null")) {
                    params.put(paramName, null);
                } else {
                    params.put(paramName, requestThreadLocal.get().getParameter(paramName));
                }
            }
        }

        return params;
    }

    public static HashMap<String, String> convertParamsToHashMap() {
        return convertParamsToHashMap("");
    }

    public static String getURINoContext() {
        String uri = requestThreadLocal.get().getRequestURI();

        String[] uriSplit = uri.split("/");
        String uriNoContext = "";
        for(int i = 2; i < uriSplit.length; i++) {
            if(i == uriSplit.length - 1) uriNoContext += uriSplit[i];
            else uriNoContext += uriSplit[i] + "/";
        }
        if(!uriNoContext.equals("")) uriNoContext += "/";

        return uriNoContext;
    }

    public static boolean checkMatchPatternPath(String pattern, String uri) {
        if(pattern.charAt(pattern.length() - 1) != '/' && pattern.charAt(pattern.length() - 1) != '*') pattern += "/";

        if(uri.length() == 0)
            if(pattern.equals("") || pattern.equals("*")) return false;
            else return false;
        if(uri.length() == pattern.length() && uri.equals(pattern)) return true;

        int c;
        for(c = 0; c < pattern.length(); c++)
            if(c < uri.length() && pattern.charAt(c) != uri.charAt(c))
                if(c == pattern.length() - 1 && String.format("%c%c", pattern.charAt(c - 1), pattern.charAt(c)).equals("/*")) return true;
                else return false;
            else if(c >= uri.length()) break;

        if(uri.length() - 1 > c) return false;
        else if(pattern.length() > uri.length())
            if(c == pattern.length() - 1 && String.format("%c%c", pattern.charAt(c - 1), pattern.charAt(c)).equals("/*")) return true;
            else return false;
        else return true;
    }

    public static boolean isRequestStaticResource() {
        return isPathStaticResource(requestThreadLocal.get().getRequestURI());
    }

    public static boolean isPathStaticResource(String uri) {
        for(String ext : STATIC_RESOURCE) {
            if(uri.contains(ext)) return true;
        }
        return false;
    }

    public static boolean isAjaxRequest() {
        return requestThreadLocal.get().getHeader("X-Requested-With") != null || requestThreadLocal.get().getHeader("X-Requested-With").equals("XMLHttpRequest");
    }

    public static boolean isAdminPage() {
        return requestThreadLocal.get().getRequestURI().contains("/admin/") || requestThreadLocal.get().getRequestURI().endsWith("/admin");
    }

}
