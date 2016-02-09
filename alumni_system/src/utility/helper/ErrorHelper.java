package utility.helper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by meranote on 2/10/2016 AD.
 */
public class ErrorHelper {

    private ErrorHelper() {}

    public static final int NO_ERR = 0;
    public static final int ERR_NO_LOGIN_401 = 1;

    public static final int ERR_BAD_LOGIN = 10;

    /**
     * Check error by session, usually used in controller/model.
     * @param session
     * @param errorcode
     * @return true if match, false if not.
     */
    public static boolean checkSessionError(HttpSession session, int errorcode) {
        return (int)session.getAttribute("error") == errorcode;
    }

    /**
     * Check error by request, usually used in jsp files.
     * @param request
     * @param errorcode
     * @return true if match, false if not.
     */
    public static boolean checkRequestError(HttpServletRequest request, int errorcode) {
        return (int)request.getAttribute("error") == errorcode;
    }

    /**
     * Clear error in session (set error to {@link ErrorHelper#NO_ERR})
     * @param session
     */
    public static void clearError(HttpSession session) {
        session.setAttribute("error", NO_ERR);
    }

    /**
     * Check if have error in session.
     * @param session
     * @return true if have, false if not.
     */
    public static boolean hasSessionError(HttpSession session) {
        return session.getAttribute("error") != null && (int)session.getAttribute("error") != NO_ERR;
    }

    /**
     * Check if have error in session.
     * @param request
     * @return true if have, false if not.
     */
    public static boolean hasRequestError(HttpServletRequest request) {
        return request.getAttribute("error") != null && (int)request.getAttribute("error") != NO_ERR;
    }

    /**
     * Get error from session.
     * @param session
     * @return error code.
     */
    public static int getError(HttpSession session) {
        return (int)session.getAttribute("error");
    }

    /**
     * Set error attribute in request from session.
     * @param session
     * @param request
     */
    public static void setRequestErrorFromSession(HttpSession session, HttpServletRequest request) {
        request.setAttribute("error", getError(session));
    }

    /**
     * Set error attribute in session.
     * @param session
     * @param errorcode
     */
    public static void setSessionError(HttpSession session, int errorcode) {
        session.setAttribute("error", errorcode);
    }

    /**
     * Set error attribute in request.
     * @param request
     * @param errorcode
     */
    public static void setRequestError(HttpServletRequest request, int errorcode) {
        request.setAttribute("error", errorcode);
    }

}
