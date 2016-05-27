package com.alumnisystem.listener;

import com.alumnisystem.utility.*;
import com.alumnisystem.utility.database.Database;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.sql.SQLException;

@WebListener()
public class InitialRequestListener implements ServletRequestListener {

    private DataSource dataSource;

    public InitialRequestListener() throws SQLException {
        try {
            InitialContext initialContext = new InitialContext();
            dataSource = (DataSource) initialContext.lookup("java:/comp/env/jdbc/AlumniDB");
        } catch (NamingException e) { }
    }

    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        HttpServletRequest request = (HttpServletRequest) servletRequestEvent.getServletRequest();
        HttpSession session = request.getSession();

        TransportHelper.setThreadLocal(request, session);
        RouteHelper.setThreadLocal(request, session);
        ResponseHelper.setThreadLocal(request, session);
        FileHelper.setThreadLocal(request);

        try {
            Database.setConnectionThreadLocal(dataSource.getConnection());
            Authorization.setSessionThreadLocal(session);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
        Database.closeConnection();
    }

}
