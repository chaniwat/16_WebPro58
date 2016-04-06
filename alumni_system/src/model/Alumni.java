package model;

import exception.NoAlumniFoundException;
import model.database.Database;
import org.mindrot.jbcrypt.BCrypt;

import javax.xml.crypto.Data;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by meranote on 4/5/2016 AD.
 */
public class Alumni implements Serializable {

    private int student_id, user_id;
    private String pname_th, fname_th, lname_th, pname_en, fname_en, lname_en, nickname, email, phone, occupation, work_name, avatar;
    private Date birthdate;
    private Address address;
    private ArrayList<Track> tracks;

    public Alumni() {
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

    public static void addNewAlumni(Alumni alumni) {
        Connection connection = null;
        try {
            connection = Database.getInstance().getConnection();

            String sql = "INSERT INTO user VALUES (0, ?, ?, 'ALUMNI')";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, String.valueOf(alumni.student_id));
            stmt.setString(2, BCrypt.hashpw("itkmitl", BCrypt.gensalt()));
            stmt.executeUpdate();

            sql = "SELECT user_id FROM user WHERE username = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, String.valueOf(alumni.student_id));

            ResultSet result = stmt.executeQuery();
            result.next();

            int user_id = result.getInt("user_id");

            sql = "INSERT INTO alumni VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, alumni.student_id);
            stmt.setInt(2, user_id);
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

            if(alumni.address != null) {
                sql = "SELECT province_id FROM provinces WHERE name_th = ?";
                stmt = connection.prepareStatement(sql);
                stmt.setString(1, alumni.address.getProvince());
                result = stmt.executeQuery();
                result.next();

                int province_id = result.getInt("province_id");

                sql = "INSERT INTO alumni_address VALUES (?, ?, ?, ?, ?, ?)";
                stmt = connection.prepareStatement(sql);
                stmt.setInt(1, alumni.student_id);
                stmt.setString(2, alumni.address.getAddress());
                stmt.setString(3, alumni.address.getDistrict());
                stmt.setString(4, alumni.address.getAmphure());
                stmt.setInt(5, province_id);
                stmt.setString(6, alumni.address.getZipcode());
                stmt.executeUpdate();
            }

            for (Track t : alumni.tracks) {
                Alumni.addTracks(alumni, t);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if(connection != null) Database.closeConnection(connection);
        }
    }

    public static void addTracks(Alumni alumni, Track track) {
        Connection connection = null;
        try {
            connection = Database.getInstance().getConnection();

            String sql = "INSERT INTO alumni_track VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, alumni.student_id);
            stmt.setInt(2, alumni.user_id);
            stmt.setInt(3, track.getTrack_id());
            stmt.setInt(4, track.getStarteduyear());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if(connection != null) Database.closeConnection(connection);
        }
    }

    public static void updateAlumni(Alumni alumni) {
        // TODO update alumni
    }

    public static Alumni getAlumniByStudentId(int student_id) {
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

    public static Alumni getAlumniByUserId(int user_id) {
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

        String sql = "SELECT address, district, amphure, name_th, zipcode " +
                "FROM alumni_address " +
                "JOIN provinces " +
                "ON alumni_address.province_id = provinces.province_id " +
                "WHERE student_id = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, alumni.student_id);
        result = stmt.executeQuery();

        if(result.next()) {
            Address address = new Address();
            address.setAddress(result.getString("address"));
            address.setDistrict(result.getString("district"));
            address.setAmphure(result.getString("amphure"));
            address.setProvince(result.getString("name_th"));
            address.setZipcode(result.getString("zipcode"));

            alumni.address = address;
        }

        sql = "SELECT alumni_track.starteduyear, " +
                "track.track_id, track.name_th AS 'track_name_th', track.name_en AS 'track_name_en', " +
                "curriculum.curriculum_id, curriculum.name_th AS 'curriculum_name_th', curriculum.name_en AS 'curriculum_name_en' " +
                "FROM alumni_track " +
                "JOIN track ON alumni_track.track_id = track.track_id " +
                "JOIN curriculum ON track.curriculum_id = curriculum.curriculum_id " +
                "WHERE student_id = ?";
        stmt = connection.prepareStatement(sql);
        stmt.setInt(1, alumni.student_id);
        result = stmt.executeQuery();

        while (result.next()) {
            Track track = new Track();
            track.setTrack_id(result.getInt("track_id"));
            track.setName_th(result.getString("track_name_th"));
            track.setName_en(result.getString("track_name_en"));
            track.setStarteduyear(result.getInt("starteduyear"));

            Curriculum curriculum = new Curriculum();
            curriculum.setCurriculum_id(result.getInt("curriculum_id"));
            curriculum.setName_th(result.getString("curriculum_name_th"));
            curriculum.setName_en(result.getString("curriculum_name_en"));

            track.setCurriculum(curriculum);

            alumni.tracks.add(track);
        }

        return alumni;
    }

    public static void main(String[] args) {
        Alumni alumni = Alumni.getAlumniByStudentId(57070029);

        System.out.println(alumni.pname_th + " " + alumni.fname_th + " " + alumni.lname_th);
    }

}
