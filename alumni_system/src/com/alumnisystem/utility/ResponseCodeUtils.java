package com.alumnisystem.utility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Response Code Helper
 */
public class ResponseCodeUtils {

    private ResponseCodeUtils() {}

    public static final int NO_ERR = 0;

    public static final int BAD_REQUEST = HttpServletResponse.SC_BAD_REQUEST;
    public static final int UNAUTHORIZED = HttpServletResponse.SC_UNAUTHORIZED;
    public static final int FORBIDDEN = HttpServletResponse.SC_FORBIDDEN;
    public static final int PAGE_NOT_FOUND = HttpServletResponse.SC_NOT_FOUND;

    public static final int INTERNAL_SERVER_ERROR = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
    public static final int SERVICE_UNAVALIABLE = HttpServletResponse.SC_SERVICE_UNAVAILABLE;

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
     * Get session error code
     * @param session
     * @return
     */
    public static int getSessionCode(HttpSession session) {
        return (int)session.getAttribute("error");
    }

    /**
     * Get request error code
     * @param request
     * @return
     */
    public static int getRequestCode(HttpServletRequest request) {
        return (int)request.getAttribute("error");
    }

}
