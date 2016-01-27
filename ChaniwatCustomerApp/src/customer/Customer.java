package customer;

/**
 * Created by Meranote on 1/26/2016.
 */
public class Customer {

    private int id;
    private String firstName, lastName, Company, Mobile, Email, addr;

    public Customer(String firstName, String lastName, String company, String mobile, String email, String addr) {
        this.firstName = firstName;
        this.lastName = lastName;
        Company = company;
        Mobile = mobile;
        Email = email;
        this.addr = addr;
    }

    public Customer(int id, String firstName, String lastName, String company, String mobile, String email, String addr) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        Company = company;
        Mobile = mobile;
        Email = email;
        this.addr = addr;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String company) {
        Company = company;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
}
