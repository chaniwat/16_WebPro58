package com.alumnisystem.utility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class TransportHelper {

    private static ThreadLocal<HttpServletRequest> requestThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<HttpSession> sessionThreadLocal = new ThreadLocal<>();

    private TransportHelper() {}

    public static void setThreadLocal(HttpServletRequest request, HttpSession session) {
        requestThreadLocal.set(request);
        sessionThreadLocal.set(session);
    }



}
