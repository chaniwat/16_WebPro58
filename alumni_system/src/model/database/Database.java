package model.database;

import tester.Tester;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by meranote on 2/9/2016 AD.
 */
public class Database {

    private static Database instance = new Database();
    public static Database getInstance() {
        return instance;
    }

    private DataSource dataSource;

    private Database() {
        try {
            InitialContext initialContext = new InitialContext();
            dataSource = (DataSource) initialContext.lookup("java:/comp/env/jdbc/AlumniDB");
        } catch (NamingException e) { }
    }

    public Connection getConnection() {
        if(Tester.isTest()) {
            try {
                return Database.getConnectionForTest();
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            try {
                return dataSource.getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public static void closeConnection(Connection connection) {
        if(connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static Connection getConnectionForTest() throws SQLException {
        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        return DriverManager.getConnection("jdbc:mysql://database.it.kmitl.ac.th:3306/it_16", "it_16", "csQgCzmQ");
    }

}
