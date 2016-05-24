package com.alumnisystem.factory;

import com.alumnisystem.exception.TeacherNotFound;
import com.alumnisystem.model.Teacher;
import com.alumnisystem.model.Work;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Teacher Model Factory
 */
public class TeacherFactory extends ModelFactory<Teacher> {

    public TeacherFactory() {
        super();
    }

    /**
     * Add teacher
     * @param model
     */
    @Override
    public Teacher create(Teacher model) {
        try {
            
            if(model.getUsernames() == null) {
                model.setUsernames(new ArrayList<>());
                model.getUsernames().add(model.getFname_en());
            }

            new UserFactory().createUser(model, model.getUsernames(), "itkmitl");

            statement.setStatement("INSERT INTO teacher VALUES (?, ?, ?)")
                    .setInt(model.getTeacher_id())
                    .setInt(model.getId())
                    .setString(String.valueOf(model.getWork_status()));

            statement.executeUpdate();
            
            return model;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Update teacher
     * @param teacher
     */
    @Override
    public Teacher update(Teacher teacher) {
        try {
            statement.setStatement("UPDATE teacher SET work_status = ? WHERE id = ?")
                    .setString(String.valueOf(teacher.getWork_status()))
                    .setInt(teacher.getTeacher_id());

            statement.executeUpdate();

            new UserFactory().update(teacher);

            return teacher;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Get all teacher
     * @return
     */
    @Override
    public ArrayList<Teacher> all() {
        try {
            statement.setStatement("SELECT * FROM teacher LEFT JOIN user ON teacher.user_id = user.id");

            ResultSet result = statement.executeQuery();

            ArrayList<Teacher> teachers = new ArrayList<>();
            while (result.next()) {
                teachers.add(buildObject(new Teacher(), result));
            }
            return teachers;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Get teacher by teacher_id
     * @param teacher_id
     * @return
     * @throws TeacherNotFound
     */
    @Override
    public Teacher find(int teacher_id) throws TeacherNotFound {
        try {
            statement.setStatement("SELECT * FROM teacher LEFT JOIN user ON teacher.user_id = user.id WHERE teacher.id = ?")
                    .setInt(teacher_id);

            ResultSet result = statement.executeQuery();

            if(result.next()) return buildObject(new Teacher(), result);
            else throw new TeacherNotFound();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Get teacher by user_id
     * @param user_id
     * @return
     * @throws TeacherNotFound
     */
    public Teacher findByUserId(int user_id) throws TeacherNotFound {
        try {
            statement.setStatement("SELECT * FROM teacher LEFT JOIN user ON teacher.user_id = user.id WHERE user.id = ?")
                    .setInt(user_id);

            ResultSet result = statement.executeQuery();

            if(result.next()) return buildObject(new Teacher(), result);
            else throw new TeacherNotFound();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Build teacher object
     * @param result
     * @return
     * @throws SQLException
     */
    @Override
    Teacher buildObject(Teacher model, ResultSet result) throws SQLException {

        model.setTeacher_id(result.getInt("teacher.id"));
        model.setWork_status(Work.Status.valueOf(result.getString("work_status")));

        new UserFactory().setObject(model, result, true);

        return model;
    }

    /**
     * Remove teacher by teacher_id
     * @param teacher_id
     * @throws TeacherNotFound
     */
    @Override
    public Teacher remove(int teacher_id) throws TeacherNotFound {
        Teacher teacher = find(teacher_id);

        new UserFactory().removeUser(teacher.getId());

        return teacher;
    }

    /**
     * Remove teacher by user_id
     * @param user_id
     * @throws TeacherNotFound
     */
    public Teacher removeByUserId(int user_id) throws TeacherNotFound {
        Teacher teacher = findByUserId(user_id);

        new UserFactory().removeUser(teacher.getId());

        return teacher;
    }

}
