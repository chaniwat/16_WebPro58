package auth;

import database.Database;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by meranote on 2/9/2016 AD.
 */
public class Authorization {

    private Connection connection;

    /**
     * Try to doLogin by username and password
     * @param username
     * @param password
     * @return user id if username and password is match in database, -1 if not, 0 for any error.
     */
    public int doLogin(String username, String password) {
        int result = 0;
        try {
            connection = Database.getInstance().getConnection();

            String sql = "SELECT * FROM user WHERE username = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, username);

            ResultSet resultSet = stmt.executeQuery();

            if(resultSet.last() && BCrypt.checkpw(password, resultSet.getString("password"))) {
                result = resultSet.getInt("id");
            } else {
                result = -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Database.closeConnection(connection);
        }

        return result;
    }

}
