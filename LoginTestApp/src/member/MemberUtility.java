package member;

import database.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Meranote on 1/27/2016.
 */
public class MemberUtility {

    private MemberUtility() {}

    public static boolean doLogin(String user, String pass) {
        try {
            String sql = "SELECT * FROM member WHERE username = ? AND password = ?";
            PreparedStatement statement = Database.getInstance().getConnection().prepareStatement(sql);
            statement.setString(1, user);
            statement.setString(2, pass);

            return statement.executeQuery().last();
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int getUserId(String username) {
        try {
            String sql = "SELECT * FROM member WHERE username = ?";
            PreparedStatement statement = Database.getInstance().getConnection().prepareStatement(sql);
            statement.setString(1, username);

            ResultSet result = statement.executeQuery();
            result.last();
            return result.getInt("id");
        } catch(SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Get user data
     * @param id
     * @return null for not found
     */
    public static User getUser(int id) {
        try {
            String sql = "SELECT * FROM member WHERE id = ?";
            PreparedStatement statement = Database.getInstance().getConnection().prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet result = statement.executeQuery();
            result.last();

            return new User(id, result.getString("username"), result.getInt("type"));
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
