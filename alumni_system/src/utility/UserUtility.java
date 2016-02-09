package utility;

import database.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by meranote on 2/10/2016 AD.
 */
public class UserUtility {

    private UserUtility() {}

    /**
     * Get user id from username
     * @param username
     * @return id, 0 for not found user, -1 for any {@link SQLException} error.
     */
    public static int getUserID(String username) {
        try {
            String sql = "SELECT * FROM user WHERE username = ?";
            PreparedStatement stmt = Database.getInstance().getConnection().prepareStatement(sql);
            stmt.setString(1, username);

            ResultSet result = stmt.executeQuery();
            if(result.last()) {
                return result.getInt("id");
            } else {
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

}
