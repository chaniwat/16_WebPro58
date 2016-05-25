package com.alumnisystem.factory;

import com.alumnisystem.exception.CurriculumNotFound;
import com.alumnisystem.model.Curriculum;
import com.sun.istack.internal.NotNull;

import java.sql.*;
import java.util.ArrayList;

/**
 * Curriculum Model Factory
 */
public class CurriculumFactory extends ModelFactory<Curriculum> {

    public CurriculumFactory() {
        super();
    }

    /**
     * Add new curriculum
     * @param curriculum
     */
    @Override
    public Curriculum create(Curriculum curriculum) {
        try {
            statement.setStatement("INSERT INTO curriculum VALUES (0, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)
                    .setString(curriculum.getCname_th())
                    .setString(curriculum.getCname_en())
                    .setString(curriculum.getSname_th())
                    .setString(curriculum.getSname_en())
                    .setInt(curriculum.getCyear())
                    .setString(String.valueOf(curriculum.getDegree()));

            statement.executeUpdate();

            result = statement.getStatement().getGeneratedKeys();

            if(result.next()) {
                curriculum.setId(result.getInt(1));
            }
            
            return curriculum;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Update curriculum
     * @param curriculum
     * @throws CurriculumNotFound
     */
    @Override
    public Curriculum update(Curriculum curriculum) {
        try {
            statement.setStatement("UPDATE curriculum " +
                    "SET cname_th = ?, cname_en = ?, " +
                    "sname_th = ?, sname_en = ?, " +
                    "cyear = ?, degree = ? " +
                    "WHERE id = ?")
                    .setString(curriculum.getCname_th())
                    .setString(curriculum.getCname_en())
                    .setString(curriculum.getSname_th())
                    .setString(curriculum.getSname_en())
                    .setInt(curriculum.getCyear())
                    .setString(String.valueOf(curriculum.getDegree()));

            statement.executeUpdate();

            return curriculum;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Get all curriculum
     * @return
     */
    @Override
    public ArrayList<Curriculum> all() {
        try {
            statement.setStatement("SELECT * FROM curriculum");

            ResultSet result = statement.executeQuery();

            ArrayList<Curriculum> curricula = new ArrayList<>();
            while (result.next()) {
                curricula.add(buildObject(new Curriculum(), result));
            }
            return curricula;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Get curriculum by curriculum_id
     * @param id
     * @return
     * @throws CurriculumNotFound
     */
    @Override
    public Curriculum find(int id) throws CurriculumNotFound {
        try {
            statement.setStatement("SELECT * FROM curriculum WHERE id = ?")
                    .setInt(id);

            ResultSet result = statement.executeQuery();

            if(result.next()) {
                return buildObject(new Curriculum(), result);
            } else {
                throw new CurriculumNotFound();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<Curriculum> findAllByDegree(Curriculum.Degree degree) {
        try {
            statement.setStatement("SELECT * FROM curriculum WHERE degree = ?")
                    .setString(degree.toString());

            ResultSet result = statement.executeQuery();

            ArrayList<Curriculum> curricula = new ArrayList<>();
            while (result.next()) {
                curricula.add(buildObject(new Curriculum(), result));
            }
            return curricula;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Build curriculum object
     * @param model
     * @param result
     * @return
     * @throws SQLException
     */
    @Override
    Curriculum buildObject(Curriculum model, ResultSet result) throws SQLException {
        model.setId(result.getInt("curriculum.id"));
        model.setCname_th(result.getString("curriculum.cname_th"));
        model.setCname_en(result.getString("curriculum.cname_en"));
        model.setSname_th(result.getString("curriculum.sname_th"));
        model.setSname_en(result.getString("curriculum.sname_en"));
        model.setCyear(result.getInt("curriculum.cyear"));
        model.setDegree(Curriculum.Degree.valueOf(result.getString("curriculum.degree")));

        return model;
    }

    @Override
    @Deprecated
    public Curriculum remove(int curriculum_id) {
        return null;
    }
}
