package com.alumnisystem.utility;

import com.alumnisystem.exception.UserNotFound;
import com.alumnisystem.factory.UserFactory;
import com.alumnisystem.model.User;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.http.HttpSession;

/**
 * Authorization Utility
 */
public class Authorization {

    private static final String AUTH_ATTR_NAME = "auth.user";

    private static ThreadLocal<HttpSession> sessionThreadLocal = new ThreadLocal<>();

    public static void setSessionThreadLocal(HttpSession session) {
        sessionThreadLocal.set(session);
    }

    /**
     * Login by username and password, And save user session into authorize authGuard session.
     * @param username as string
     * @param password as string
     * @return true if username and password is match , false for nothing.
     */
    public static boolean doLogin(String username, String password) {
        try {
            User user = new UserFactory().findByUsername(username);

            if(BCrypt.checkpw(password, user.getPassword())) {
                sessionThreadLocal.get().setAttribute("auth.user", user);
                sessionThreadLocal.get().setMaxInactiveInterval(3600);

                return true;
            }
        } catch (UserNotFound ex) { }
        return false;
    }

    public static void doLogout() {
        sessionThreadLocal.get().setAttribute(AUTH_ATTR_NAME, null);
    }

    public static boolean isLogin() {
        return sessionThreadLocal.get().getAttribute(AUTH_ATTR_NAME) != null;
    }

    public static User getCurrentUser() {
        return (User) sessionThreadLocal.get().getAttribute(AUTH_ATTR_NAME);
    }

}
