package model;

import exception.NoAlumniFoundException;
import exception.NoTrackFoundInAlumniException;
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

    private int alumni_id;
    private String pname_th, fname_th, lname_th, pname_en, fname_en, lname_en, nickname, email, phone, occupation, work_name, avatar;
    private Date birthdate;
    private Alumni.Address address;
    private ArrayList<Alumni.Track> tracks;

    public Alumni() {
        address = new Address();
        tracks = new ArrayList<>();
    }

    public int getAlumni_id() {
        return alumni_id;
    }

    public void setAlumni_id(int alumni_id) {
        this.alumni_id = alumni_id;
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

    public Alumni.Address getAddress() {
        return address;
    }

    public void setAddress(Alumni.Address address) {
        this.address = address;
    }

    public ArrayList<Alumni.Track> getTracks() {
        return tracks;
    }

    public void setTracks(ArrayList<Alumni.Track> tracks) {
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
     * @param alumni New {@link Alumni} object to add
     */
    public static void addAlumni(Alumni alumni) throws NoTrackFoundInAlumniException {
        if(alumni.tracks.size() <= 0) {
            throw new NoTrackFoundInAlumniException();
        }

        Connection connection = null;

        try {
            connection = Database.getInstance().getConnection();

            String sql = "INSERT INTO alumni VALUES (0, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, alumni.pname_th);
            stmt.setString(2, alumni.fname_th);
            stmt.setString(3, alumni.lname_th);
            stmt.setString(4, alumni.pname_en);
            stmt.setString(5, alumni.fname_en);
            stmt.setString(6, alumni.lname_en);
            stmt.setString(7, alumni.nickname);
            stmt.setDate(8, (java.sql.Date) alumni.birthdate);
            stmt.setString(9, alumni.email);
            stmt.setString(10, alumni.phone);
            stmt.setString(11, alumni.occupation);
            stmt.setString(12, alumni.work_name);
            stmt.setString(13, alumni.avatar);
            stmt.executeUpdate();

            sql = "INSERT INTO alumni_address(`alumni_id`) SELECT `alumni_id` FROM alumni WHERE alumni.pname_th = ? AND alumni.fname_th = ? AND alumni.lname_th = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, alumni.pname_th);
            stmt.setString(2, alumni.fname_th);
            stmt.setString(3, alumni.lname_th);
            stmt.executeUpdate();

            for(Alumni.Track track : alumni.tracks) {
                User user = getOrCreateUser(connection, String.valueOf(track.student_id));

                sql = "INSERT INTO alumni_track SELECT `alumni_id`, ?, ?, ?, ?, ? FROM alumni WHERE alumni.pname_th = ? AND alumni.fname_th = ? AND alumni.lname_th = ?";
                stmt = connection.prepareStatement(sql);
                stmt.setInt(1, track.student_id);
                stmt.setInt(2, track.generation);
                stmt.setInt(3, track.track.getTrack_id());
                stmt.setInt(4, track.starteduyear);
                stmt.setInt(5, track.endeduyear);
                stmt.setString(6, alumni.pname_th);
                stmt.setString(7, alumni.fname_th);
                stmt.setString(8, alumni.lname_th);
                stmt.executeUpdate();

                sql = "INSERT INTO alumni_user_pivot SELECT `user_id`, `alumni_id` FROM alumni, user WHERE user.user_id = ? AND alumni.pname_th = ? AND alumni.fname_th = ? AND alumni.lname_th = ?";
                stmt = connection.prepareStatement(sql);
                stmt.setInt(1, user.getId());
                stmt.setString(2, alumni.pname_th);
                stmt.setString(3, alumni.fname_th);
                stmt.setString(4, alumni.lname_th);
                stmt.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if(connection != null) Database.closeConnection(connection);
        }
    }

    /**
     * Add new track to alumni
     * @param alumni Target {@link Alumni} object
     * @param track New {@link Alumni.Track} object to add to alumni
     */
    public static void addTracks(Alumni alumni, Alumni.Track track) {
        Connection connection = null;

        try {
            connection = Database.getInstance().getConnection();

            User user = getOrCreateUser(connection, String.valueOf(track.student_id));

            String sql = "INSERT INTO alumni_track VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, alumni.alumni_id);
            stmt.setInt(2, track.student_id);
            stmt.setInt(3, track.generation);
            stmt.setInt(4, track.track.getTrack_id());
            stmt.setInt(5, track.starteduyear);
            stmt.setInt(6, track.endeduyear);
            stmt.executeUpdate();

            sql = "INSERT INTO alumni_user_pivot " +
                    "SELECT `user_id`, `alumni_id` " +
                    "FROM alumni, user " +
                    "WHERE user.user_id = ? AND alumni.pname_th = ? AND alumni.fname_th = ? AND alumni.lname_th = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, user.getId());
            stmt.setString(2, alumni.pname_th);
            stmt.setString(3, alumni.fname_th);
            stmt.setString(4, alumni.lname_th);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if(connection != null) Database.closeConnection(connection);
        }
    }

    /**
     *  Get user, if not exist create new one.
     *  @param username
     *  @param connection
     *  @return User
     */
    private static User getOrCreateUser(Connection connection, String username) {
        User user;

        try {
            user = User.getUserByUsername(username);
            if(user.getType() != User.UserType.ALUMNI) throw new RuntimeException("Type not match, manually delete in database or change type of user");
        } catch (NoUserFoundException ex) {
            user = new User();
            user.setUsername(username);
            user.setType(User.UserType.ALUMNI);
            User.addUser(user, "itkmitl");

            user = User.getUserByUsername(username);
        }

        return user;
    }

    /**
     * Update alumni data
     * @param alumni Updated {@link Alumni} object
     */
    public static void updateAlumni(Alumni alumni) throws NoAlumniFoundException {
        Connection connection = null;

        try {
            connection = Database.getInstance().getConnection();

            String sql = "UPDATE alumni " +
                    "SET pname_th = ?, fname_th = ?, lname_th = ?, " +
                    "pname_en = ?, fname_en = ?, lname_en = ?, " +
                    "nickname = ?, birthdate = ?, email = ?, phone = ?, " +
                    "occupation = ?, work_name = ?, avatar = ? " +
                    "WHERE alumni_id = ?";
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
            stmt.setInt(14, alumni.alumni_id);
            int result = stmt.executeUpdate();

            if(result == 0) throw new NoAlumniFoundException();

            sql = "UPDATE alumni_address " +
                    "SET address = ?, district = ?, amphure = ?, province_id = ?, zipcode = ? " +
                    "WHERE alumni_id = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, alumni.address.address);
            stmt.setString(2, alumni.address.district);
            stmt.setString(3, alumni.address.amphure);
            if(alumni.address.province != null) stmt.setInt(4, alumni.address.province.getProvince_id());
            else stmt.setNull(4, Types.INTEGER);
            stmt.setString(5, alumni.address.zipcode);
            stmt.setInt(6, alumni.alumni_id);
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if(connection != null) Database.closeConnection(connection);
        }
    }

    /**
     * Update track data
     * @param track Update {@link Alumni.Track} object
     */
    public static void updateAlumniTrack(Alumni.Track track) {
        Connection connection = null;

        try {
            connection = Database.getInstance().getConnection();

            String sql = "UPDATE alumni_track " +
                    "SET track_id = ?, starteduyear = ?, endeduyear = ? " +
                    "WHERE student_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, track.track.getTrack_id());
            stmt.setInt(2, track.starteduyear);
            stmt.setInt(3, track.endeduyear);
            stmt.setInt(4, track.student_id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if(connection != null) Database.closeConnection(connection);
        }
    }

    /**
     * Get All alumni object.
     * @return {@link ArrayList}&lt;{@link Alumni}&gt;
     */
    public static ArrayList<Alumni> getAllAlumni() {
        Connection connection = null;
        try {
            connection = Database.getInstance().getConnection();

            String sql = "SELECT * FROM alumni JOIN alumni_address ON alumni.alumni_id = alumni_address.alumni_id";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet result = stmt.executeQuery();

            ArrayList<Alumni> alumnis = new ArrayList<>();
            while (result.next()) {
                Alumni alumni = new Alumni();

                alumni.alumni_id = result.getInt("alumni_id");
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

                alumni.address.address = result.getString("address");
                alumni.address.district = result.getString("district");
                alumni.address.amphure = result.getString("amphure");
                alumni.address.province = Province.getProvinceByProvinceId(result.getInt("province_id"));
                alumni.address.zipcode = result.getString("zipcode");

                String sql2 = "SELECT * FROM alumni_track WHERE alumni_id = ?";
                PreparedStatement stmt2 = connection.prepareStatement(sql2);
                stmt2.setInt(1, alumni.alumni_id);

                ResultSet result2 = stmt2.executeQuery();
                while(result2.next()) {
                    Alumni.Track track = new Alumni.Track();
                    track.setStudent_id(result2.getInt("student_id"));
                    track.setGeneration(result2.getInt("generation"));
                    track.setTrack(model.Track.getTrack(result2.getInt("track_id")));
                    track.setStarteduyear(result2.getInt("starteduyear"));
                    track.setEndeduyear(result2.getInt("endeduyear"));

                    alumni.tracks.add(track);
                }

                alumnis.add(alumni);
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
     * @param alumni_id
     * @return {@link Alumni}
     * @throws NoAlumniFoundException
     */
    public static Alumni getAlumniById(int alumni_id) throws NoAlumniFoundException {
        Connection connection = null;
        try {
            connection = Database.getInstance().getConnection();

            String sql = "SELECT * " +
                    "FROM alumni " +
                    "JOIN alumni_address ON alumni.alumni_id = alumni_address.alumni_id " +
                    "JOIN alumni_track ON alumni.alumni_id = alumni_track.alumni_id " +
                    "WHERE alumni.alumni_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, alumni_id);

            ResultSet result = stmt.executeQuery();
            if(result.next()) {
                Alumni alumni = new Alumni();

                alumni.alumni_id = result.getInt("alumni_id");
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

                alumni.address.address = result.getString("address");
                alumni.address.district = result.getString("district");
                alumni.address.amphure = result.getString("amphure");
                int province_id;
                if((province_id = result.getInt("province_id")) != 0)
                    alumni.address.province = Province.getProvinceByProvinceId(province_id);
                else alumni.address.province = null;
                alumni.address.zipcode = result.getString("zipcode");

                while(true) {
                    Alumni.Track track = new Alumni.Track();
                    track.setStudent_id(result.getInt("student_id"));
                    track.setGeneration(result.getInt("generation"));
                    track.setTrack(model.Track.getTrack(result.getInt("track_id")));
                    track.setStarteduyear(result.getInt("starteduyear"));
                    track.setEndeduyear(result.getInt("endeduyear"));

                    alumni.tracks.add(track);
                    if(!result.next()) break;
                }

                return alumni;
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
     * @return {@link Alumni}
     * @throws NoAlumniFoundException
     */
    public static Alumni getAlumniByUserId(int user_id) throws NoAlumniFoundException {
        Connection connection = null;
        try {
            connection = Database.getInstance().getConnection();

            String sql = "SELECT alumni_id FROM alumni_user_pivot WHERE user_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, user_id);

            ResultSet result = stmt.executeQuery();
            if(result.next()) {
                return getAlumniById(result.getInt("alumni_id"));
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
     * Get alumni by student_id
     * @param student_id
     * @return {@link Alumni}
     * @throws NoAlumniFoundException
     */
    public static Alumni getAlumniByStudentId(int student_id) throws NoAlumniFoundException {
        Connection connection = null;

        try {
            connection = Database.getInstance().getConnection();

            String sql = "SELECT alumni_id FROM alumni_track WHERE student_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, student_id);

            ResultSet result = stmt.executeQuery();
            if(result.next()) {
                return getAlumniById(result.getInt("alumni_id"));
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
     * Remove alumni by alumni_id
     * @param alumni_id
     * @throws NoAlumniFoundException
     */
    public static void removeAlumniById(int alumni_id) throws NoAlumniFoundException {
        Connection connection = null;

        try {
            connection = Database.getInstance().getConnection();

            String sql = "DELETE alumni_user_pivot, alumni, user " +
                    "FROM alumni_user_pivot " +
                    "JOIN alumni ON alumni_user_pivot.alumni_id = alumni.alumni_id " +
                    "JOIN user ON alumni_user_pivot.user_id = user.user_id " +
                    "WHERE alumni_user_pivot.alumni_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, alumni_id);
            int result = stmt.executeUpdate();

            if(result <= 0) throw new NoAlumniFoundException();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if(connection != null) Database.closeConnection(connection);
        }
    }

    /**
     * Remove alumni by student_id
     * @param student_id
     * @throws NoAlumniFoundException
     */
    public static void removeAlumniByStudentId(int student_id) throws NoAlumniFoundException {
        Connection connection = null;

        try {
            connection = Database.getInstance().getConnection();

            String sql = "SELECT * FROM alumni_track WHERE student_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, student_id);

            ResultSet result = stmt.executeQuery();

            if(result.next()) removeAlumniById(result.getInt("alumni_id"));
            else throw new NoAlumniFoundException();
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
        Connection connection = null;

        try {
            connection = Database.getInstance().getConnection();

            String sql = "DELETE alumni_user_pivot, alumni, user " +
                    "FROM alumni_user_pivot " +
                    "JOIN alumni ON alumni_user_pivot.alumni_id = alumni.alumni_id " +
                    "JOIN user ON alumni_user_pivot.user_id = user.user_id " +
                    "WHERE alumni_user_pivot.user_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, user_id);
            int result = stmt.executeUpdate();

            if(result <= 0) throw new NoAlumniFoundException();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if(connection != null) Database.closeConnection(connection);
        }
    }

    /**
     * Remove alumni track by student_id
     * @param student_id
     * @throws NoTrackFoundInAlumniException
     */
    public static void removeAlumniTrackByStudentId(int student_id) throws NoTrackFoundInAlumniException {
        Connection connection = null;

        try {
            connection = Database.getInstance().getConnection();

            String sql = "DELETE FROM alumni_track WHERE student_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, student_id);
            int result = stmt.executeUpdate();

            if(result <= 0) throw new NoTrackFoundInAlumniException();

            sql = "DELETE alumni_user_pivot, user " +
                "FROM alumni_user_pivot " +
                "JOIN user ON alumni_user_pivot.user_id = user.user_id " +
                "WHERE user.username = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, String.valueOf(student_id));
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if(connection != null) Database.closeConnection(connection);
        }
    }

    /**
     * Track Model
     * for alumni_track table
     */
    public static class Track {

        private int student_id, generation, starteduyear, endeduyear;
        private model.Track track;

        public int getStudent_id() {
            return student_id;
        }

        public void setStudent_id(int student_id) {
            this.student_id = student_id;
        }

        public int getGeneration() {
            return generation;
        }

        public void setGeneration(int generation) {
            this.generation = generation;
        }

        public int getStarteduyear() {
            return starteduyear;
        }

        public void setStarteduyear(int starteduyear) {
            this.starteduyear = starteduyear;
        }

        public int getEndeduyear() {
            return endeduyear;
        }

        public void setEndeduyear(int endeduyear) {
            this.endeduyear = endeduyear;
        }

        public model.Track getTrack() {
            return track;
        }

        public void setTrack(model.Track track) {
            this.track = track;
        }
    }

    /**
     * Address Model
     * for alumni_address table
     */
    public static class Address {

        private String address, amphure, district, zipcode;
        private Province province;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAmphure() {
            return amphure;
        }

        public void setAmphure(String amphure) {
            this.amphure = amphure;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public Province getProvince() {
            return province;
        }

        public void setProvince(Province province) {
            this.province = province;
        }

        public String getZipcode() {
            return zipcode;
        }

        public void setZipcode(String zipcode) {
            this.zipcode = zipcode;
        }

    }

}
