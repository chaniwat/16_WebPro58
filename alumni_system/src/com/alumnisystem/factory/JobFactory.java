package com.alumnisystem.factory;

import com.alumnisystem.exception.JobNotFound;
import com.alumnisystem.model.Job;
import com.alumnisystem.model.JobType;
import com.sun.istack.internal.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Job Model Factory
 */
public class JobFactory extends ModelFactory<Job> {

    public JobFactory() {
        super();
    }

    @Override
    public ArrayList<Job> all() {
        try {
            statement.setStatement("SELECT * FROM job LEFT JOIN jobtype ON job.jobtype_id = jobtype.id");

            result = statement.executeQuery();
            ArrayList<Job> jobs = new ArrayList<>();
            while (result.next()) {
                jobs.add(buildObject(new Job(), result));
            }
            return jobs;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Job find(@NotNull int id) throws JobNotFound {
        try {
            statement.setStatement("SELECT * FROM job LEFT JOIN jobtype ON job.jobtype_id = jobtype.id WHERE job.id = ?")
                    .setInt(id);

            result = statement.executeQuery();
            if(result.next()) {
                return buildObject(new Job(), result);
            } else {
                throw new JobNotFound();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Job create(@NotNull Job model) {
        try {
            statement.setStatement("INSERT INTO job VALUES (0, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)
                    .setInt(model.getJobType().getId())
                    .setString(model.getName_th())
                    .setStatement(model.getName_en());

            model.setId(statement.executeUpdate());

            return model;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Job remove(@NotNull int id) throws JobNotFound {
        Job job = find(id);

        try {
            statement.setStatement("DELETE FROM job WHERE job.id = ?")
                    .setInt(id);

            statement.executeUpdate();

            return job;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Job update(@NotNull Job model) {
        try {
            statement.setStatement("UPDATE job SET jobtype_id = ?, name_th = ?, name_en = ? WHERE job.id = ?")
                    .setInt(model.getJobType().getId())
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
    Job buildObject(@NotNull Job model, ResultSet result) throws SQLException {
        model.setId(result.getInt("job.id"));
        model.setName_th(result.getString("job.name_th"));
        model.setName_en(result.getString("job.name_en"));

        model.setJobType(new JobTypeFactory().buildObject(new JobType(), result));

        return model;
    }

}
