package com.alumnisystem.utility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;

/**
 * Route Helper
 */
public class RouteHelper {

    private static ThreadLocal<HttpServletRequest> requestThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<HttpSession> sessionThreadLocal = new ThreadLocal<>();

    private RouteHelper() {}

    public static void setThreadLocal(HttpServletRequest request, HttpSession session) {
        requestThreadLocal.set(request);
        sessionThreadLocal.set(session);
    }

    /**
     * Generate the path
     * @param path
     * @return
     */
    public static String generateURL(String path) {
        String uri = requestThreadLocal.get().getScheme() + "://" +
                requestThreadLocal.get().getServerName() +
                ("http".equals(requestThreadLocal.get().getScheme()) && requestThreadLocal.get().getServerPort() == 80 || "https".equals(requestThreadLocal.get().getScheme()) && requestThreadLocal.get().getServerPort() == 443 ? "" : ":" + requestThreadLocal.get().getServerPort() ) +
                requestThreadLocal.get().getContextPath();

        return uri + "/" + path;
    }

    /**
     * Pull last user path url from session
     * @return
     */
    public static String pullLastPathURL() {
        String tmp = (String) sessionThreadLocal.get().getAttribute("lastpathurl");
        sessionThreadLocal.get().setAttribute("lastpathurl", "");
        return tmp != null ? tmp : "";
    }

    /**
     * Push last user path url to session
     * @param lastpathurl
     */
    public static void pushLastPathURL(String lastpathurl) {
        sessionThreadLocal.get().setAttribute("lastpathurl", lastpathurl);
    }

    public static HashMap<String, String> convertParamsToHashMap(String prefix) {
        Enumeration<String> paramnames = requestThreadLocal.get().getParameterNames();
        HashMap<String, String> params = new HashMap<>();

        while (paramnames.hasMoreElements()) {
            String paramname = paramnames.nextElement();
            if(!paramname.startsWith(prefix)) {
                continue;
            } else {
                if(requestThreadLocal.get().getParameter(paramname).trim() == "" || requestThreadLocal.get().getParameter(paramname) == "null") {
                    params.put(paramname, null);
                } else {
                    params.put(paramname, requestThreadLocal.get().getParameter(paramname));
                }
            }
        }

        return params;
    }

    public static HashMap<String, String> convertParamsToHashMap() {
        return convertParamsToHashMap("");
    }

    public static String generateHomeURL() {
        return generateURL("");
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

}
