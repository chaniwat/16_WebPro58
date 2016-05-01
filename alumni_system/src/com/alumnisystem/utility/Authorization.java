package com.alumnisystem.utility;

import com.alumnisystem.database.SuperStatement;
import com.alumnisystem.factory.UserFactory;
import com.alumnisystem.model.User;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Authorization Utility
 */
public class Authorization {

    private SuperStatement statement;
    private HttpSession session;

    public Authorization(Connection connection, HttpSession session) {
        statement = new SuperStatement(connection);
        this.session = session;
    }

    public static Authorization getAuthInstance(HttpServletRequest request) {
        return (Authorization) request.getAttribute("auth");
    }

    /**
     * Login by username and password, And save user session into authorize authGuard session.
     * @param username
     * @param password
     * @return true if username and password is match , false for nothing.
     */
    public boolean doLogin(String username, String password) {
        try {
            statement.setStatement("SELECT * FROM user LEFT JOIN user_alias ON user.id = user_alias.user_id WHERE username = ?")
                    .setString(username);

            ResultSet result = statement.executeQuery();

            if(result.last() && BCrypt.checkpw(password, result.getString("user.password"))) {

                User user = new UserFactory(statement.getConnection()).find(result.getInt("user.id"));

                session.setAttribute("auth.user", user);
                session.setMaxInactiveInterval(3600);

                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void doLogout() {
        session.setAttribute("auth.user", null);
    }

    public boolean isLogin() {
        return session.getAttribute("auth.user") != null;
    }

    public User getCurrentUser() {
        return (User) session.getAttribute("auth.user");
    }

}
