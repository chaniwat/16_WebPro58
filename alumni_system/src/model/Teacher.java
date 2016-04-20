package model;

import exception.NoTeacherFoundException;
import exception.NoUserFoundException;
import model.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Teacher model
 * for teacher table
 */
public class Teacher {

    private int teacher_id, user_id;
    private String pname_th, fname_th, lname_th, pname_en, fname_en, lname_en, phone, email;
    private Work.Status work_status;

    public int getTeacher_id() {
        return teacher_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getPname_th() {
        return pname_th;
    }

    public void setPname_th(String pname_th) {
        this.pname_th = pname_th;
    }

    public String getFname_th() {
        return fname_th;
    }

    public void setFname_th(String fname_th) {
        this.fname_th = fname_th;
    }

    public String getLname_th() {
        return lname_th;
    }

    public void setLname_th(String lname_th) {
        this.lname_th = lname_th;
    }

    public String getPname_en() {
        return pname_en;
    }

    public void setPname_en(String pname_en) {
        this.pname_en = pname_en;
    }

    public String getFname_en() {
        return fname_en;
    }

    public void setFname_en(String fname_en) {
        this.fname_en = fname_en;
    }

    public String getLname_en() {
        return lname_en;
    }

    public void setLname_en(String lname_en) {
        this.lname_en = lname_en;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Work.Status getWork_status() {
        return work_status;
    }

    public void setWork_status(Work.Status work_status) {
        this.work_status = work_status;
    }

    /**
     * Add teacher
     * @param teacher
     */
    public static void addTeacher(Teacher teacher) {
        Connection connection = null;

        try {
            connection = Database.getInstance().getConnection();

            User user;

            try {
                if(teacher.fname_en != null) user = User.getUserByUsername(teacher.fname_en);
                else user = User.getUserByUsername(String.valueOf(teacher.teacher_id));
                if(user.getType() != User.UserType.TEACHER) throw new RuntimeException("Type not match, manually delete in database or change type of user");
            } catch (NoUserFoundException ex) {
                user = new User();
                if(teacher.fname_en != null) user.setUsername(teacher.fname_en);
                else user.setUsername(String.valueOf(teacher.teacher_id));
                user.setType(User.UserType.TEACHER);
                User.addUser(user, "itkmitl");

                if(teacher.fname_en != null) user = User.getUserByUsername(teacher.fname_en);
                else user = User.getUserByUsername(String.valueOf(teacher.teacher_id));
            }

            String sql = "INSERT INTO teacher VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, teacher.teacher_id);
            stmt.setInt(2, user.getId());
            stmt.setString(3, teacher.pname_th);
            stmt.setString(4, teacher.fname_th);
            stmt.setString(5, teacher.lname_th);
            stmt.setString(6, teacher.pname_en);
            stmt.setString(7, teacher.fname_en);
            stmt.setString(8, teacher.lname_en);
            stmt.setString(9, String.valueOf(teacher.work_status));
            stmt.setString(10, teacher.phone);
            stmt.setString(11, teacher.email);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if(connection != null) Database.closeConnection(connection);
        }
    }

    /**
     * Update teacher
     * @param teacher
     */
    public static void updateTeacher(Teacher teacher) throws NoTeacherFoundException {
        Connection connection = null;

        try {
            connection = Database.getInstance().getConnection();

            String sql = "UPDATE teacher " +
                    "SET pname_th = ?, fname_th = ?, lname_th = ?, " +
                    "pname_en = ?, fname_en = ?, lname_th = ?, " +
                    "work_status = ?, phone = ?, email = ? " +
                    "WHERE teacher_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, teacher.pname_th);
            stmt.setString(2, teacher.fname_th);
            stmt.setString(3, teacher.lname_th);
            stmt.setString(4, teacher.pname_en);
            stmt.setString(5, teacher.fname_en);
            stmt.setString(6, teacher.lname_en);
            stmt.setString(7, String.valueOf(teacher.work_status));
            stmt.setString(8, teacher.phone);
            stmt.setString(9, teacher.email);
            stmt.setInt(10, teacher.teacher_id);
            int result = stmt.executeUpdate();

            if(result <= 0) throw new NoTeacherFoundException();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if(connection != null) Database.closeConnection(connection);
        }
    }

    /**
     * Get all teacher
     * @return
     */
    public static ArrayList<Teacher> getAllTeacher() {
        Connection connection = null;

        try {
            connection = Database.getInstance().getConnection();

            String sql = "SELECT * FROM teacher";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet result = stmt.executeQuery();

            ArrayList<Teacher> teachers = new ArrayList<>();
            while (result.next()) {
                teachers.add(buildTeacherObject(result));
            }
            return teachers;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            if(connection != null) Database.closeConnection(connection);
        }
    }

    /**
     * Get teacher by teacher_id
     * @param teacher_id
     * @return
     * @throws NoTeacherFoundException
     */
    public static Teacher getTeacherById(int teacher_id) throws NoTeacherFoundException {
        Connection connection = null;

        try {
            connection = Database.getInstance().getConnection();

            String sql = "SELECT * FROM teacher WHERE teacher_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, teacher_id);

            ResultSet result = stmt.executeQuery();

            if(result.next()) return buildTeacherObject(result);
            else throw new NoTeacherFoundException();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            if(connection != null) Database.closeConnection(connection);
        }
    }

    /**
     * Get teacher by user_id
     * @param user_id
     * @return
     * @throws NoTeacherFoundException
     */
    public static Teacher getTeacherByUserId(int user_id) throws NoTeacherFoundException {
        Connection connection = null;

        try {
            connection = Database.getInstance().getConnection();

            String sql = "SELECT * FROM teacher WHERE user_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, user_id);

            ResultSet result = stmt.executeQuery();

            if(result.next()) return buildTeacherObject(result);
            else throw new NoTeacherFoundException();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            if(connection != null) Database.closeConnection(connection);
        }
    }

    /**
     * Build teacher object
     * @param result
     * @return
     * @throws SQLException
     */
    private static Teacher buildTeacherObject(ResultSet result) throws SQLException {
        Teacher teacher = new Teacher();

        teacher.teacher_id = result.getInt("teacher_id");
        teacher.user_id = result.getInt("user_id");
        teacher.pname_th = result.getString("pname_th");
        teacher.fname_th = result.getString("fname_th");
        teacher.lname_th = result.getString("lname_th");
        teacher.pname_en = result.getString("pname_en");
        teacher.fname_en = result.getString("fname_en");
        teacher.lname_en = result.getString("lname_en");
        teacher.work_status = Work.Status.valueOf(result.getString("work_status"));
        teacher.phone = result.getString("phone");
        teacher.email = result.getString("email");

        return teacher;
    }

    /**
     * Remove teacher by teacher_id
     * @param teacher_id
     * @throws NoTeacherFoundException
     */
    public static void removeTeacherById(int teacher_id) throws NoTeacherFoundException {
        Connection connection = null;

        try {
            connection = Database.getInstance().getConnection();

            String sql = "DELETE teacher, user FROM teacher JOIN user ON teacher.user_id = user.user_id WHERE teacher.teacher_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, teacher_id);
            int result = stmt.executeUpdate();

            if(result <= 0) throw new NoTeacherFoundException();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if(connection != null) Database.closeConnection(connection);
        }
    }

    /**
     * Remove teacher by user_id
     * @param user_id
     * @throws NoTeacherFoundException
     */
    public static void removeTeacherByUserId(int user_id) throws NoTeacherFoundException {
        Connection connection = null;

        try {
            connection = Database.getInstance().getConnection();

            String sql = "DELETE teacher, user FROM teacher JOIN user ON teacher.user_id = user.user_id WHERE user.user_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, user_id);
            int result = stmt.executeUpdate();

            if(result <= 0) throw new NoTeacherFoundException();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if(connection != null) Database.closeConnection(connection);
        }
    }

}
