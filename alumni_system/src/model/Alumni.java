package model;

import exception.NoAlumniFoundException;
import exception.NoUserFoundException;
import model.database.Database;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

/**
 * Alumni Model
 * for alumni table
 */
public class Alumni implements Serializable {

    private int student_id, user_id;
    private String pname_th, fname_th, lname_th, pname_en, fname_en, lname_en, nickname, email, phone, occupation, work_name, avatar;
    private Date birthdate;
    private Address address;
    private ArrayList<Track> tracks;

    public Alumni() {
        address = new Address();
        tracks = new ArrayList<>();
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getWork_name() {
        return work_name;
    }

    public void setWork_name(String work_name) {
        this.work_name = work_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public ArrayList<Track> getTracks() {
        return tracks;
    }

    public void setTracks(ArrayList<Track> tracks) {
        this.tracks = tracks;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    /**
     * Add new alumni
     * @param alumni
     */
    public static void addNewAlumni(Alumni alumni) {
        Connection connection = null;
        try {
            connection = Database.getInstance().getConnection();

            User user = null;
            try {
                user = User.getUserByID(alumni.student_id);
                if(user.getType() != User.UserType.ALUMNI) throw new RuntimeException("Type not match, manually delete in database or change type of user");
            } catch (NoUserFoundException ex) {
                user = new User();
                user.setUsername(String.valueOf(alumni.student_id));
                user.setType(User.UserType.ALUMNI);
                User.addUser(user, "itkmitl");

                user = User.getUserByUsername(String.valueOf(alumni.student_id));
            }

            alumni.user_id = user.getId();

            String sql = "INSERT INTO alumni VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, alumni.student_id);
            stmt.setInt(2, alumni.user_id);
            stmt.setString(3, alumni.pname_th);
            stmt.setString(4, alumni.fname_th);
            stmt.setString(5, alumni.lname_th);
            stmt.setString(6, alumni.pname_en);
            stmt.setString(7, alumni.fname_en);
            stmt.setString(8, alumni.lname_en);
            stmt.setString(9, alumni.nickname);
            stmt.setDate(10, (java.sql.Date) alumni.birthdate);
            stmt.setString(11, alumni.email);
            stmt.setString(12, alumni.phone);
            stmt.setString(13, alumni.occupation);
            stmt.setString(14, alumni.work_name);
            stmt.setString(15, alumni.avatar);
            stmt.executeUpdate();

            Address.addAddressToStudentId(alumni.student_id, alumni.address);

            for (Track t : alumni.tracks) {
                Alumni.addTracks(alumni, t);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if(connection != null) Database.closeConnection(connection);
        }
    }

    /**
     * Add new track to alumni
     * @param alumni
     * @param track
     */
    public static void addTracks(Alumni alumni, Track track) {
        Connection connection = null;
        try {
            connection = Database.getInstance().getConnection();

            String sql = "INSERT INTO alumni_track VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, alumni.student_id);
            stmt.setInt(2, alumni.user_id);
            stmt.setInt(3, track.getTrack_id());
            stmt.setInt(4, track.getStarteduyear());
            stmt.setInt(5, track.getEndeduyear());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if(connection != null) Database.closeConnection(connection);
        }
    }

    /**
     * Update alumni data
     * @param alumni
     */
    public static void updateAlumni(Alumni alumni) throws NoAlumniFoundException {
        getAlumniByStudentId(alumni.student_id);

        Connection connection = null;
        try {
            connection = Database.getInstance().getConnection();

            String sql = "UPDATE alumni " +
                    "SET pname_th = ?, fname_th = ?, lname_th = ?, " +
                    "pname_en = ?, fname_en = ?, lname_en = ?, " +
                    "nickname = ?, birthdate = ?, email = ?, phone = ?, " +
                    "occupation = ?, work_name = ?, avatar = ? " +
                    "WHERE student_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, alumni.pname_th);
            stmt.setString(2, alumni.fname_th);
            stmt.setString(3, alumni.lname_th);
            stmt.setString(4, alumni.pname_en);
            stmt.setString(5, alumni.fname_en);
            stmt.setString(6, alumni.lname_en);
            stmt.setString(7, alumni.nickname);
            stmt.setDate(8, new java.sql.Date(alumni.getBirthdate().getTime()));
            stmt.setString(9, alumni.email);
            stmt.setString(10, alumni.phone);
            stmt.setString(11, alumni.occupation);
            stmt.setString(12, alumni.work_name);
            stmt.setString(13, alumni.avatar);
            stmt.setInt(14, alumni.student_id);
            stmt.executeUpdate();

            Address.updateAddressToStudentId(alumni.student_id, alumni.address);
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if(connection != null) Database.closeConnection(connection);
        }
    }

    /**
     * Get All alumni object.
     * @return
     */
    public static ArrayList<Alumni> getAllAlumni() {
        Connection connection = null;
        try {
            connection = Database.getInstance().getConnection();

            String sql = "SELECT * FROM alumni";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet result = stmt.executeQuery();

            ArrayList<Alumni> alumnis = new ArrayList<>();
            while (result.next()) {
                alumnis.add(buildAlumniObject(connection, result));
            }

            return alumnis;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            if(connection != null) Database.closeConnection(connection);
        }
    }

    /**
     * Get alumni by student_id
     * @param student_id
     * @return
     */
    public static Alumni getAlumniByStudentId(int student_id) throws NoAlumniFoundException {
        Connection connection = null;
        try {
            connection = Database.getInstance().getConnection();

            String sql = "SELECT * FROM alumni WHERE student_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, student_id);

            ResultSet result = stmt.executeQuery();
            if(result.next()) {
                return buildAlumniObject(connection, result);
            } else {
                throw new NoAlumniFoundException();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            if(connection != null) Database.closeConnection(connection);
        }
    }

    /**
     * Get alumni by user_id
     * @param user_id
     * @return
     */
    public static Alumni getAlumniByUserId(int user_id) throws NoAlumniFoundException {
        Connection connection = null;
        try {
            connection = Database.getInstance().getConnection();

            String sql = "SELECT * FROM alumni WHERE user_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, user_id);

            ResultSet result = stmt.executeQuery();
            if(result.next()) {
                return buildAlumniObject(connection, result);
            } else {
                throw new NoAlumniFoundException();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            if(connection != null) Database.closeConnection(connection);
        }
    }

    /**
     * Build alumni object and return
     * @param connection
     * @param result
     * @return
     * @throws SQLException
     */
    private static Alumni buildAlumniObject(Connection connection, ResultSet result) throws SQLException {
        Alumni alumni = new Alumni();

        alumni.student_id = result.getInt("student_id");
        alumni.user_id = result.getInt("user_id");
        alumni.pname_th = result.getString("pname_th");
        alumni.fname_th = result.getString("fname_th");
        alumni.lname_th = result.getString("lname_th");
        alumni.pname_en = result.getString("pname_en");
        alumni.fname_en = result.getString("fname_en");
        alumni.lname_en = result.getString("lname_en");
        alumni.nickname = result.getString("nickname");
        alumni.birthdate = result.getDate("birthdate");
        alumni.email = result.getString("email");
        alumni.phone = result.getString("phone");
        alumni.occupation = result.getString("occupation");
        alumni.work_name = result.getString("work_name");
        alumni.avatar = result.getString("avatar");

        alumni.address = Address.getAddressByStudentId(alumni.student_id);

        String sql = "SELECT alumni_track.starteduyear, alumni_track.endeduyear, " +
                "track.track_id, track.name_th AS 'track_name_th', track.name_en AS 'track_name_en', " +
                "curriculum.curriculum_id, curriculum.name_th AS 'curriculum_name_th', curriculum.name_en AS 'curriculum_name_en' " +
                "FROM alumni_track " +
                "JOIN track ON alumni_track.track_id = track.track_id " +
                "JOIN curriculum ON track.curriculum_id = curriculum.curriculum_id " +
                "WHERE student_id = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, alumni.student_id);
        ResultSet alu_result = stmt.executeQuery();

        while (alu_result.next()) {
            Track track = new Track();
            track.setTrack_id(alu_result.getInt("track_id"));
            track.setName_th(alu_result.getString("track_name_th"));
            track.setName_en(alu_result.getString("track_name_en"));
            track.setStarteduyear(alu_result.getInt("starteduyear"));
            track.setEndeduyear(alu_result.getInt("endeduyear"));

            Curriculum curriculum = new Curriculum();
            curriculum.setCurriculum_id(alu_result.getInt("curriculum_id"));
            curriculum.setName_th(alu_result.getString("curriculum_name_th"));
            curriculum.setName_en(alu_result.getString("curriculum_name_en"));

            track.setCurriculum(curriculum);

            alumni.tracks.add(track);
        }

        return alumni;
    }

    /**
     * Remove alumni by student_id
     * @param student_id
     * @throws NoAlumniFoundException
     */
    public static void removeAlumniByStudentId(int student_id) throws NoAlumniFoundException {
        getAlumniByStudentId(student_id);

        Connection connection = null;
        try {
            connection = Database.getInstance().getConnection();

            String sql = "DELETE FROM alumni WHERE student_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, student_id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if(connection != null) Database.closeConnection(connection);
        }
    }

    /**
     * Remove alumni by user_id
     * @param user_id
     * @throws NoAlumniFoundException
     */
    public static void removeAlumniByUserId(int user_id) throws NoAlumniFoundException {
        getAlumniByUserId(user_id);

        Connection connection = null;
        try {
            connection = Database.getInstance().getConnection();

            String sql = "DELETE FROM alumni WHERE user_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, user_id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if(connection != null) Database.closeConnection(connection);
        }
    }

}
