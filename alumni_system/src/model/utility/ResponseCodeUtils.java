package model.utility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by meranote on 2/10/2016 AD.
 */
public class ResponseCodeUtils {

    private ResponseCodeUtils() {}

    public static final int NO_ERR = 0;

    public static final int BAD_REQUEST = 400;
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;

    public static final int INTERNAL_SERVER_ERROR = 500;
    public static final int SERVICE_UNAVALIABLE = 503;

    public static final int BAD_LOGIN = 601;
    public static final int NOT_ENOUGH_PERMISSION = 602;

    public static final int NO_USER_MODEL_FOUND = 701;

    public static final int FORM_INPUT_NOT_COMPLETE = 901;

    public static final int PROFILE_UPDATED_COMPLETE = 1001;

    /**
     * Check if have error in session.
     * @param session
     * @return true if have, false if not.
     */
    public static boolean hasCodeInSession(HttpSession session) {
        return session.getAttribute("error") != null && (int)session.getAttribute("error") != NO_ERR;
    }

    /**
     * Check if have error in session.
     * @param request
     * @return true if have, false if not.
     */
    public static boolean hasCodeInRequest(HttpServletRequest request) {
        return request.getAttribute("error") != null && (int)request.getAttribute("error") != NO_ERR;
    }

    /**
     * Push error attribute in session scope.
     * @param session
     * @param errorcode
     */
    public static void pushSessionCode(HttpSession session, int errorcode) {
        session.setAttribute("error", errorcode);
    }

    /**
     * Push error in request scope.
     * @param request
     * @param errorcode
     */
    public static void pushRequestCode(HttpServletRequest request, int errorcode) {
        request.setAttribute("error", errorcode);
    }

    /**
     * Pull and clear error in session scope.
     * @param session
     * @return true if match, false if not.
     */
    public static int pullSessionCode(HttpSession session) {
        int code = (int)session.getAttribute("error");
        pushSessionCode(session, NO_ERR);
        return code;
    }

    /**
     * Pull and clear error in request scope.
     * @param request
     * @return true if match, false if not.
     */
    public static int pullRequestCode(HttpServletRequest request) {
        int code = (int)request.getAttribute("error");
        pushRequestCode(request, NO_ERR);
        return code;
    }

}
