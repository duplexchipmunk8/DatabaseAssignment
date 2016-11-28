import java.sql.ResultSet;
import java.sql.SQLException;

public class Customer {
    private int customerID;
    private String fName;
    private String lName;
    private String city;
    private String province;
    private String postalCode;
    private double balance;


    public Customer(ResultSet rs) throws SQLException {
        this.customerID = rs.getInt("CUSTOMER_ID");
        this.fName = rs.getString("CUSTOMER_FNAME");
        this.lName = rs.getString("CUSTOMER_LNAME");
        this.city = rs.getString("CUSTOMER_CITY");
        this.province = rs.getString("CUSTOMER_PROVINCE");
        this.postalCode = rs.getString("CUSTOMER_POSTALCODE");
        this.balance = rs.getDouble("CUSTOMER_BALANCE");
    }

    public int getCustomerID() {
        return customerID;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public String getCity() {
        return city;
    }

    public String getProvince() {
        return province;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public double getBalance() {
        return balance;
    }
}
