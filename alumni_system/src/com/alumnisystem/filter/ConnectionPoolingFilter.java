package com.alumnisystem.filter;

import com.alumnisystem.database.Database;
import com.alumnisystem.database.MysqlDBCP;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Connection Create Filter
 */
@WebFilter(filterName = "ConnectionPoolingFilter")
public class ConnectionPoolingFilter implements Filter {

    private MysqlDBCP datasource;

    public void init(FilterConfig config) throws ServletException {
        try {
            datasource = new MysqlDBCP();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        try {
            req.setAttribute("db.connection", datasource.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        chain.doFilter(req, resp);

        Database.closeConnection((HttpServletRequest) req);
    }

    public void destroy() {
        try {
            datasource.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
