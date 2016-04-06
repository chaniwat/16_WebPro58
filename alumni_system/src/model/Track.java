package model;

import exception.NoTrackFoundException;
import model.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by meranote on 4/5/2016 AD.
 */
public class Track {

    private int track_id;
    private Curriculum curriculum;
    private String name_th, name_en;
    private int starteduyear;

    public Track() {
        curriculum = new Curriculum();
    }

    public int getStarteduyear() {
        return starteduyear;
    }

    public void setStarteduyear(int starteduyear) {
        this.starteduyear = starteduyear;
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

    public static Track getTrack(int track_id) throws NoTrackFoundException {
        Connection connection = null;
        try {
            connection = Database.getInstance().getConnection();

            String sql = "SELECT track.track_id, track.name_th AS 'track_name_th', track.name_en AS 'track_name_en', " +
                    "curriculum.curriculum_id, curriculum.name_th AS 'curriculum_name_th', curriculum.name_en AS 'curriculum_name_en' " +
                    "FROM track " +
                    "JOIN curriculum ON track.curriculum_id = curriculum.curriculum_id " +
                    "WHERE track_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, track_id);

            ResultSet result = stmt.executeQuery();
            if(result.next()) {
                Track t = new Track();
                t.track_id = result.getInt("track_id");
                t.name_th = result.getString("track_name_th");
                t.name_en = result.getString("track_name_en");

                Curriculum curriculum = new Curriculum();
                curriculum.setCurriculum_id(result.getInt("curriculum_id"));
                curriculum.setName_th(result.getString("curriculum_name_th"));
                curriculum.setName_en(result.getString("curriculum_name_en"));
                t.curriculum = curriculum;

                return t;
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

    public static ArrayList<Track> getAllTrack() {
        Connection connection = null;
        try {
            connection = Database.getInstance().getConnection();

            String sql = "SELECT track.track_id, track.name_th AS 'track_name_th', track.name_en AS 'track_name_en', " +
                    "curriculum.curriculum_id, curriculum.name_th AS 'curriculum_name_th', curriculum.name_en AS 'curriculum_name_en' " +
                    "FROM track " +
                    "JOIN curriculum ON track.curriculum_id = curriculum.curriculum_id";
            PreparedStatement stmt = connection.prepareStatement(sql);

            ResultSet result = stmt.executeQuery();
            ArrayList<Track> tracks = new ArrayList<>();
            while (result.next()) {
                Track t = new Track();
                t.track_id = result.getInt("track_id");
                t.name_th = result.getString("track_name_th");
                t.name_en = result.getString("track_name_en");

                Curriculum curriculum = new Curriculum();
                curriculum.setCurriculum_id(result.getInt("curriculum_id"));
                curriculum.setName_th(result.getString("curriculum_name_th"));
                curriculum.setName_en(result.getString("curriculum_name_en"));
                t.curriculum = curriculum;

                tracks.add(t);
            }
            return tracks;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            if(connection != null) Database.closeConnection(connection);
        }
    }

}
