package model.utility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by meranote on 2/9/2016 AD.
 */
public class RouteUtils {

    private RouteUtils() {}

    /**
     * Generate the path
     * @param request
     * @param path
     * @return
     */
    public static String generateURL(HttpServletRequest request, String path) {
        String uri = request.getScheme() + "://" +
                request.getServerName() +
                ("http".equals(request.getScheme()) && request.getServerPort() == 80 || "https".equals(request.getScheme()) && request.getServerPort() == 443 ? "" : ":" + request.getServerPort() ) +
                request.getContextPath();

        return uri + "/" + path;
    }

    /**
     * Pull last user path url from session
     * @param session
     * @return
     */
    public static String pullLastPathURL(HttpSession session) {
        String tmp = (String) session.getAttribute("lastpathurl");
        session.setAttribute("lastpathurl", "");
        return tmp != null ? tmp : "";
    }

    /**
     * Push last user path url to session
     * @param session
     * @param lastpathurl
     */
    public static void pushLastPathURL(HttpSession session, String lastpathurl) {
        session.setAttribute("lastpathurl", lastpathurl);
    }

    public static String generateHomeURL(HttpServletRequest request) {
        return generateURL(request, "");
    }
}
