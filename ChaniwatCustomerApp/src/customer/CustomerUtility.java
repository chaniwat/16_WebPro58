package customer;

import net.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Meranote on 1/26/2016.
 */
public class CustomerUtility {

    private CustomerUtility() {}

    /**
     * Add new customer into 'chaniwat_customerApp' table.
     * @param customer
     * @return customer id if succeed, 0 for failed
     * @throws SQLException
     */
    public static int addCustomer(Customer customer) {
        int customerid = 0;
        try {
            String sql = "INSERT INTO chaniwat_customerApp VALUES(0, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = Database.getInstance().getConnection().prepareStatement(sql);
            stmt.setString(1, customer.getFirstName());
            stmt.setString(2, customer.getLastName());
            stmt.setString(3, customer.getCompany());
            stmt.setString(4, customer.getMobile());
            stmt.setString(5, customer.getEmail());
            stmt.setString(6, customer.getAddr());

            stmt.executeUpdate();
            stmt.close();

            sql = "SELECT id FROM chaniwat_customerApp WHERE firstname = ? AND lastname = ?";
            stmt = Database.getInstance().getConnection().prepareStatement(sql);
            stmt.setString(1, customer.getFirstName());
            stmt.setString(2, customer.getLastName());

            ResultSet result = stmt.executeQuery();
            result.last();
            customerid = result.getInt("id");

            stmt.close();
        } catch (SQLException e) {
            return customerid;
        } finally {
            return customerid;
        }
    }

    /**
     * Get customer data by id.
     * @param customerId unique id.
     * @return {@link Customer} object, null for not found or failed.
     */
    public static Customer getCustomer(int customerId) {
        try {
            String sql = "SELECT * FROM chaniwat_customerApp WHERE id = ?";
            PreparedStatement stmt = Database.getInstance().getConnection().prepareStatement(sql);
            stmt.setInt(1, customerId);

            return queryCustomer(stmt);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get customer data by name.
     * @param firstname
     * @param lastname
     * @return {@link Customer} object, null for not found or failed.
     */
    public static Customer getCustomer(String firstname, String lastname) {
        try {
            String sql = "SELECT * FROM chaniwat_customerApp WHERE firstname = ? AND lastname = ?";
            PreparedStatement stmt = Database.getInstance().getConnection().prepareStatement(sql);
            stmt.setString(1, firstname);
            stmt.setString(2, lastname);

            return queryCustomer(stmt);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Customer queryCustomer(PreparedStatement stmt) throws SQLException {
        ResultSet result;
        if((result = stmt.executeQuery()) != null) {
            result.last();
            Customer customer = new Customer(
                    result.getInt("id"),
                    result.getString("firstname"),
                    result.getString("lastname"),
                    result.getString("company"),
                    result.getString("mobile"),
                    result.getString("email"),
                    result.getString("addr")
            );
            stmt.close();

            return customer;
        } else {
            return null;
        }
    }

}
