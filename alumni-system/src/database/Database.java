package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Meranote on 1/27/2016.
 */
public class Database {

    private static Database ourInstance = new Database();
    public static Database getInstance() {
        return ourInstance;
    }

    private static Connection connection;

    private Database() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            connection = DriverManager.getConnection("jdbc:mysql://database.it.kmitl.ac.th/it_16", "it_16", "csQgCzmQ");
        } catch(SQLException e) {
            e.printStackTrace();
            return;
        }
    }

    public Connection getConnection() {
        return connection;
    }
}