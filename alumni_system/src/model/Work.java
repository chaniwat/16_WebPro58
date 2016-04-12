package model;

import exception.NoSectionFoundException;
import model.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Work model
 */
public class Work {

    private Work() {}

    /**
     * Work status
     * for enum column work_status in teacher table
     */
    public enum Status {
        EMPLOYEE,
        OFFICIAL
    }

    /**
     * Work section/department
     * for work_section table
     */
    public static class Section {
        private int section_id;
        private String name_th, name_en;

        public int getSection_id() {
            return section_id;
        }

        public void setSection_id(int section_id) {
            this.section_id = section_id;
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

        public static ArrayList<Section> getAllSection() {
            Connection connection = null;

            try {
                connection = Database.getInstance().getConnection();

                String sql = "SELECT * FROM work_section";
                PreparedStatement stmt = connection.prepareStatement(sql);
                ResultSet result = stmt.executeQuery();

                ArrayList<Section> sections = new ArrayList<>();
                while (result.next()) {
                    sections.add(buildSectionObject(result));
                }
                return sections;
            } catch (SQLException ex) {
                ex.printStackTrace();
                return null;
            } finally {
                if(connection != null) Database.closeConnection(connection);
            }
        }

        /**
         * Add new work_section
         * @param section
         */
        public static void addSection(Section section) {
            Connection connection = null;

            try {
                connection = Database.getInstance().getConnection();

                String sql = "INSERT INTO work_section VALUES (0, ?, ?)";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, section.name_th);
                stmt.setString(2, section.name_en);
                stmt.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                if(connection != null) Database.closeConnection(connection);
            }
        }

        /**
         * Update work_section
         * @param section
         * @throws NoSectionFoundException
         */
        public static void updateSection(Section section) throws NoSectionFoundException {
            Connection connection = null;

            try {
                connection = Database.getInstance().getConnection();

                String sql = "UPDATE work_section " +
                        "SET name_th = ?, name_en = ? " +
                        "WHERE section_id = ?";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, section.name_th);
                stmt.setString(2, section.name_en);
                stmt.setInt(3, section.section_id);
                int result = stmt.executeUpdate();

                if(result <= 0) throw new NoSectionFoundException();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                if(connection != null) Database.closeConnection(connection);
            }
        }

        /**
         * Get work_section by section_id
         * @param section_id
         * @return
         * @throws NoSectionFoundException
         */
        public static Section getSectionById(int section_id) throws NoSectionFoundException {
            Connection connection = null;

            try {
                connection = Database.getInstance().getConnection();

                String sql = "SELECT * FROM work_section WHERE section_id = ?";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setInt(1, section_id);
                ResultSet result = stmt.executeQuery();

                if(result.next()) return buildSectionObject(result);
                else throw new NoSectionFoundException();
            } catch (SQLException ex) {
                ex.printStackTrace();
                return null;
            } finally {
                if(connection != null) Database.closeConnection(connection);
            }
        }

        /**
         * Build work_section object
         * @param result
         * @return
         * @throws SQLException
         */
        private static Section buildSectionObject(ResultSet result) throws SQLException {
            Section section = new Section();

            section.section_id = result.getInt("section_id");
            section.name_th = result.getString("name_th");
            section.name_en = result.getString("name_en");

            return section;
        }

        /**
         * Remove work_section by id
         * @param section_id
         * @throws NoSectionFoundException
         */
        public static void removeSectionById(int section_id) throws NoSectionFoundException {
            Connection connection = null;

            try {
                connection = Database.getInstance().getConnection();

                String sql = "DELETE FROM work_section WHERE section_id = ?";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setInt(1, section_id);
                int result = stmt.executeUpdate();

                if(result <= 0) throw new NoSectionFoundException();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                if(connection != null) Database.closeConnection(connection);
            }
        }

    }

}
