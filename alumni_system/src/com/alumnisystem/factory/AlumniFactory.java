package com.alumnisystem.factory;

import com.alumnisystem.exception.AlumniNotFound;
import com.alumnisystem.model.Alumni;
import com.alumnisystem.model.Curriculum;
import com.alumnisystem.model.Job;
import com.alumnisystem.model.Track;
import com.sun.istack.internal.NotNull;

import java.sql.*;
import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Alumni Model Factory
 */
public class AlumniFactory extends ModelFactory<Alumni> {

    public AlumniFactory() {
        super();
    }

    /**
     * Create new alumni
     * @param model New {@link Alumni} object to add
     */
    @Override
    public Alumni create(@NotNull Alumni model) throws RuntimeException {
        if(model.getTracks().size() <= 0) {
            throw new RuntimeException("Track not set on new alumni");
        }

        if(model.getUsernames() == null) {
            model.setUsernames(new ArrayList<>());
            for(Track track : model.getTracks()) {
                model.getUsernames().add(String.valueOf(track.getStudent_id()));
            }
        }

        new UserFactory().createUser(model, model.getUsernames(), "ITKMITL");

        try {
            statement.setStatement("INSERT INTO alumni VALUES (0, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)
                    .setInt(model.getId());

            if(model.getJob() == null) {
                statement.setNull(Types.INTEGER);
            } else {
                statement.setInt(model.getJob().getId());
            }

            statement.setString(model.getNickname());

            if(model.getBirthdate() != null) {
                statement.setDate(new java.sql.Date(model.getBirthdate().getTime()));
            } else {
                statement.setNull(Types.DATE);
            }

            statement.setString(model.getWork_name());

            model.setAlumni_id(statement.executeUpdate());

            statement.setStatement("INSERT INTO alumni_address VALUES (?, ?, ?, ?, ?, ?)")
                    .setInt(model.getAlumni_id())
                    .setString(model.getAddress())
                    .setString(model.getDistrict())
                    .setString(model.getAmphure())
                    .setString(model.getProvince())
                    .setString(model.getZipcode());
            statement.executeUpdate();

            for(Track track : model.getTracks()) {
                addTrack(model, track);
            }

            return model;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Add new track to alumni
     * @param alumni Target {@link Alumni} object
     * @param track New {@link Track} object to add to alumni
     */
    public Track addTrack(Alumni alumni, Track track) {
        try {
            statement.setStatement("SELECT * FROM user_alias WHERE user_id = ? AND username = ?")
                    .setInt(alumni.getId())
                    .setString(String.valueOf(track.getStudent_id()));

            ResultSet result = statement.executeQuery();
            if(!result.next()) {
                new UserFactory().addUsernameAlias(alumni, String.valueOf(track.getStudent_id()));
            }

            statement.setStatement("INSERT INTO alumni_track VALUES (?, ?, ?, ?, ?, ?)")
                    .setInt(alumni.getAlumni_id())
                    .setInt(track.getId())
                    .setInt(track.getStudent_id())
                    .setInt(track.getGeneration())
                    .setInt(track.getStarteduyear())
                    .setInt(track.getEndeduyear());
            statement.executeUpdate();

            return track;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Update alumni data
     * @param model Updated {@link Alumni} object
     * @throws AlumniNotFound
     */
    @Override
    public Alumni update(Alumni model) {
        try {
            statement.setStatement("UPDATE alumni " +
                    "SET nickname = ?, birthdate = ?, work_name = ?, " +
                    "job_id = ? " +
                    "WHERE id = ?")
                    .setString(model.getNickname());

            if(model.getBirthdate() != null) {
                statement.setDate(new java.sql.Date(model.getBirthdate().getTime()));
            } else {
                statement.setNull(Types.DATE);
            }

            statement.setString(model.getWork_name());

            if(model.getJob() == null) {
                statement.setNull(Types.INTEGER);
            } else {
                statement.setInt(model.getJob().getId());
            }

            statement.setInt(model.getAlumni_id());

            int result = statement.executeUpdate();

            if(result == 0) throw new AlumniNotFound();

            statement.setStatement("UPDATE alumni_address " +
                    "SET address = ?, district = ?, amphure = ?, province = ?, zipcode = ? " +
                    "WHERE alumni_id = ?")
                    .setString(model.getAddress())
                    .setString(model.getDistrict())
                    .setString(model.getAmphure())
                    .setString(model.getProvince())
                    .setString(model.getZipcode())
                    .setInt(model.getAlumni_id());
            statement.executeUpdate();

            new UserFactory().update(model);

            return model;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Update track data
     * @param track Update {@link Track} object
     * @throws RuntimeException
     */
    public Track updateAlumniTrack(Track track) throws RuntimeException {
        try {
            statement.setStatement("UPDATE alumni_track " +
                    "SET track_id = ?, starteduyear = ?, endeduyear = ? " +
                    "WHERE student_id = ?")
                    .setInt(track.getId())
                    .setInt(track.getStarteduyear())
                    .setInt(track.getEndeduyear())
                    .setInt(track.getStudent_id());
            int result = statement.executeUpdate();

            if(result <= 0) throw new RuntimeException();

            return track;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Get All alumni object.
     * @return {@link ArrayList}&lt;{@link Alumni}&gt;
     */
    @Override
    public ArrayList<Alumni> all() {
        try {
            statement.setStatement("SELECT * " +
                    "FROM alumni " +
                    "LEFT JOIN user ON alumni.user_id = user.id " +
                    "LEFT JOIN alumni_address ON alumni.id = alumni_address.alumni_id " +
                    "LEFT JOIN alumni_track ON alumni.id = alumni_track.alumni_id " +
                    "LEFT JOIN track ON alumni_track.track_id = track.id " +
                    "LEFT JOIN curriculum ON track.curriculum_id = curriculum.id " +
                    "LEFT JOIN job ON alumni.job_id = job.id " +
                    "LEFT JOIN jobtype ON job.jobtype_id = jobtype.id");
            ResultSet result = statement.executeQuery();

            ArrayList<Alumni> alumnis = new ArrayList<>();
            while (result.next()) {
                alumnis.add(buildObject(new Alumni(), result));
            }

            return alumnis;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Get all alumni within degree
     * @param degree
     * @return
     */
    public ArrayList<Alumni> getAlumniWithinDegree(Curriculum.Degree degree) {
        return getAlumniWithinDegreeAndGeneration(degree, 0);
    }

    /**
     * Get all alumni within degree and generation
     * @param degree
     * @param generation
     * @return
     */
    public ArrayList<Alumni> getAlumniWithinDegreeAndGeneration(Curriculum.Degree degree, int generation) {
        try {
            if(generation == 0) {
                statement.setStatement("SELECT * " +
                        "FROM alumni " +
                        "LEFT JOIN user ON alumni.user_id = user.id " +
                        "LEFT JOIN alumni_address ON alumni.id = alumni_address.alumni_id " +
                        "LEFT JOIN alumni_track ON alumni.id = alumni_track.alumni_id " +
                        "LEFT JOIN track ON alumni_track.track_id = track.id " +
                        "LEFT JOIN curriculum ON track.curriculum_id = curriculum.id " +
                        "LEFT JOIN job ON alumni.job_id = job.id " +
                        "LEFT JOIN jobtype ON job.jobtype_id = jobtype.id " +
                        "WHERE curriculum.degree = ?")
                        .setString(String.valueOf(degree));
            } else {
                statement.setStatement("SELECT * " +
                        "FROM alumni " +
                        "LEFT JOIN user ON alumni.user_id = user.id " +
                        "LEFT JOIN alumni_address ON alumni.id = alumni_address.alumni_id " +
                        "LEFT JOIN alumni_track ON alumni.id = alumni_track.alumni_id " +
                        "LEFT JOIN track ON alumni_track.track_id = track.id " +
                        "LEFT JOIN curriculum ON track.curriculum_id = curriculum.id " +
                        "LEFT JOIN job ON alumni.job_id = job.id " +
                        "LEFT JOIN jobtype ON job.jobtype_id = jobtype.id " +
                        "WHERE curriculum.degree = ? AND alumni_track.generation = ?")
                        .setString(String.valueOf(degree))
                        .setInt(generation);
            }

            ResultSet result = statement.executeQuery();

            ArrayList<Alumni> alumnis = new ArrayList<>();
            while (result.next()) {
                alumnis.add(buildObject(new Alumni(), result));
            }

            return alumnis;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Get alumni by student_id
     * @param alumni_id
     * @return {@link Alumni}
     * @throws AlumniNotFound
     */
    @Override
    public Alumni find(int alumni_id) throws AlumniNotFound {
        try {
            statement.setStatement("SELECT * " +
                    "FROM alumni " +
                    "LEFT JOIN user ON alumni.user_id = user.id " +
                    "LEFT JOIN alumni_address ON alumni.id = alumni_address.alumni_id " +
                    "LEFT JOIN alumni_track ON alumni.id = alumni_track.alumni_id " +
                    "LEFT JOIN track ON alumni_track.track_id = track.id " +
                    "LEFT JOIN curriculum ON track.curriculum_id = curriculum.id " +
                    "LEFT JOIN job ON alumni.job_id = job.id " +
                    "LEFT JOIN jobtype ON job.jobtype_id = jobtype.id " +
                    "WHERE alumni.id = ?")
                    .setInt(alumni_id);

            ResultSet result = statement.executeQuery();
            if(result.next()) {
                return buildObject(new Alumni(), result);
            } else {
                throw new AlumniNotFound();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Get alumni by user_id
     * @param user_id
     * @return {@link Alumni}
     * @throws AlumniNotFound
     */
    public Alumni findByUserId(int user_id) throws AlumniNotFound {
        try {
            statement.setStatement("SELECT * " +
                    "FROM alumni " +
                    "LEFT JOIN user ON alumni.user_id = user.id " +
                    "LEFT JOIN alumni_address ON alumni.id = alumni_address.alumni_id " +
                    "LEFT JOIN alumni_track ON alumni.id = alumni_track.alumni_id " +
                    "LEFT JOIN track ON alumni_track.track_id = track.id " +
                    "LEFT JOIN curriculum ON track.curriculum_id = curriculum.id " +
                    "LEFT JOIN job ON alumni.job_id = job.id " +
                    "LEFT JOIN jobtype ON job.jobtype_id = jobtype.id " +
                    "WHERE user.id = ?")
                    .setInt(user_id);

            ResultSet result = statement.executeQuery();
            if(result.next()) {
                return buildObject(new Alumni(), result);
            } else {
                throw new AlumniNotFound();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Get alumni by student_id
     * @param student_id
     * @return {@link Alumni}
     * @throws AlumniNotFound
     */
    public Alumni findByStudentId(int student_id) throws AlumniNotFound {
        try {
            statement.setStatement("SELECT * " +
                    "FROM alumni " +
                    "LEFT JOIN user ON alumni.user_id = user.id " +
                    "LEFT JOIN alumni_address ON alumni.id = alumni_address.alumni_id " +
                    "LEFT JOIN alumni_track ON alumni.id = alumni_track.alumni_id " +
                    "LEFT JOIN track ON alumni_track.track_id = track.id " +
                    "LEFT JOIN curriculum ON track.curriculum_id = curriculum.id " +
                    "LEFT JOIN job ON alumni.job_id = job.id " +
                    "LEFT JOIN jobtype ON job.jobtype_id = jobtype.id " +
                    "WHERE alumni.id = (" +
                    "SELECT alumni_id FROM alumni_track WHERE student_id = ?)")
                    .setInt(student_id);

            ResultSet result = statement.executeQuery();
            if(result.next()) {
                return buildObject(new Alumni(), result);
            } else {
                throw new AlumniNotFound();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Build single alumni object
     * @param result
     * @return
     * @throws SQLException
     */
    @Override
    Alumni buildObject(Alumni model, ResultSet result) throws SQLException {
        model.setAlumni_id(result.getInt("alumni.id"));

        new UserFactory().setObject(model, result, true);

        model.setJob(new JobFactory().buildObject(new Job(), result));
        model.setNickname(result.getString("alumni.nickname"));
        model.setBirthdate(result.getDate("alumni.birthdate"));
        model.setWork_name(result.getString("alumni.work_name"));

        model.setAddress(result.getString("alumni_address.address"));
        model.setDistrict(result.getString("alumni_address.district"));
        model.setAmphure(result.getString("alumni_address.amphure"));
        model.setProvince(result.getString("alumni_address.province"));
        model.setZipcode(result.getString("alumni_address.zipcode"));

        while(true) {
            Track track = new TrackFactory().buildObject(new Track(), result);

            track.setStudent_id(result.getInt("alumni_track.student_id"));
            track.setGeneration(result.getInt("alumni_track.generation"));
            track.setStarteduyear(result.getInt("alumni_track.starteduyear"));
            track.setEndeduyear(result.getInt("alumni_track.endeduyear"));

            model.getTracks().add(track);
            if(!result.next() || result.getInt("alumni.id") != model.getAlumni_id()) {
                result.previous();
                break;
            }
        }

        return model;
    }

    /**
     * Remove alumni by alumni_id
     * @param alumni_id
     * @throws AlumniNotFound
     */
    @Override
    public Alumni remove(int alumni_id) throws AlumniNotFound {
        Alumni alumni = find(alumni_id);

        new UserFactory().removeUser(alumni.getId());

        return alumni;
    }

    /**
     * Remove alumni by student_id
     * @param student_id
     * @throws AlumniNotFound
     */
    public Alumni removeByStudentId(int student_id) throws AlumniNotFound {
        Alumni alumni = findByStudentId(student_id);

        new UserFactory().removeUser(alumni.getId());

        return alumni;
    }

    /**
     * Remove alumni by user_id
     * @param user_id
     * @throws AlumniNotFound
     */
    public Alumni removeByUserId(int user_id) throws AlumniNotFound {
        Alumni alumni = findByUserId(user_id);

        new UserFactory().removeUser(alumni.getId());

        return alumni;
    }

    /**
     * Remove alumni track by student_id
     * @param student_id
     * @throws RuntimeException
     */
    public void removeTrackByStudentId(int student_id) throws RuntimeException {
        try {
            statement.setStatement("DELETE FROM alumni_track WHERE student_id = ?")
                    .setInt(student_id);
            int result = statement.executeUpdate();

            if(result <= 0) throw new RuntimeException();

            statement.setStatement("DELETE FROM user_alias WHERE username = ?")
                    .setString(String.valueOf(student_id));
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Get generation number list of degree
     * @param degree
     * @return
     */
    public TreeSet<Integer> getGenerationList(Curriculum.Degree degree) {
        try {
            statement.setStatement("SELECT generation FROM alumni_track " +
                    "JOIN track ON alumni_track.track_id = track.id " +
                    "JOIN curriculum ON track.curriculum_id = curriculum.id WHERE curriculum.degree = ? " +
                    "GROUP BY generation")
                    .setString(String.valueOf(degree));

            result = statement.executeQuery();

            TreeSet<Integer> integers = new TreeSet<>();

            while (result.next()) {
                integers.add(result.getInt("generation"));
            }

            return integers;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
