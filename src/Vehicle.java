import java.sql.ResultSet;
import java.sql.SQLException;

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

    public boolean isHasAC() {
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

    @Override
    public String toString() {
        return "Vehicle{" +
                "code=" + code +
                ", passengers=" + passengers +
                ", size='" + size + '\'' +
                ", hasAC=" + hasAC +
                ", color='" + color + '\'' +
                ", isAuto=" + isAuto +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", priceCode=" + priceCode +
                ", quantity=" + quantity +
                '}';
    }
}