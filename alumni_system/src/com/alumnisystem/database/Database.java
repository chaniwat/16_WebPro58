package com.alumnisystem.database;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by meranote on 2/9/2016 AD.
 */
public class Database {

    private Database() {}

    public static Connection getConnection(HttpServletRequest request) {
        return (Connection)request.getAttribute("db.connection");
    }

    public static void closeConnection(HttpServletRequest request) {
        if(request.getAttribute("db.connection") != null) {
            try {
                ((Connection) request.getAttribute("db.connection")).close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static Connection getDatabaseConnectionForTest() throws SQLException {
        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/it_16", "root", "mysql");
    }

}
