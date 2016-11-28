import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.FlowLayout;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Menu extends JFrame {

    private JPanel contentPane;

    public static void main(final Vehicle vehicle, final Customer customer) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Menu frame = new Menu(vehicle, customer);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Menu(final Vehicle vehicle, final Customer customer) {

        setTitle("Rentals for " + vehicle.toString());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(1, 1, 1, 1));
        setContentPane(contentPane);
        contentPane.setLayout(new GridLayout(6, 6, 4, 4));

        JLabel details = new JLabel(vehicle.getDetails());
        contentPane.add(details);



        JPanel p1 = new JPanel();
        contentPane.add(p1);
        p1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        JLabel pickUp = new JLabel("Pick-Up Date:");
        pickUp.setFont(new Font("Helvetica Neue", Font.BOLD, 14));
        p1.add(pickUp);

        JLabel month = new JLabel("Month");
        month.setFont(new Font("Helvetica Neue", Font.PLAIN, 12));
        p1.add(month);

        final JComboBox<String> monthBox = new JComboBox<String>();
        monthBox.setFont(new Font("Helvetica Neue", Font.PLAIN, 12));
        monthBox.setModel(new DefaultComboBoxModel<String>(
                new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));

        p1.add(monthBox);

        // the day label and wheel
        JLabel day = new JLabel("Day");
        day.setFont(new Font("Helvetica Neue", Font.PLAIN, 12));
        p1.add(day);
        final JComboBox<String> dayBox = new JComboBox<String>();
        dayBox.setFont(new Font("Helvetica Neue", Font.PLAIN, 12));
        dayBox.setModel(new DefaultComboBoxModel<String>(new String[] { "01", "02", "03", "04", "05", "06", "07", "08",
                "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25",
                "26", "27", "28", "29", "30", "31" }));
        p1.add(dayBox);

        JLabel year = new JLabel("Year");
        year.setFont(new Font("Helvetica Neue", Font.PLAIN, 12));
        p1.add(year);

        final JComboBox<String> yearBox = new JComboBox<String>();
        yearBox.setFont(new Font("Helvetica Neue", Font.PLAIN, 12));
        yearBox.setModel(new DefaultComboBoxModel<String>(new String[] { "2016", "2017", "2018", "2019", "2020", "2021",
                "2022", "2023", "2024", "2025", "2026" }));
        p1.add(yearBox);

        JPanel p2 = new JPanel();
        contentPane.add(p2);
        p2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        JLabel dropOff = new JLabel("Drop-Off Date:");
        dropOff.setFont(new Font("Helvetica Neue", Font.BOLD, 14));
        p2.add(dropOff);

        JLabel month2 = new JLabel("Month");
        month2.setFont(new Font("Helvetica Neue", Font.PLAIN, 12));
        p2.add(month2);

        final JComboBox<String> monthBox2 = new JComboBox<String>();
        monthBox2.setModel(new DefaultComboBoxModel<String>(
                new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
        monthBox2.setFont(new Font("Helvetica Neue", Font.PLAIN, 12));
        p2.add(monthBox2);

        // the Day label and the combo box
        JLabel day2 = new JLabel("Day");
        day2.setFont(new Font("Helvetica Neue", Font.PLAIN, 12));
        p2.add(day2);
        final JComboBox<String> dayBox2 = new JComboBox<String>();
        dayBox2.setModel(new DefaultComboBoxModel<String>(new String[] { "01", "02", "03", "04", "05", "06", "07", "08",
                "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25",
                "26", "27", "28", "29", "30", "31" }));
        dayBox2.setFont(new Font("Helvetica Neue", Font.PLAIN, 12));
        p2.add(dayBox2);

        // the Year label and the combo box
        JLabel year2 = new JLabel("Year");
        year2.setFont(new Font("Helvetica Neue", Font.PLAIN, 12));
        p2.add(year2);
        final JComboBox<String> yearBox2 = new JComboBox<String>();
        yearBox2.setModel(new DefaultComboBoxModel<String>(new String[] { "2016", "2017", "2018", "2019", "2020", "2021",
                "2022", "2023", "2024", "2025", "2026" }));
        yearBox2.setFont(new Font("Helvetica Neue", Font.PLAIN, 12));
        p2.add(yearBox2);

        // the fourth panel and its contents
        JPanel p4 = new JPanel();
        contentPane.add(p4);
        JButton search = new JButton("Rent Now");

        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (vehicle.getQuantity() <= 0) {
                    JOptionPane.showMessageDialog(null, "The selected vehicle is not in stock.", "Error", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                String fromDate = dayBox.getSelectedItem() + "/" + monthBox.getSelectedItem() + "/" + yearBox.getSelectedItem();
                String toDate = dayBox2.getSelectedItem() + "/" + monthBox2.getSelectedItem() + "/" + yearBox2.getSelectedItem();

                java.util.Date utilDateFrom = null;
                java.util.Date utilDateTo = null;


                try {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    utilDateFrom = formatter.parse(fromDate);
                    utilDateTo = formatter.parse(toDate);

                    if (utilDateFrom.after(utilDateTo)) {
                        JOptionPane.showMessageDialog(null, "The drop-off date must be after the pick-up date.", "Error", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    } else if (utilDateFrom.before(new Date())) {
                        JOptionPane.showMessageDialog(null, "The pick-up date must not have already passed.", "Error", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }

                    //Insert the new rental
                    String sql = "INSERT INTO RENTAL (`RENTAL_DATE`, `CUSTOMER_ID`) VALUES (" + utilDateFrom.getTime() + ", " + customer.getCustomerID() + ")";
                    Connection c = null;
                    Statement stmt = null;
                    int rentalId;

                    Class.forName("org.sqlite.JDBC");
                    c = DriverManager.getConnection("jdbc:sqlite:CarRentalService.db");
                    c.setAutoCommit(false);
                    stmt = c.createStatement();
                    stmt.executeUpdate(sql);

                    ResultSet rs = stmt.getGeneratedKeys();
                    if (rs.next()) {

                        //Get the new rental id
                        rentalId = rs.getInt(1);

                        //Get the price of the rental
                        sql = "SELECT PRICE_RENTFEE FROM CAR AS C \n" +
                                "INNER JOIN VEHICLE_DETAILS AS V on C.VEHICLE_CODE = V.VEHICLE_CODE\n" +
                                "INNER JOIN PRICE AS P on P.PRICE_CODE = V.PRICE_CODE " +
                                "WHERE V.VEHICLE_CODE = '" + vehicle.getCode() +"';";

                        rs = stmt.executeQuery(sql);

                        double dailyPrice = 0;
                        double totalPrice;
                        long diff = utilDateTo.getTime() - utilDateFrom.getTime();
                        int duration = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);


                        if (rs.next()) {
                            dailyPrice = rs.getDouble("PRICE_RENTFEE");
                            totalPrice = dailyPrice * duration;

                            sql = "INSERT INTO RENTAL_DETAILS (`RENTAL_NUMBER`, `CAR_CODE`, `DETAIL_FEE`, `DETAIL_DUEDATE`, `DETAIL_LATEFEE`)\n" +
                                    "VALUES (" +rentalId+", "+vehicle.getCode()+", " +totalPrice+ ", "+utilDateTo.getTime()+", 0);";

                            stmt.executeUpdate(sql);

                            sql = "UPDATE CAR\n" +
                                    "SET CAR_QUANTITY = CAR_QUANTITY - 1\n" +
                                    "WHERE CAR_CODE = '"+vehicle.getCode()+"'";

                            stmt.executeUpdate(sql);
                        }
                    }
                    stmt.close();
                    c.commit();
                    c.close();
                } catch (ParseException ex) {
                    System.out.println(e.toString());
                    ex.printStackTrace();
                } catch (ClassNotFoundException | SQLException e1) {
                    e1.printStackTrace();
                }

                JOptionPane.showMessageDialog(null, "Success!!", "Not an error!", JOptionPane.INFORMATION_MESSAGE);

            }
        });


        search.setFont(new Font("Helvetica Neue", Font.PLAIN, 14));
        p4.add(search);
    }

}
