package model;

import exception.NoStaffFoundException;
import exception.NoUserFoundException;
import model.Work;
import model.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by meranote on 4/5/2016 AD.
 */
public class Staff {

    private int staff_id, user_id;
    private String pname_th, fname_th, lname_th, pname_en, fname_en, lname_en, phone, email;
    private Work.Section section;

    public int getStaff_id() {
        return staff_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setStaff_id(int staff_id) {
        this.staff_id = staff_id;
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

    public Work.Section getSection() {
        return section;
    }

    public void setSection(Work.Section section) {
        this.section = section;
    }

    /**
     * Add new staff
     * @param staff
     */
    public static void addStaff(Staff staff) {
        Connection connection = null;

        try {
            connection = Database.getInstance().getConnection();

            User user;
            try {
                if(staff.fname_en != null) user = User.getUserByUsername(staff.fname_en);
                else user = User.getUserByUsername(String.valueOf(staff.staff_id));
                if(user.getType() != User.UserType.STAFF) throw new RuntimeException("Type not match, manually delete in database or change type of user");
            } catch (NoUserFoundException ex) {
                user = new User();
                if(staff.fname_en != null) user.setUsername(staff.fname_en);
                else user.setUsername(String.valueOf(staff.staff_id));
                user.setType(User.UserType.STAFF);
                User.addUser(user, "itkmitl");

                if(staff.fname_en != null) user = User.getUserByUsername(staff.fname_en);
                else user = User.getUserByUsername(String.valueOf(staff.staff_id));
            }

            String sql = "INSERT INTO staff VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, staff.staff_id);
            stmt.setInt(2, user.getId());
            stmt.setString(3, staff.pname_th);
            stmt.setString(4, staff.fname_th);
            stmt.setString(5, staff.lname_th);
            stmt.setString(6, staff.pname_en);
            stmt.setString(7, staff.fname_en);
            stmt.setString(8, staff.lname_en);
            stmt.setString(9, staff.phone);
            stmt.setString(10, staff.email);
            stmt.setInt(11, staff.section.getSection_id());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if(connection != null) Database.closeConnection(connection);
        }
    }

    /**
     * Update staff
     * @param staff
     * @throws NoStaffFoundException
     */
    public static void updateStaff(Staff staff) throws NoStaffFoundException {
        Connection connection = null;

        try {
            connection = Database.getInstance().getConnection();

            String sql = "UPDATE staff " +
                    "SET pname_th = ?, fname_th = ?, lname_th = ?, " +
                    "pname_en = ?, fname_en = ?, lname_en = ?, " +
                    "phone = ?, email = ?, section_id = ? " +
                    "WHERE staff_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, staff.pname_th);
            stmt.setString(2, staff.fname_th);
            stmt.setString(3, staff.lname_th);
            stmt.setString(4, staff.pname_en);
            stmt.setString(5, staff.fname_en);
            stmt.setString(6, staff.lname_en);
            stmt.setString(7, staff.phone);
            stmt.setString(8, staff.email);
            stmt.setInt(9, staff.section.getSection_id());
            stmt.setInt(10, staff.staff_id);
            int result = stmt.executeUpdate();

            if(result <= 0) throw new NoStaffFoundException();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if(connection != null) Database.closeConnection(connection);
        }
    }

    /**
     * Get all staff
     * @return
     */
    public static ArrayList<Staff> getAllStaff() {
        Connection connection = null;

        try {
            connection = Database.getInstance().getConnection();

            String sql = "SELECT * FROM staff LEFT JOIN work_section ON staff.section_id = work_section.section_id";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet result = stmt.executeQuery();

            ArrayList<Staff> staffs = new ArrayList<>();
            while (result.next()) {
                staffs.add(buildStaffObject(result));
            }
            return staffs;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            if(connection != null) Database.closeConnection(connection);
        }
    }

    /**
     * Get staff by staff_id
     * @param staff_id
     * @return
     * @throws NoStaffFoundException
     */
    public static Staff getStaffById(int staff_id) throws NoStaffFoundException {
        Connection connection = null;

        try {
            connection = Database.getInstance().getConnection();

            String sql = "SELECT * FROM staff JOIN work_section ON staff.section_id = work_section.section_id WHERE staff_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, staff_id);
            ResultSet result = stmt.executeQuery();

            if(result.next()) return buildStaffObject(result);
            else throw new NoStaffFoundException();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            if(connection != null) Database.closeConnection(connection);
        }
    }

    /**
     * Get staff by user_id
     * @param user_id
     * @return
     * @throws NoStaffFoundException
     */
    public static Staff getStaffByUserId(int user_id) throws NoStaffFoundException {
        Connection connection = null;

        try {
            connection = Database.getInstance().getConnection();

            String sql = "SELECT * FROM staff JOIN work_section ON staff.section_id = work_section.section_id WHERE user_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, user_id);
            ResultSet result = stmt.executeQuery();

            if(result.next()) return buildStaffObject(result);
            else throw new NoStaffFoundException();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            if(connection != null) Database.closeConnection(connection);
        }
    }

    /**
     * Build staff object
     * @param result
     * @return
     * @throws SQLException
     */
    private static Staff buildStaffObject(ResultSet result) throws SQLException {
        Staff staff = new Staff();

        staff.staff_id = result.getInt("staff_id");
        staff.pname_th = result.getString("pname_th");
        staff.fname_th = result.getString("fname_th");
        staff.lname_th = result.getString("lname_th");
        staff.pname_en = result.getString("pname_en");
        staff.fname_en = result.getString("fname_en");
        staff.lname_en = result.getString("lname_en");
        staff.phone = result.getString("phone");
        staff.email = result.getString("email");
        staff.section = Work.Section.getSectionById(result.getInt("section_id"));

        return staff;
    }

    /**
     * Remove staff by staff_id
     * @param staff_id
     * @throws NoStaffFoundException
     */
    public static void removeStaffById(int staff_id) throws NoStaffFoundException {
        Connection connection = null;

        try {
            connection = Database.getInstance().getConnection();

            String sql = "DELETE staff, user FROM staff JOIN user ON staff.user_id = user.user_id WHERE staff.staff_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, staff_id);
            int result = stmt.executeUpdate();

            if(result <= 0) throw new NoStaffFoundException();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if(connection != null) Database.closeConnection(connection);
        }
    }

    /**
     * Remove staff by user_id
     * @param user_id
     * @throws NoStaffFoundException
     */
    public static void removeStaffByUserId(int user_id) throws NoStaffFoundException {
        Connection connection = null;

        try {
            connection = Database.getInstance().getConnection();

            String sql = "DELETE staff, user FROM staff JOIN user ON staff.user_id = user.user_id WHERE user.user_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, user_id);
            int result = stmt.executeUpdate();

            if(result <= 0) throw new NoStaffFoundException();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if(connection != null) Database.closeConnection(connection);
        }
    }

}
