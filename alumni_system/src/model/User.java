package model;

import exception.NoUserFoundException;
import model.database.Database;

import java.io.Serializable;
import java.sql.*;

/**
 * Created by meranote on 2/10/2016 AD.
 */
public class User implements Serializable {

    public enum UserType {
        ALUMNI,
        STAFF,
        TEACHER,
        DEVELOPER
    }

    private int id;
    private String username;
    private UserType type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    /**
     * Find user by user_id
     * @param user_id
     * @return {@link User}
     * @throws NoUserFoundException if user not found
     */
    public static User getUser(int user_id) throws NoUserFoundException {
        Connection connection = null;
        try {
            connection = Database.getInstance().getConnection();

            String sql = "SELECT * FROM user WHERE user_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, user_id);

            ResultSet result = stmt.executeQuery();
            if(result.next()) {
                User user = new User();

                user.id = user_id;
                user.username = result.getString("username");
                user.type = UserType.valueOf(result.getString("type"));

                return user;
            } else {
                throw new NoUserFoundException();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            if(connection != null) Database.closeConnection(connection);
        }
    }

}
