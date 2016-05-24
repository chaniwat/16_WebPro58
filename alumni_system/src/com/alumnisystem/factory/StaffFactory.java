package com.alumnisystem.factory;

import com.alumnisystem.exception.StaffNotFound;
import com.alumnisystem.model.*;

import java.sql.*;
import java.util.ArrayList;

/**
 * Staff Model Factory
 */
public class StaffFactory extends ModelFactory<Staff> {

    public StaffFactory() {
        super();
    }

    /**
     * Add new staff
     * @param model
     */
    @Override
    public Staff create(Staff model) {
        
        if(model.getUsernames() == null) {
            model.setUsernames(new ArrayList<>());
            model.getUsernames().add(model.getFname_en());
        }
        
        new UserFactory().createUser(model, model.getUsernames(), "itkmitl");
        
        try {
            statement.setStatement("INSERT INTO staff VALUES (?, ?, ?)")
                    .setInt(model.getStaff_id())
                    .setInt(model.getId())
                    .setInt(model.getSection().getId());
            
            statement.executeUpdate();
            
            return model;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Update staff
     * @param model
     * @throws StaffNotFound
     */
    @Override
    public Staff update(Staff model) {
        try {
            statement.setStatement("UPDATE staff SET work_section_id = ? WHERE id = ?")
                    .setInt(model.getSection().getId())
                    .setInt(model.getStaff_id());

            statement.executeUpdate();

            new UserFactory().update(model);

            return model;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Get all staff
     * @return
     */
    @Override
    public ArrayList<Staff> all() {
        try {
            statement.setStatement("SELECT * FROM staff " +
                    "LEFT JOIN work_section ON staff.work_section_id = work_section.id " +
                    "LEFT JOIN user ON staff.user_id = user.id");
            ResultSet result = statement.executeQuery();

            ArrayList<Staff> staffs = new ArrayList<>();

            while (result.next()) {
                staffs.add(buildObject(new Staff(), result));
            }

            return staffs;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Get staff by staff_id
     * @param id
     * @return
     * @throws StaffNotFound
     */
    @Override
    public Staff find(int id) throws StaffNotFound {
        try {
            statement.setStatement("SELECT * FROM staff " +
                    "LEFT JOIN work_section ON staff.work_section_id = work_section.id " +
                    "LEFT JOIN user ON staff.user_id = user.id " +
                    "WHERE staff.id = ?")
                    .setInt(id);

            ResultSet result = statement.executeQuery();

            if(result.next()) return buildObject(new Staff(), result);
            else throw new StaffNotFound();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Get staff by user_id
     * @param user_id
     * @return
     * @throws StaffNotFound
     */
    public Staff findByUserId(int user_id) throws StaffNotFound {
        try {
            statement.setStatement("SELECT * FROM staff " +
                    "LEFT JOIN work_section ON staff.work_section_id = work_section.id " +
                    "LEFT JOIN user ON staff.user_id = user.id " +
                    "WHERE user.id = ?")
                    .setInt(user_id);

            ResultSet result = statement.executeQuery();

            if(result.next()) return buildObject(new Staff(), result);
            else throw new StaffNotFound();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Build staff object
     * @param result
     * @return
     * @throws SQLException
     */
    @Override
    Staff buildObject(Staff model, ResultSet result) throws SQLException {
        model.setStaff_id(result.getInt("staff.id"));

        new UserFactory().setObject(model, result, true);

        model.setSection(new WorkSectionFactory().buildObject(new Work.Section(), result));

        return model;
    }

    /**
     * Remove staff by id
     * @param id
     * @throws StaffNotFound
     */
    public Staff remove(int id) throws StaffNotFound {
        Staff staff = find(id);

        new UserFactory().removeUser(staff.getId());

        return staff;
    }

    /**
     * Remove staff by user_id
     * @param user_id
     * @throws StaffNotFound
     */
    public Staff removeByUserId(int user_id) throws StaffNotFound {
        Staff staff = findByUserId(user_id);

        new UserFactory().removeUser(user_id);

        return staff;
    }

}
