package com.alumnisystem.database;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by meranote on 2/9/2016 AD.
 */
public class Database {

    public static class Column {
        public String name;
        public int type;
    }

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

    public static ArrayList<Column> getTableColumnNameList(Connection connection, String table) {
        ArrayList<Column> columns = new ArrayList<>();

        try {
            ResultSet result = connection.getMetaData().getColumns(null, null, table, null);
            while (result.next()) {
                Column column = new Column();

                column.name = result.getString("COLUMN_NAME");
                column.type = result.getInt("DATA_TYPE");

                columns.add(column);
            }
            return columns;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<String> getTableNameList(Connection connection) {
        ArrayList<String> tables = new ArrayList<>();

        try {
            ResultSet result = connection.getMetaData().getTables(null, null, null, null);
            while (result.next()) {
                tables.add(result.getString("TABLE_NAME"));
            }
            return tables;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Connection getDatabaseConnectionForTest() throws SQLException {
        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/it_16", "root", "mysql");
    }

    public static Database getInstance() {
        return new Database();
    }

    public Connection getConnection() {
        return null;
    }

    public static void closeConnection(Connection connection) {

    }

}
