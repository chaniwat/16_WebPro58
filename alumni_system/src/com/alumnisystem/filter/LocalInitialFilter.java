package com.alumnisystem.filter;

import com.alumnisystem.utility.ResponseHelper;
import com.alumnisystem.utility.RouteHelper;
import com.alumnisystem.utility.database.Database;
import com.alumnisystem.utility.database.MysqlDBCP;
import com.alumnisystem.utility.Authorization;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Initial all local request instance.
 */
@WebFilter(filterName = "LocalInitialFilter")
public class LocalInitialFilter implements Filter {

    private MysqlDBCP datasource;

    public void init(FilterConfig config) throws ServletException {
        try {
            datasource = new MysqlDBCP();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
        try {
            datasource.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;
        HttpSession session = request.getSession();

        RouteHelper.setThreadLocal(request, session);
        ResponseHelper.setThreadLocal(request, session);

        try {
            Database.setConnectionThreadLocal(datasource.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Authorization.setSessionThreadLocal(session);

        chain.doFilter(req, resp);

        Database.closeConnection();
    }

}
