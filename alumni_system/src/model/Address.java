package model;

import exception.NoAlumniAddressFoundException;
import exception.NoProvinceFoundException;
import model.database.Database;

import java.sql.*;
import java.util.ArrayList;

/**
 * Address Model
 * for alumni_address table
 */
public class Address {

    /**
     * Province Model
     * for provinces table
     */
    public static class Province {
        private int province_id, province_code;
        private String name_th, name_en;

        public int getProvince_id() {
            return province_id;
        }

        public void setProvince_id(int province_id) {
            this.province_id = province_id;
        }

        public int getProvince_code() {
            return province_code;
        }

        public void setProvince_code(int province_code) {
            this.province_code = province_code;
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

        public static ArrayList<Province> getAllProvince() {
            Connection connection = null;
            try {
                connection = Database.getInstance().getConnection();

                String sql = "SELECT * FROM provinces";
                PreparedStatement stmt = connection.prepareStatement(sql);

                ResultSet result = stmt.executeQuery();

                ArrayList<Province> provinces = new ArrayList<>();
                while (result.next()) {
                    provinces.add(buildProvinceObject(result));
                }
                return provinces;
            } catch (SQLException ex) {
                ex.printStackTrace();
                return null;
            } finally {
                if(connection != null) Database.closeConnection(connection);
            }
        }

        /**
         * Get Province by province_id
         * @param province_id
         * @return
         * @throws NoProvinceFoundException
         */
        public static Province getProvinceByProvinceId(int province_id) throws NoProvinceFoundException {
            Connection connection = null;
            try {
                connection = Database.getInstance().getConnection();

                String sql = "SELECT * FROM provinces WHERE province_id = ?";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setInt(1, province_id);

                ResultSet result = stmt.executeQuery();

                if(result.next()) {
                    return buildProvinceObject(result);
                } else {
                    throw new NoProvinceFoundException();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                return null;
            } finally {
                if(connection != null) Database.closeConnection(connection);
            }
        }

        /**
         * Get Province by province_code
         * @param province_code
         * @return
         * @throws NoProvinceFoundException
         */
        public static Province getProvinceByProvinceCode(int province_code) throws NoProvinceFoundException {
            Connection connection = null;
            try {
                connection = Database.getInstance().getConnection();

                String sql = "SELECT * FROM provinces WHERE province_code = ?";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setInt(1, province_code);

                ResultSet result = stmt.executeQuery();

                if(result.next()) {
                    return buildProvinceObject(result);
                } else {
                    throw new NoProvinceFoundException();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                return null;
            } finally {
                if(connection != null) Database.closeConnection(connection);
            }
        }

        /**
         * Get Province by name_th
         * @param name_th
         * @return
         * @throws NoProvinceFoundException
         */
        public static Province getProvinceByNameTH(String name_th) throws NoProvinceFoundException {
            Connection connection = null;
            try {
                connection = Database.getInstance().getConnection();

                String sql = "SELECT * FROM provinces WHERE name_th = ?";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, name_th);

                ResultSet result = stmt.executeQuery();

                if(result.next()) {
                    return buildProvinceObject(result);
                } else {
                    throw new NoProvinceFoundException();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                return null;
            } finally {
                if(connection != null) Database.closeConnection(connection);
            }
        }

        /**
         * Get Province by name_en
         * @param name_en
         * @return
         * @throws NoProvinceFoundException
         */
        public static Province getProvinceByNameEN(String name_en) throws NoProvinceFoundException {
            Connection connection = null;
            try {
                connection = Database.getInstance().getConnection();

                String sql = "SELECT * FROM provinces WHERE name_en = ?";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, name_en);

                ResultSet result = stmt.executeQuery();

                if(result.next()) {
                    return buildProvinceObject(result);
                } else {
                    throw new NoProvinceFoundException();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                return null;
            } finally {
                if(connection != null) Database.closeConnection(connection);
            }
        }

        /**
         * Build province object
         * @param result
         * @return
         * @throws SQLException
         */
        private static Province buildProvinceObject(ResultSet result) throws SQLException {
            Province province = new Province();

            province.province_id = result.getInt("province_id");
            province.province_code = result.getInt("province_code");
            province.name_th = result.getString("name_th");
            province.name_en = result.getString("name_en");

            return province;
        }

    }

    private String address, amphure, district, zipcode;
    private Province province;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAmphure() {
        return amphure;
    }

    public void setAmphure(String amphure) {
        this.amphure = amphure;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    /**
     * Add new student_id's address
     * @param student_id
     * @param address
     */
    public static void addAddressToStudentId(int student_id, Address address) {
        Connection connection = null;
        try {
            connection = Database.getInstance().getConnection();
            if(address == null) address = new Address();

            String sql = "INSERT INTO alumni_address VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, student_id);
            stmt.setString(2, address.address);
            stmt.setString(3, address.district);
            stmt.setString(4, address.amphure);
            if(address.province != null) stmt.setInt(5, address.province.province_id);
            else stmt.setNull(5, Types.INTEGER);
            stmt.setString(6, address.zipcode);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if(connection != null) Database.closeConnection(connection);
        }
    }

    /**
     * Update student_id's address
     * @param student_id
     * @param address
     * @throws NoAlumniAddressFoundException
     */
    public static void updateAddressToStudentId(int student_id, Address address) throws NoAlumniAddressFoundException {
        Address.getAddressByStudentId(student_id);

        Connection connection = null;
        try {
            connection = Database.getInstance().getConnection();

            String sql = "UPDATE alumni_address " +
                    "SET address = ?, district = ?, amphure = ?, province_id = ?, zipcode = ? " +
                    "WHERE student_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, address.address);
            stmt.setString(2, address.district);
            stmt.setString(3, address.amphure);
            if(address.province != null) stmt.setInt(4, address.province.province_id);
            else stmt.setNull(4, Types.INTEGER);
            stmt.setString(5, address.zipcode);
            stmt.setInt(6, student_id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if(connection != null) Database.closeConnection(connection);
        }
    }

    /**
     * Get student_id's address
     * @param student_id
     * @return
     * @throws NoAlumniAddressFoundException
     */
    public static Address getAddressByStudentId(int student_id) throws NoAlumniAddressFoundException {
        Connection connection = null;
        try {
            connection = Database.getInstance().getConnection();

            String sql = "SELECT * FROM alumni_address WHERE student_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, student_id);

            ResultSet result = stmt.executeQuery();
            if(result.next()) return buildAddressObject(result);
            else throw new NoAlumniAddressFoundException();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            if(connection != null) Database.closeConnection(connection);
        }
    }

    /**
     * Build Address object
     * @param result
     * @return
     * @throws SQLException
     */
    private static Address buildAddressObject(ResultSet result) throws SQLException {
        Address address = new Address();

        address.address = result.getString("address");
        address.district = result.getString("district");
        address.amphure = result.getString("amphure");
        address.zipcode = result.getString("zipcode");
        if (result.getInt("province_id") != 0)
            address.province = Province.getProvinceByProvinceId(result.getInt("province_id"));
        else address.province = null;

        return address;
    }

    /**
     * Remove address by student_id
     * @param student_id
     * @throws NoAlumniAddressFoundException
     */
    public static void removeAddressByStudentId(int student_id) throws NoAlumniAddressFoundException {
        getAddressByStudentId(student_id);

        Connection connection = null;
        try {
            connection = Database.getInstance().getConnection();

            String sql = "DELETE alumni_address WHERE student_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, student_id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if(connection != null) Database.closeConnection(connection);
        }
    }

}
