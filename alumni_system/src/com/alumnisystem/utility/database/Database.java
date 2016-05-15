package com.alumnisystem.utility.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<>();

    private Database() {}

    public static Connection getConnection() {
        return connectionThreadLocal.get();
    }

    public static void closeConnection() {
        try {
            connectionThreadLocal.get().close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void setConnectionThreadLocal(Connection connection) {
        Database.connectionThreadLocal.set(connection);
    }

    public static Connection getDatabaseConnectionForTest() throws SQLException {
        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
//        return DriverManager.getConnection("jdbc:mysql://localhost:3306/it_16", "root", "mysql");
        return DriverManager.getConnection("jdbc:mysql://database.it.kmitl.ac.th:3306/it_16", "it_16", "csQgCzmQ");
    }

}
