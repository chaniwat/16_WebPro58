package model.auth;

import model.User;
import com.alumnisystem.database.Database;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by meranote on 2/9/2016 AD.
 */
public class Authorization {

    private Connection connection;
    private HttpSession session;
    private String authGuard;

    public static final String DEFAULTGUARD = "user";

    public Authorization(HttpSession session) {
        this.session = session;
    }

    public static Authorization getAuthInstance(HttpSession session) {
        return (Authorization) session.getAttribute("auth");
    }

    public void setGuard(String authGuard) {
        this.authGuard = authGuard;
    }

    public boolean isLogin() {
        return session.getAttribute(this.authGuard) != null ? true : false;
    }

    /**
     * Login by username and password, And save user session into authorize authGuard session.
     * @param username
     * @param password
     * @return true if username and password is match , false for nothing.
     */
    public boolean doLogin(String username, String password) {
        try {
            connection = Database.getInstance().getConnection();

            String sql = "SELECT * FROM user WHERE username = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, username);

            ResultSet resultSet = stmt.executeQuery();

            if(resultSet.last() && BCrypt.checkpw(password, resultSet.getString("password"))) {
                User user = new User();
                user.setId(resultSet.getInt("user_id"));
                user.setUsername(username);
                user.setType(User.UserType.valueOf(resultSet.getString("type")));

                session.setAttribute(authGuard, user);
                session.setMaxInactiveInterval(3600);

                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if(connection != null) Database.closeConnection(connection);
        }
    }

    public void doLogout() {
        session.setAttribute(authGuard, null);
    }

    public User getCurrentUser() {
        return (User) session.getAttribute(authGuard);
    }

}
