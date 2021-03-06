package com.alumnisystem.factory;

import com.alumnisystem.exception.TrackNotFound;
import com.alumnisystem.model.Curriculum;
import com.alumnisystem.model.Track;
import com.sun.istack.internal.NotNull;

import java.sql.*;
import java.util.ArrayList;

/**
 * Track Model Factory
 */
public class TrackFactory extends ModelFactory<Track> {

    public TrackFactory() {
        super();
    }

    /**
     * Add new track
     * @param track
     */
    @Override
    public Track create(Track track) {
        try {
            statement.setStatement("INSERT INTO track VALUES (0, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)
                    .setString(track.getName_th())
                    .setString(track.getName_en())
                    .setInt(track.getCurriculum().getId());

            statement.executeUpdate();

            result = statement.getStatement().getGeneratedKeys();

            if(result.next()) {
                track.setId(result.getInt(1));
            }

            return track;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Update track
     * @param track Updated {@link Track} object
     */
    @Override
    public Track update(Track track) {
        try {
            statement.setStatement("UPDATE track " +
                    "SET name_th = ?, name_en = ?, curriculum_id = ? " +
                    "WHERE id = ?")
                    .setString(track.getName_th())
                    .setString(track.getName_en())
                    .setInt(track.getCurriculum().getId())
                    .setInt(track.getId());

            statement.executeUpdate();

            return track;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Get track by id
     * @param id
     * @return
     * @throws TrackNotFound
     */
    @Override
    public Track find(int id) throws TrackNotFound {
        try {
            statement.setStatement("SELECT * FROM track " +
                    "JOIN curriculum ON track.curriculum_id = curriculum.id " +
                    "WHERE track.id = ?")
                    .setInt(id);

            ResultSet result = statement.executeQuery();
            if(result.next()) {
                return buildObject(new Track(), result);
            } else {
                throw new TrackNotFound();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Get track by id
     * @param curriculum_id
     * @return
     */
    public ArrayList<Track> findAllByCurriculumID(int curriculum_id) {
        try {
            statement.setStatement("SELECT * FROM track " +
                    "JOIN curriculum ON track.curriculum_id = curriculum.id " +
                    "WHERE curriculum.id = ?")
                    .setInt(curriculum_id);

            ResultSet result = statement.executeQuery();
            ArrayList<Track> tracks = new ArrayList<>();
            while (result.next()) {
                tracks.add(buildObject(new Track(), result));
            }
            return tracks;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Get all track
     * @return
     */
    @Override
    public ArrayList<Track> all() {
        try {
            statement.setStatement("SELECT * FROM track LEFT JOIN curriculum ON track.curriculum_id = curriculum.id");

            ResultSet result = statement.executeQuery();
            ArrayList<Track> tracks = new ArrayList<>();
            while (result.next()) {
                tracks.add(buildObject(new Track(), result));
            }

            return tracks;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Build track object
     * @param model
     * @param result
     * @return
     * @throws SQLException
     */
    @Override
    Track buildObject(Track model, ResultSet result) throws SQLException {
        model.setId(result.getInt("track.id"));
        model.setName_th(result.getString("track.name_th"));
        model.setName_en(result.getString("track.name_en"));

        model.setCurriculum(new CurriculumFactory().buildObject(new Curriculum(), result));

        return model;
    }

    @Override
    @Deprecated
    public Track remove(@NotNull int id) {
        return null;
    }

}
