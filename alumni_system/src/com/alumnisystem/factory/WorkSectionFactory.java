package com.alumnisystem.factory;

import com.alumnisystem.exception.WorkSectionNotFound;
import com.alumnisystem.model.Work;
import com.sun.istack.internal.NotNull;

import java.sql.*;
import java.util.ArrayList;

/**
 * WorkSection Model Table
 */
public class WorkSectionFactory extends ModelFactory<Work.Section> {

    public WorkSectionFactory() {
        super();
    }

    @Override
    public ArrayList<Work.Section> all() {
        try {
            statement.setStatement("SELECT * FROM work_section");
            ResultSet result = statement.executeQuery();

            ArrayList<Work.Section> sections = new ArrayList<>();
            while (result.next()) {
                sections.add(buildObject(new Work.Section(), result));
            }
            return sections;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Add new work_section
     * @param section
     */
    @Override
    public Work.Section create(Work.Section section) {
        try {
            statement.setStatement("INSERT INTO work_section VALUES (0, ?, ?)", Statement.RETURN_GENERATED_KEYS)
                    .setString(section.getName_th())
                    .setString(section.getName_en());

            section.setId(statement.executeUpdate());

            return section;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Update work_section
     * @param section
     * @throws WorkSectionNotFound
     */
    @Override
    public Work.Section update(Work.Section section) {
        try {
            statement.setStatement("UPDATE work_section SET name_th = ?, name_en = ? WHERE id = ?")
                    .setString(section.getName_th())
                    .setString(section.getName_en())
                    .setInt(section.getId());

            statement.executeUpdate();

            return section;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Get work_section by section_id
     * @param id
     * @return
     * @throws WorkSectionNotFound
     */
    @Override
    public Work.Section find(int id) throws WorkSectionNotFound {
        try {
            statement.setStatement("SELECT * FROM work_section WHERE id = ?")
                    .setInt(id);

            ResultSet result = statement.executeQuery();

            if(result.next()) return buildObject(new Work.Section(), result);
            else throw new WorkSectionNotFound();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Build work_section object
     * @param result
     * @return
     * @throws SQLException
     */
    @Override
    Work.Section buildObject(Work.Section section, ResultSet result) throws SQLException {
        section.setId(result.getInt("work_section.id"));
        section.setName_th(result.getString("work_section.name_th"));
        section.setName_en(result.getString("work_section.name_en"));

        return section;
    }

    /**
     * Remove work_section by id
     * @param section_id
     * @throws WorkSectionNotFound
     */
    @Override
    public Work.Section remove(int section_id) throws WorkSectionNotFound {
        Work.Section section = find(section_id);

        try {
            statement.setStatement("DELETE FROM work_section WHERE id = ?")
                    .setInt(section_id);
            int result = statement.executeUpdate();

            if(result <= 0) throw new WorkSectionNotFound();

            return section;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
