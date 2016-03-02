package utility.helper;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by meranote on 2/9/2016 AD.
 */
public class RouteHelper {

    private RouteHelper() {}

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
}
