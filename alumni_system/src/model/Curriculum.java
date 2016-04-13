package model;

import exception.NoCurriculumFoundException;
import model.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Curriculum model
 * for curriculum table
 */
public class Curriculum {

    private int curriculum_id;
    private String name_th, name_en;

    public void setCurriculum_id(int curriculum_id) {
        this.curriculum_id = curriculum_id;
    }

    public int getCurriculum_id() {
        return curriculum_id;
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
     * Add new curriculum
     * @param curriculum
     */
    public static void addCurriculum(Curriculum curriculum) {
        Connection connection = null;

        try {
            connection = Database.getInstance().getConnection();

            String sql = "INSERT INTO curriculum VALUES (0, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, curriculum.name_th);
            stmt.setString(2, curriculum.name_en);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if(connection != null) Database.closeConnection(connection);
        }
    }

    /**
     * Update curriculum
     * @param curriculum
     * @throws NoCurriculumFoundException
     */
    public static void updateCurriculum(Curriculum curriculum) throws NoCurriculumFoundException {
        Connection connection = null;

        try {
            connection = Database.getInstance().getConnection();

            String sql = "UPDATE curriculum " +
                    "SET name_th = ?, name_en = ? " +
                    "WHERE curriculum_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, curriculum.name_th);
            stmt.setString(2, curriculum.name_en);
            stmt.setInt(3, curriculum.curriculum_id);

            int result = stmt.executeUpdate();
            if(result <= 0) throw new NoCurriculumFoundException();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if(connection != null) Database.closeConnection(connection);
        }
    }

    /**
     * Get all curriculum
     * @return
     */
    public static ArrayList<Curriculum> getAllCurriculum() {
        Connection connection = null;

        try {
            connection = Database.getInstance().getConnection();

            String sql = "SELECT * FROM curriculum";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet result = stmt.executeQuery();

            ArrayList<Curriculum> curricula = new ArrayList<>();
            while (result.next()) {
                curricula.add(buildCurriculumObject(result));
            }
            return curricula;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            if(connection != null) Database.closeConnection(connection);
        }
    }

    /**
     * Get curriculum by curriculum_id
     * @param curriculum_id
     * @return
     * @throws NoCurriculumFoundException
     */
    public static Curriculum getCurriculumById(int curriculum_id) throws NoCurriculumFoundException {
        Connection connection = null;

        try {
            connection = Database.getInstance().getConnection();

            String sql = "SELECT * FROM curriculum WHERE curriculum_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, curriculum_id);
            ResultSet result = stmt.executeQuery();

            ArrayList<Curriculum> curricula = new ArrayList<>();
            if(result.next()) return buildCurriculumObject(result);
            else throw new NoCurriculumFoundException();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            if(connection != null) Database.closeConnection(connection);
        }
    }

    /**
     * Build curriculum object
     * @param result
     * @return
     * @throws SQLException
     */
    private static Curriculum buildCurriculumObject(ResultSet result) throws SQLException {
        Curriculum curriculum = new Curriculum();

        curriculum.curriculum_id = result.getInt("curriculum_id");
        curriculum.name_th = result.getString("name_th");
        curriculum.name_en = result.getString("name_en");

        return curriculum;
    }

    /**
     * Remove curriculum
     * @param curriculum_id
     * @throws NoCurriculumFoundException
     */
    public static void removeCurriculumById(int curriculum_id) throws NoCurriculumFoundException {
        Connection connection = null;

        try {
            connection = Database.getInstance().getConnection();

            String sql = "DELETE FROM curriculum WHERE curriculum_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, curriculum_id);

            int result = stmt.executeUpdate();
            if(result <= 0) throw new NoCurriculumFoundException();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if(connection != null) Database.closeConnection(connection);
        }
    }

}
