package model;

import exception.NoTrackFoundException;
import model.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Track model
 * for track table
 */
public class Track {

    private int track_id;
    private Curriculum curriculum;
    private String name_th, name_en;

    public Track() {
        curriculum = new Curriculum();
    }

    public int getTrack_id() {
        return track_id;
    }

    public void setTrack_id(int track_id) {
        this.track_id = track_id;
    }

    public Curriculum getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(Curriculum curriculum) {
        this.curriculum = curriculum;
    }

    public String getName_th() {
        return name_th;
    }

    public void setName_th(String name_th) {
        this.name_th = name_th;
    }

    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    /**
     * Add new track
     * @param track
     */
    public static void addTrack(Track track) {
        Connection connection = null;

        try {
            connection = Database.getInstance().getConnection();

            String sql = "INSERT INTO track VALUES (0, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, track.name_th);
            stmt.setString(2, track.name_en);
            stmt.setInt(3, track.curriculum.getCurriculum_id());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if(connection != null) Database.closeConnection(connection);
        }
    }

    /**
     * Update track
     * @param track Updated {@link Track} object
     */
    public static void updateTrack(Track track) {
        Connection connection = null;

        try {
            connection = Database.getInstance().getConnection();

            String sql = "UPDATE track " +
                    "SET name_th = ?, name_en = ?, curriculum_id = ? " +
                    "WHERE track_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, track.name_th);
            stmt.setString(2, track.name_en);
            stmt.setInt(3, track.curriculum.getCurriculum_id());
            stmt.setInt(4, track.getTrack_id());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if(connection != null) Database.closeConnection(connection);
        }
    }

    /**
     * Get track by track_id
     * @param track_id
     * @return
     * @throws NoTrackFoundException
     */
    public static Track getTrackById(int track_id) throws NoTrackFoundException {
        Connection connection = null;
        try {
            connection = Database.getInstance().getConnection();

            String sql = "SELECT * FROM track JOIN curriculum ON track.curriculum_id = curriculum.curriculum_id WHERE track_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, track_id);

            ResultSet result = stmt.executeQuery();
            if(result.next()) {
                return buildTrackObject(result);
            } else {
                throw new NoTrackFoundException();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            if(connection != null) Database.closeConnection(connection);
        }
    }

    /**
     * Get all track
     * @return
     */
    public static ArrayList<Track> getAllTrack() {
        Connection connection = null;
        try {
            connection = Database.getInstance().getConnection();

            String sql = "SELECT * FROM track LEFT JOIN curriculum ON track.curriculum_id = curriculum.curriculum_id";
            PreparedStatement stmt = connection.prepareStatement(sql);

            ResultSet result = stmt.executeQuery();
            ArrayList<Track> tracks = new ArrayList<>();
            while (result.next()) {
                tracks.add(buildTrackObject(result));
            }

            return tracks;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            if(connection != null) Database.closeConnection(connection);
        }
    }

    /**
     * Build track object
     * @param result
     * @return
     * @throws SQLException
     */
    private static Track buildTrackObject(ResultSet result) throws SQLException {
        Track t = new Track();
        t.track_id = result.getInt("track_id");
        t.name_th = result.getString("track.name_th");
        t.name_en = result.getString("track.name_en");

        Curriculum curriculum = new Curriculum();
        curriculum.setCurriculum_id(result.getInt("curriculum_id"));
        curriculum.setName_th(result.getString("curriculum.name_th"));
        curriculum.setName_en(result.getString("curriculum.name_en"));
        t.curriculum = curriculum;

        return t;
    }

}
