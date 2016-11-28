import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQLiteJDBC {

    public static void initializeDatabase() {

        System.out.println("Beginning initial database setup");

        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:CarRentalService.db");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");

        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:CarRentalService.db");
            System.out.println("Opened database successfully");

            stmt = c.createStatement();

            String sql = "CREATE TABLE IF NOT EXISTS PRICE (\n" +
                    "    PRICE_CODE INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    PRICE_DESCRIPTION VARCHAR(40),\n" +
                    "    PRICE_RENTFEE NUMERIC(5 , 2 ) CHECK (PRICE_RENTFEE >= 0),\n" +
                    "    PRICE_LATEFEE NUMERIC(5 , 2 ) CHECK (PRICE_LATEFEE >= 0)\n" +
                    ");\n" +
                    "\n" +
                    "CREATE TABLE IF NOT EXISTS VEHICLE_DETAILS (\n" +
                    "    VEHICLE_CODE INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    VEHICLE_PASSENGERS NUMERIC(2 , 0 ) CHECK (VEHICLE_PASSENGERS >= 0),\n" +
                    "    VEHICLE_SIZE VARCHAR(15),\n" +
                    "    VEHICLE_AC BOOLEAN,\n" +
                    "    VEHICLE_COLOR VARCHAR(20),\n" +
                    "    VEHICLE_AUTOMATIC BOOLEAN,\n" +
                    "    VEHICLE_BRAND VARCHAR(20),\n" +
                    "    VEHICLE_MODEL VARCHAR(20),\n" +
                    "    VEHICLE_YEAR NUMERIC(4 , 0 ),\n" +
                    "    PRICE_CODE INT,\n" +
                    "    FOREIGN KEY (PRICE_CODE)\n" +
                    "        REFERENCES PRICE (PRICE_CODE)\n" +
                    ");\n" +
                    "\n" +
                    "CREATE TABLE IF NOT EXISTS CAR (\n" +
                    "    CAR_CODE INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    VEHICLE_CODE INT,\n" +
                    "    CAR_QUANTITY NUMERIC(3 , 0 ) CHECK (CAR_QUANTITY >= 0),\n" +
                    "    FOREIGN KEY (VEHICLE_CODE)\n" +
                    "        REFERENCES VEHICLE_DETAILS (VEHICLE_CODE)\n" +
                    ");\n" +
                    "\n" +
                    "CREATE TABLE IF NOT EXISTS CUSTOMER (\n" +
                    "    CUSTOMER_ID INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    CUSTOMER_FNAME VARCHAR(30),\n" +
                    "    CUSTOMER_LNAME VARCHAR(30),\n" +
                    "    CUSTOMER_STREET VARCHAR(120),\n" +
                    "    CUSTOMER_CITY VARCHAR(50),\n" +
                    "    CUSTOMER_PROVINCE CHAR(2),\n" +
                    "    CUSTOMER_POSTALCODE VARCHAR(7),\n" +
                    "    CUSTOMER_BALANCE NUMERIC(10 , 2 )\n" +
                    ");\n" +
                    "\n" +
                    "CREATE TABLE IF NOT EXISTS RENTAL (\n" +
                    "    RENTAL_NUMBER INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    RENTAL_DATE DATE,\n" +
                    "    CUSTOMER_ID INT,\n" +
                    "    FOREIGN KEY (CUSTOMER_ID)\n" +
                    "        REFERENCES CUSTOMER (CUSTOMER_ID)\n" +
                    ");\n" +
                    "\n" +
                    "\n" +
                    "CREATE TABLE IF NOT EXISTS RENTAL_DETAILS (\n" +
                    "    RENTAL_NUMBER INT,\n" +
                    "    CAR_CODE INT,\n" +
                    "    DETAIL_FEE NUMERIC(8 , 2 ),\n" +
                    "    DETAIL_DUEDATE DATE,\n" +
                    "    DETAIL_RETURNDATE DATE,\n" +
                    "    DETAIL_LATEFEE NUMERIC(8 , 2 ),\n" +
                    "    PRIMARY KEY (RENTAL_NUMBER , CAR_CODE),\n" +
                    "    FOREIGN KEY (RENTAL_NUMBER)\n" +
                    "        REFERENCES RENTAL (RENTAL_NUMBER),\n" +
                    "    FOREIGN KEY (CAR_CODE)\n" +
                    "        REFERENCES CAR (CAR_CODE)\n" +
                    ");";

            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Table created successfully");


        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:CarRentalService.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();

            String sql = "INSERT INTO `CUSTOMER` (`CUSTOMER_FNAME`, `CUSTOMER_LNAME`, `CUSTOMER_STREET`, `CUSTOMER_CITY`, `CUSTOMER_PROVINCE`, `CUSTOMER_POSTALCODE`, `CUSTOMER_BALANCE`) \n" +
                    "VALUES ('Timothy', 'Thompson', '25 South Street', 'Halifax', 'NS', 'B0B 0B0', '0');\n" +
                    "INSERT INTO `CUSTOMER` (`CUSTOMER_FNAME`, `CUSTOMER_LNAME`, `CUSTOMER_STREET`, `CUSTOMER_CITY`, `CUSTOMER_PROVINCE`, `CUSTOMER_POSTALCODE`, `CUSTOMER_BALANCE`) \n" +
                    "VALUES ('Drake', 'Golfer', '409 Forward Crescent', 'Bedford', 'NS', 'B1B 1B1', '500');\n" +
                    "INSERT INTO `CUSTOMER` (`CUSTOMER_FNAME`, `CUSTOMER_LNAME`, `CUSTOMER_STREET`, `CUSTOMER_CITY`, `CUSTOMER_PROVINCE`, `CUSTOMER_POSTALCODE`, `CUSTOMER_BALANCE`) \n" +
                    "VALUES ('Fredrick', 'MacDougall', '823 Crabdale Avenue', 'Truro', 'NS', 'B9L 4T9', '0');\n" +
                    "INSERT INTO `CUSTOMER` (`CUSTOMER_FNAME`, `CUSTOMER_LNAME`, `CUSTOMER_STREET`, `CUSTOMER_CITY`, `CUSTOMER_PROVINCE`, `CUSTOMER_POSTALCODE`, `CUSTOMER_BALANCE`) \n" +
                    "VALUES ('Tracy', 'Foxton', '535 Forkspoon Lane', 'Moncton', 'NB', 'C3T 2Y3', '1000');\n" +
                    "INSERT INTO `CUSTOMER` (`CUSTOMER_FNAME`, `CUSTOMER_LNAME`, `CUSTOMER_STREET`, `CUSTOMER_CITY`, `CUSTOMER_PROVINCE`, `CUSTOMER_POSTALCODE`, `CUSTOMER_BALANCE`) \n" +
                    "VALUES ('Taylor', 'Erikson', '190 Keltic Drive', 'Sydney River', 'NS', 'B4L 9C2', '0');\n" +
                    "INSERT INTO `CUSTOMER` (`CUSTOMER_FNAME`, `CUSTOMER_LNAME`, `CUSTOMER_STREET`, `CUSTOMER_CITY`, `CUSTOMER_PROVINCE`, `CUSTOMER_POSTALCODE`, `CUSTOMER_BALANCE`) \n" +
                    "VALUES ('Robert', 'Chang', '122 Main Street', 'Glace Bay', 'NS', 'C2B 5B2', '0');\n" +
                    "INSERT INTO `CUSTOMER` (`CUSTOMER_FNAME`, `CUSTOMER_LNAME`, `CUSTOMER_STREET`, `CUSTOMER_CITY`, `CUSTOMER_PROVINCE`, `CUSTOMER_POSTALCODE`, `CUSTOMER_BALANCE`) \n" +
                    "VALUES ('Sarah', 'Goode', '708 Fairview Road', 'Yarmouth', 'NS', 'Y6T 3B6', '250');\n";

            stmt.executeUpdate(sql);

            sql = "INSERT INTO `PRICE` (`PRICE_DESCRIPTION`, `PRICE_RENTFEE`, `PRICE_LATEFEE`) VALUES \n" +
                    "('Basic', 25, 25);\n" +
                    "INSERT INTO `PRICE` (`PRICE_DESCRIPTION`, `PRICE_RENTFEE`, `PRICE_LATEFEE`) VALUES \n" +
                    "('Economy', 50, 30);\n" +
                    "INSERT INTO `PRICE` (`PRICE_DESCRIPTION`, `PRICE_RENTFEE`, `PRICE_LATEFEE`) VALUES \n" +
                    "('Standard', 75, 35);\n" +
                    "INSERT INTO `PRICE` (`PRICE_DESCRIPTION`, `PRICE_RENTFEE`, `PRICE_LATEFEE`) VALUES \n" +
                    "('Premium', 100, 40);\n" +
                    "INSERT INTO `PRICE` (`PRICE_DESCRIPTION`, `PRICE_RENTFEE`, `PRICE_LATEFEE`) VALUES \n" +
                    "('Ultimate', 200, 50);\n";

            stmt.executeUpdate(sql);


            sql = "INSERT INTO `VEHICLE_DETAILS` (`VEHICLE_PASSENGERS`, `VEHICLE_SIZE`, `VEHICLE_AC`, `VEHICLE_COLOR`, `VEHICLE_AUTOMATIC`, `VEHICLE_BRAND`, `VEHICLE_MODEL`, `VEHICLE_YEAR`, `PRICE_CODE`) \n" +
                    "VALUES (4, 'Coupe', 1, 'Orange', 1, 'BMW', 'M4', '2016', '04');\n" +
                    "\n" +
                    "INSERT INTO `VEHICLE_DETAILS` (`VEHICLE_PASSENGERS`, `VEHICLE_SIZE`, `VEHICLE_AC`, `VEHICLE_COLOR`, `VEHICLE_AUTOMATIC`, `VEHICLE_BRAND`, `VEHICLE_MODEL`, `VEHICLE_YEAR`, `PRICE_CODE`) \n" +
                    "VALUES (4, 'Sedan', 1, 'Red', 1, 'BMW', 'M3', '2014', '04');\n" +
                    "\n" +
                    "INSERT INTO `VEHICLE_DETAILS` (`VEHICLE_PASSENGERS`, `VEHICLE_SIZE`, `VEHICLE_AC`, `VEHICLE_COLOR`, `VEHICLE_AUTOMATIC`, `VEHICLE_BRAND`, `VEHICLE_MODEL`, `VEHICLE_YEAR`, `PRICE_CODE`) \n" +
                    "VALUES (2, 'Convertible', 1, 'White', 0, 'Mercedes-Benz', 'SL400 Roadster', '2016', '04');\n" +
                    "\n" +
                    "INSERT INTO `VEHICLE_DETAILS` (`VEHICLE_PASSENGERS`, `VEHICLE_SIZE`, `VEHICLE_AC`, `VEHICLE_COLOR`, `VEHICLE_AUTOMATIC`, `VEHICLE_BRAND`, `VEHICLE_MODEL`, `VEHICLE_YEAR`, `PRICE_CODE`) \n" +
                    "VALUES (4, 'Sedan', 1, 'Black', 1, 'Rolls-Royce', 'Phantom', '2012', '05');\n" +
                    "\n" +
                    "INSERT INTO `VEHICLE_DETAILS` (`VEHICLE_PASSENGERS`, `VEHICLE_SIZE`, `VEHICLE_AC`, `VEHICLE_COLOR`, `VEHICLE_AUTOMATIC`, `VEHICLE_BRAND`, `VEHICLE_MODEL`, `VEHICLE_YEAR`, `PRICE_CODE`) \n" +
                    "VALUES (4, 'Coupe', 1, 'Black', 1, 'Rolls-Royce', 'Wraith', '2015', '05');\n" +
                    "\n" +
                    "INSERT INTO `VEHICLE_DETAILS` (`VEHICLE_PASSENGERS`, `VEHICLE_SIZE`, `VEHICLE_AC`, `VEHICLE_COLOR`, `VEHICLE_AUTOMATIC`, `VEHICLE_BRAND`, `VEHICLE_MODEL`, `VEHICLE_YEAR`, `PRICE_CODE`) \n" +
                    "VALUES (2, 'Coupe', 1, 'Red', 1, 'Ferrari', 'LaFerrari', '2016', '05');\n" +
                    "\n" +
                    "INSERT INTO `VEHICLE_DETAILS` (`VEHICLE_PASSENGERS`, `VEHICLE_SIZE`, `VEHICLE_AC`, `VEHICLE_COLOR`, `VEHICLE_AUTOMATIC`, `VEHICLE_BRAND`, `VEHICLE_MODEL`, `VEHICLE_YEAR`, `PRICE_CODE`) \n" +
                    "VALUES (2, 'Coupe', 1, 'Yellow', 0, 'Aston Martin', 'Vantage', '2013', '04');\n" +
                    "\n" +
                    "INSERT INTO `VEHICLE_DETAILS` (`VEHICLE_PASSENGERS`, `VEHICLE_SIZE`, `VEHICLE_AC`, `VEHICLE_COLOR`, `VEHICLE_AUTOMATIC`, `VEHICLE_BRAND`, `VEHICLE_MODEL`, `VEHICLE_YEAR`, `PRICE_CODE`) \n" +
                    "VALUES (2, 'Coupe', 1, 'Blue', 1, 'Lamborghini', 'Aventador', '2015', '05');\n" +
                    "\n" +
                    "INSERT INTO `VEHICLE_DETAILS` (`VEHICLE_PASSENGERS`, `VEHICLE_SIZE`, `VEHICLE_AC`, `VEHICLE_COLOR`, `VEHICLE_AUTOMATIC`, `VEHICLE_BRAND`, `VEHICLE_MODEL`, `VEHICLE_YEAR`, `PRICE_CODE`) \n" +
                    "VALUES (4, 'Sedan', 1, 'Black', 1, 'Bentley', 'Mulsanne', '2016', '05');\n" +
                    "\n" +
                    "INSERT INTO `VEHICLE_DETAILS` (`VEHICLE_PASSENGERS`, `VEHICLE_SIZE`, `VEHICLE_AC`, `VEHICLE_COLOR`, `VEHICLE_AUTOMATIC`, `VEHICLE_BRAND`, `VEHICLE_MODEL`, `VEHICLE_YEAR`, `PRICE_CODE`) \n" +
                    "VALUES (4, 'Convertible', 1, 'Red', 1, 'Maserati', 'GranTurismo', '2012', '05');\n" +
                    "\n" +
                    "INSERT INTO `VEHICLE_DETAILS` (`VEHICLE_PASSENGERS`, `VEHICLE_SIZE`, `VEHICLE_AC`, `VEHICLE_COLOR`, `VEHICLE_AUTOMATIC`, `VEHICLE_BRAND`, `VEHICLE_MODEL`, `VEHICLE_YEAR`, `PRICE_CODE`) \n" +
                    "VALUES (4, 'Sedan', 1, 'Red', 0, 'BMW', 'M6', '2014', '05');";


            stmt.executeUpdate(sql);

            sql = " INSERT INTO `CAR` (`VEHICLE_CODE`, `CAR_QUANTITY`) VALUES (01, 2);\n" +
                    "INSERT INTO `CAR` (`VEHICLE_CODE`, `CAR_QUANTITY`) VALUES (02, 1);\n" +
                    "INSERT INTO `CAR` (`VEHICLE_CODE`, `CAR_QUANTITY`) VALUES (03, 3);\n" +
                    "INSERT INTO `CAR` (`VEHICLE_CODE`, `CAR_QUANTITY`) VALUES (04, 1);\n" +
                    "INSERT INTO `CAR` (`VEHICLE_CODE`, `CAR_QUANTITY`) VALUES (05, 1);\n" +
                    "INSERT INTO `CAR` (`VEHICLE_CODE`, `CAR_QUANTITY`) VALUES (06, 1);\n" +
                    "INSERT INTO `CAR` (`VEHICLE_CODE`, `CAR_QUANTITY`) VALUES (07, 3);\n" +
                    "INSERT INTO `CAR` (`VEHICLE_CODE`, `CAR_QUANTITY`) VALUES (08, 2);\n" +
                    "INSERT INTO `CAR` (`VEHICLE_CODE`, `CAR_QUANTITY`) VALUES (09, 1);\n" +
                    "INSERT INTO `CAR` (`VEHICLE_CODE`, `CAR_QUANTITY`) VALUES (10, 1);\n" +
                    "\n";

            stmt.executeUpdate(sql);

            stmt.close();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Records created successfully");

    }

    public static void main(String args[]) {


        /*




        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:CarRentalService.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "SELECT * FROM RENTAL";
            ResultSet rs = stmt.executeQuery(sql);

            while ( rs.next() ) {
                System.out.println(rs.getInt("RENTAL_NUMBER") + " " + rs.getDate("RENTAL_DATE") +  " " + rs.getInt("CUSTOMER_ID"));
            }

            rs.close();
            stmt.close();
            c.close();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");

*/



    }
}
