package com.alumnisystem.factory;

import com.alumnisystem.exception.UserNotFound;
import com.alumnisystem.model.*;
import com.sun.istack.internal.NotNull;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;

/**
 * User Model Factory
 */
public class UserFactory extends ModelFactory<User> {

    public UserFactory() {
        super();
    }

    @Override
    public ArrayList<User> all() {
        try {
            statement.setStatement("SELECT * FROM user JOIN user_alias ON user.id = user_alias.user_id");

            result = statement.executeQuery();
            ArrayList<User> users = new ArrayList<>();
            while (result.next()) {
                users.add(buildObject(new User(), result));
            }
            return users;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Find user by user_id
     * @param id
     * @return {@link User}
     * @throws UserNotFound if user not found
     */
    @Override
    public User find(@NotNull int id) throws UserNotFound {
        try {
            statement.setStatement("SELECT * FROM user JOIN user_alias ON user.id = user_alias.user_id WHERE user.id = ?")
                    .setInt(id);

            result = statement.executeQuery();
            if(result.next()) {
                return buildObject(new User(), result);
            } else {
                throw new UserNotFound();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Get user by username
     * @param username
     * @return
     * @throws UserNotFound
     */
    public User findByUsername(@NotNull String username) throws UserNotFound {
        try {
            statement.setStatement("SELECT * FROM user JOIN user_alias ON user.id = user_alias.user_id WHERE user.id = " +
                    "(SELECT user_alias.user_id FROM user_alias WHERE username = ?)")
                    .setString(username);

            result = statement.executeQuery();
            if(result.next()) {
                return buildObject(new User(), result);
            } else {
                throw new UserNotFound();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Build and return user object
     * @param result
     * @return
     * @throws SQLException
     */
    @Override
    User buildObject(User model, ResultSet result) throws SQLException {
        return setObject(model, result, false);
    }

    /**
     * Build and return user object
     * @param result
     * @param skipUsernameAlias
     * @return
     * @throws SQLException
     */
    User setObject(User model, ResultSet result, boolean skipUsernameAlias) throws SQLException {
        model.setId(result.getInt("user.id"));
        model.setPname_th(result.getString("user.pname_th"));
        model.setFname_th(result.getString("user.fname_th"));
        model.setLname_th(result.getString("user.lname_th"));
        model.setPname_en(result.getString("user.pname_en"));
        model.setFname_en(result.getString("user.fname_en"));
        model.setLname_en(result.getString("user.lname_en"));
        model.setPhone(result.getString("user.phone"));
        model.setEmail(result.getString("user.email"));
        model.setType(User.Type.valueOf(result.getString("user.type")));
        model.setAdmin(result.getBoolean("user.admintype"));
        model.setPassword(result.getString("user.password"));

        if(!skipUsernameAlias) {
            while (true) {
                model.getUsernames().add(result.getString("user_alias.username"));
                if(result.next()) {
                    if(result.getInt("user.id") != model.getId()) {
                        result.previous();
                        break;
                    }
                } else {
                    break;
                }
            }
        }

        return model;
    }

    @Override
    @Deprecated
    public User create(@NotNull User model) {
        throw new RuntimeException("use each user factory create() instead");
    }

    /**
     * Add new user_detail
     * @param model
     * @param usernames
     * @param password
     */
    User createUser(@NotNull User model, @NotNull ArrayList<String> usernames, @NotNull String password) {
        try {
            statement.setStatement("INSERT INTO user VALUES (0, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)
                    .setString(String.valueOf(model.getType()))
                    .setBoolean(model.isAdmin())
                    .setString(model.getPname_th())
                    .setString(model.getFname_th())
                    .setString(model.getLname_th())
                    .setString(model.getPname_en())
                    .setString(model.getFname_en())
                    .setString(model.getLname_en())
                    .setString(model.getPhone())
                    .setString(model.getEmail())
                    .setString(BCrypt.hashpw(password, BCrypt.gensalt()));

            statement.executeUpdate();

            result = statement.getStatement().getGeneratedKeys();

            if(result.next()) {
                model.setId(result.getInt(1));
            }

            for(String username : usernames) {
                addUsernameAlias(model, username);
            }

            return model;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }


    @Override
    public User update(@NotNull User model) {
        try {
            statement.setStatement("UPDATE user " +
                    "SET pname_th = ?, fname_th = ?, lname_th = ?, " +
                    "pname_en = ?, fname_en = ?, lname_en = ?, " +
                    "phone = ?, email = ? " +
                    "WHERE id = ?")
                    .setString(model.getPname_th())
                    .setString(model.getFname_th())
                    .setString(model.getLname_th())
                    .setString(model.getPname_en())
                    .setString(model.getFname_en())
                    .setString(model.getLname_en())
                    .setString(model.getPhone())
                    .setString(model.getEmail())
                    .setInt(model.getId());

            statement.executeUpdate();

            return model;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    @Deprecated
    public User remove(@NotNull int id) {
        throw new RuntimeException("use each user factory remove() instead");
    }

    /**
     * Remove user by id
     * @param id
     * @throws UserNotFound
     */
    User removeUser(@NotNull int id) throws UserNotFound {
        User user = find(id);

        try {
            statement.setStatement("DELETE FROM user WHERE id = ?")
                    .setInt(id);

            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return user;
    }

    /**
     * Add new username alias
     * @param model
     * @param username
     * @return
     */
    public User addUsernameAlias(@NotNull User model, @NotNull String username) {
        try {
            statement.setStatement("INSERT INTO user_alias VALUES (?, ?)")
                    .setInt(model.getId())
                    .setString(username);

            statement.executeUpdate();

            model.getUsernames().add(username);

            return model;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Remove username alias
     * @param model
     * @param username
     * @return
     */
    public User removeUsernameAlias(@NotNull User model, @NotNull String username) {
        try {
            statement.setStatement("DELETE FROM user_alias WHERE user_id = ? AND username = ?")
                    .setInt(model.getId())
                    .setString(username);
            statement.executeUpdate();

            model.getUsernames().remove(username);

            return model;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Get user password
     * @param id
     * @return
     * @throws UserNotFound
     */
    public String getUserPassword(@NotNull int id) throws UserNotFound {
        try {
            statement.setStatement("SELECT password FROM user WHERE id = ?")
                    .setInt(id);

            ResultSet result = statement.executeQuery();
            if(result.next()) {
                return result.getString("password");
            } else {
                throw new UserNotFound();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Change user password
     * @param id
     * @param password BCrpyt hash password
     * @throws UserNotFound
     */
    public void changeUserPassword(@NotNull int id, @NotNull String password) throws UserNotFound {
        try {
            statement.setStatement("UPDATE user SET password = ? WHERE id = ?")
                    .setString(BCrypt.hashpw(password, BCrypt.gensalt()))
                    .setInt(id);
            int result = statement.executeUpdate();

            if(result <= 0) throw new UserNotFound();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
