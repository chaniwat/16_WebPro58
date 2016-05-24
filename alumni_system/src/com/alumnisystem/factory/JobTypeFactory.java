package com.alumnisystem.factory;

import com.alumnisystem.exception.JobTypeNotFound;
import com.alumnisystem.model.JobType;
import com.sun.istack.internal.NotNull;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * JobType Model Factory
 */
public class JobTypeFactory extends ModelFactory<JobType> {

    public JobTypeFactory() {
        super();
    }

    @Override
    public ArrayList<JobType> all() {
        try {
            statement.setStatement("SELECT * FROM jobtype");

            result = statement.executeQuery();
            ArrayList<JobType> jobTypes = new ArrayList<>();
            while (result.next()) {
                jobTypes.add(buildObject(new JobType(), result));
            }
            return jobTypes;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public JobType find(@NotNull int id) throws JobTypeNotFound {
        try {
            statement.setStatement("SELECT * FROM jobtype WHERE jobtype.id = ?")
                    .setInt(id);

            result = statement.executeQuery();
            if(result.next()) {
                return buildObject(new JobType(), result);
            } else {
                throw new JobTypeNotFound();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public JobType create(@NotNull JobType model) {
        try {
            statement.setStatement("INSERT INTO jobtype VALUES (0, ?, ?)", Statement.RETURN_GENERATED_KEYS)
                    .setString(model.getName_th())
                    .setString(model.getName_en());

            statement.executeUpdate();

            result = statement.getStatement().getGeneratedKeys();

            if(result.next()) {
                model.setId(result.getInt(1));
            }

            return model;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public JobType remove(@NotNull int id) {
        JobType jobType = find(id);

        try {
            statement.setStatement("DELETE FROM jobtype WHERE jobtype.id = ?")
                    .setInt(id);

            statement.executeUpdate();

            return jobType;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public JobType update(@NotNull JobType model) {
        try {
            statement.setStatement("UPDATE jobtype SET name_th = ?, name_en = ? WHERE jobtype.id = ?")
                    .setString(model.getName_th())
                    .setStatement(model.getName_en())
                    .setInt(model.getId());

            statement.executeUpdate();

            return model;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    JobType buildObject(@NotNull JobType model, ResultSet result) throws SQLException {
        model.setId(result.getInt("jobtype.id"));
        model.setName_th(result.getString("jobtype.name_th"));
        model.setName_en(result.getString("jobtype.name_en"));

        return model;
    }
}
