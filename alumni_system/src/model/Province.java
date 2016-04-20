package model;

import exception.NoProvinceFoundException;
import model.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Province Model
 * for provinces table
 */
public class Province {
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
