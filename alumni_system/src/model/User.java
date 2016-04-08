package model;

import exception.NoUserFoundException;
import model.database.Database;
import org.mindrot.jbcrypt.BCrypt;

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
     * Add new user
     * @param user
     * @param password
     */
    public static void addUser(User user, String password) {
        Connection connection = null;
        try {
            connection = Database.getInstance().getConnection();

            String sql = "INSERT INTO user VALUES (0, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, user.username);
            stmt.setString(2, BCrypt.hashpw(password, BCrypt.gensalt()));
            stmt.setString(3, String.valueOf(user.type));
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if(connection != null) Database.closeConnection(connection);
        }
    }

    /**
     * Find user by user_id
     * @param user_id
     * @return {@link User}
     * @throws NoUserFoundException if user not found
     */
    public static User getUserByID(int user_id) throws NoUserFoundException {
        Connection connection = null;

        try {
            connection = Database.getInstance().getConnection();

            String sql = "SELECT * FROM user WHERE user_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, user_id);

            ResultSet result = stmt.executeQuery();
            if(result.next()) {
                return buildUserObject(result);
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

    /**
     * Get user by username
     * @param username
     * @return
     * @throws NoUserFoundException
     */
    public static User getUserByUsername(String username) throws NoUserFoundException {
        Connection connection = null;

        try {
            connection = Database.getInstance().getConnection();

            String sql = "SELECT * FROM user WHERE username = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, username);

            ResultSet result = stmt.executeQuery();
            if(result.next()) {
                return buildUserObject(result);
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

    /**
     * Build and return user object
     * @param result
     * @return
     * @throws SQLException
     */
    private static User buildUserObject(ResultSet result) throws SQLException {
        User user = new User();

        user.id = result.getInt("user_id");
        user.username = result.getString("username");
        user.type = UserType.valueOf(result.getString("type"));

        return user;
    }

    /**
     * Remove user by user_id
     * @param user_id
     * @throws NoUserFoundException
     */
    public static void removeUser(int user_id) throws NoUserFoundException {
        getUserByID(user_id);

        Connection connection = null;
        try {
            connection = Database.getInstance().getConnection();

            String sql = "DELETE FROM user WHERE user_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, user_id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if(connection != null) Database.closeConnection(connection);
        }
    }

    /**
     * Get user password
     * @param user_id
     * @return
     * @throws NoUserFoundException
     */
    public static String getUserPassword(int user_id) throws NoUserFoundException {
        Connection connection = null;

        try {
            connection = Database.getInstance().getConnection();

            String sql = "SELECT password FROM user WHERE user_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, user_id);

            ResultSet result = stmt.executeQuery();
            if(result.next()) {
                return result.getString("password");
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

    /**
     * Change user password
     * @param user_id
     * @param password
     * @throws NoUserFoundException
     */
    public static void changeUserPassword(int user_id, String password) throws NoUserFoundException {
        getUserByID(user_id);

        Connection connection = null;
        try {
            connection = Database.getInstance().getConnection();

            String sql = "UPDATE user SET password = ? WHERE user_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, BCrypt.hashpw(password, BCrypt.gensalt()));
            stmt.setInt(2, user_id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
