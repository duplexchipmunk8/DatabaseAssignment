import javax.swing.*;
import java.sql.*;
import java.util.concurrent.TimeUnit;

public class Vehicle {
    private int code;
    private int passengers;
    private String size;
    private boolean hasAC;
    private String color;
    private boolean isAuto;
    private String brand;
    private String model;
    private int year;
    private int priceCode;
    private int quantity;

    public Vehicle(ResultSet rs) throws SQLException {
        this.code = rs.getInt("CAR_CODE");
        this.passengers = rs.getInt("VEHICLE_PASSENGERS");
        this.size = rs.getString("VEHICLE_SIZE");
        this.hasAC = rs.getBoolean("VEHICLE_AC");
        this.color = rs.getString("VEHICLE_COLOR");
        this.isAuto = rs.getBoolean("VEHICLE_AUTOMATIC");
        this.brand = rs.getString("VEHICLE_BRAND");
        this.model = rs.getString("VEHICLE_MODEL");
        this.year = rs.getInt("VEHICLE_YEAR");
        this.priceCode = rs.getInt("PRICE_CODE");
        this.quantity = rs.getInt("CAR_QUANTITY");
    }

    public int getCode() {
        return code;
    }

    public int getPassengers() {
        return passengers;
    }

    public String getSize() {
        return size;
    }

    public boolean hasAC() {
        return hasAC;
    }

    public String getColor() {
        return color;
    }

    public boolean isAuto() {
        return isAuto;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public int getPriceCode() {
        return priceCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void decrementQuantity() {
        this.quantity--;
    }

    @Override
    public String toString() {
        return getYear() + " " + getBrand() + " " + getModel();
    }

    public String getDetails() {
        String details = toString() + "\n";
        details += "Color: " + getColor() + "\n";
        details += "Size: " + getSize() + "\n";
        details += "AC: " + (hasAC ? "Yes" : "No") + "\n";
        details += "Transmission: " + (isAuto ? "Automatic" : "Manual") + "\n";
        details += "Passengers: " + getPassengers() + "\n";

        return details;
    }

    public boolean rent(Rental rental) throws SQLException, ClassNotFoundException {

        double dailyPrice = 0, totalPrice = 0;
        int rentalId = 0;

        long diff = rental.getDateTo().getTime() - rental.getDateFrom().getTime();
        int duration = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

        //Get the daily price;
        String sql = "SELECT PRICE_RENTFEE FROM CAR AS C \n" +
                "INNER JOIN VEHICLE_DETAILS AS V on C.VEHICLE_CODE = V.VEHICLE_CODE\n" +
                "INNER JOIN PRICE AS P on P.PRICE_CODE = V.PRICE_CODE " +
                "WHERE V.VEHICLE_CODE = '" + getCode() +"';";

        Connection c = null;
        Statement stmt = null;

        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection("jdbc:sqlite:CarRentalService.db");
        c.setAutoCommit(false);
        stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        if (rs.next())
            dailyPrice = rs.getDouble("PRICE_RENTFEE");

        //calculate the total price
        totalPrice = dailyPrice * duration;

        //confirm rental
        int result = JOptionPane.showConfirmDialog(null, "The total price will be $" + totalPrice + ". Do you want to continue?",
                "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (result == JOptionPane.NO_OPTION || result == JOptionPane.CLOSED_OPTION)
            return false;

        //Create the rental
        sql = "INSERT INTO RENTAL (`RENTAL_DATE`, `CUSTOMER_ID`) VALUES (" + rental.getDateFrom().getTime() + ", " + rental.getCustomer().getCustomerID() + ")";
        stmt.executeUpdate(sql);
        rs = stmt.getGeneratedKeys();

        if (rs.next())
            rentalId = rs.getInt(1);

        sql = "INSERT INTO RENTAL_DETAILS (`RENTAL_NUMBER`, `CAR_CODE`, `DETAIL_FEE`, `DETAIL_DUEDATE`, `DETAIL_LATEFEE`)\n" +
                "VALUES (" +rentalId+", "+getCode()+", " +totalPrice+ ", "+rental.getDateTo().getTime()+", 0);";


        stmt.executeUpdate(sql);

        //Update the quantity on hand
        sql = "UPDATE CAR\n" +
                "SET CAR_QUANTITY = CAR_QUANTITY - 1\n" +
                "WHERE CAR_CODE = '"+getCode()+"'";

        stmt.executeUpdate(sql);

        //update the customers balance
        sql = "UPDATE CUSTOMER SET CUSTOMER_BALANCE = CUSTOMER_BALANCE + " + totalPrice + " WHERE CUSTOMER_ID = '" +
                rental.getCustomer().getCustomerID() + "';";

        stmt.executeUpdate(sql);
        stmt.close();
        c.commit();
        c.close();
        decrementQuantity();

        JOptionPane.showMessageDialog(null, "Rental Completed! The balance has been applied to your account.", "Success!", JOptionPane.INFORMATION_MESSAGE);
        return true;
    }
}
