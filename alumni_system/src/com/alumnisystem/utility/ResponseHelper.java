package com.alumnisystem.utility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Response Code Helper
 */
public class ResponseHelper {

    private static ThreadLocal<HttpServletRequest> requestThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<HttpSession> sessionThreadLocal = new ThreadLocal<>();

    private ResponseHelper() {}

    public static void setThreadLocal(HttpServletRequest request, HttpSession session) {
        requestThreadLocal.set(request);
        sessionThreadLocal.set(session);
    }

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
     * @return true if have, false if not.
     */
    public static boolean hasCodeInSession() {
        return sessionThreadLocal.get().getAttribute("error") != null && (int)sessionThreadLocal.get().getAttribute("error") != NO_ERR;
    }

    /**
     * Check if have error in session.
     * @return true if have, false if not.
     */
    public static boolean hasCodeInRequest() {
        return requestThreadLocal.get().getAttribute("error") != null && (int)requestThreadLocal.get().getAttribute("error") != NO_ERR;
    }

    /**
     * Push error attribute in session scope.
     * @param errorcode
     */
    public static void pushSessionCode(int errorcode) {
        sessionThreadLocal.get().setAttribute("error", errorcode);
    }

    /**
     * Push error in request scope.
     * @param errorcode
     */
    public static void pushRequestCode(int errorcode) {
        requestThreadLocal.get().setAttribute("error", errorcode);
    }

    /**
     * Pull and clear error in session scope.
     * @return true if match, false if not.
     */
    public static int pullSessionCode() {
        int code = (int)sessionThreadLocal.get().getAttribute("error");
        pushSessionCode(NO_ERR);
        return code;
    }

    /**
     * Get session error code
     * @return
     */
    public static int getSessionCode() {
        return (int)sessionThreadLocal.get().getAttribute("error");
    }

    /**
     * Get request error code
     * @return
     */
    public static int getRequestCode() {
        return (int)requestThreadLocal.get().getAttribute("error");
    }

}
