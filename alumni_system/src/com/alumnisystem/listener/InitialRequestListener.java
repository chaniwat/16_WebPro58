package com.alumnisystem.listener;

import com.alumnisystem.utility.Authorization;
import com.alumnisystem.utility.ResponseHelper;
import com.alumnisystem.utility.RouteHelper;
import com.alumnisystem.utility.TransportHelper;
import com.alumnisystem.utility.database.Database;
import com.alumnisystem.utility.database.MysqlDBCP;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

@WebListener()
public class InitialRequestListener implements ServletRequestListener {

    private MysqlDBCP datasource;

    public InitialRequestListener() throws SQLException {
        datasource = new MysqlDBCP();
    }

    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        HttpServletRequest request = (HttpServletRequest) servletRequestEvent.getServletRequest();
        HttpSession session = request.getSession();

        TransportHelper.setThreadLocal(request, session);
        RouteHelper.setThreadLocal(request, session);
        ResponseHelper.setThreadLocal(request, session);

        if(RouteHelper.isRequestStaticResource()) {
            return;
        }

        try {
            Database.setConnectionThreadLocal(datasource.getConnection());
            Authorization.setSessionThreadLocal(session);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
        HttpServletRequest request = (HttpServletRequest) servletRequestEvent.getServletRequest();

        if(RouteHelper.isPathStaticResource(request.getRequestURI())) {
            return;
        }

        Database.closeConnection();
    }

}
