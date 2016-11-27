import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class CarSearch extends JFrame {

    private JPanel contentPane;
    private JTextField searchBox;
    private JPanel p3;
    private JPanel p2;
    private JCheckBox cb1;
    private JCheckBox cb2;
    private JCheckBox cb3;
    private JCheckBox cb4;
    private JCheckBox cb5;
    private JCheckBox cb6;
    private JComboBox<String> make;
    private JComboBox<String> color;
    private JComboBox<String> transmission;
    private JComboBox<String> year;
    private JPanel p4;
    private JList resultList;
    JScrollPane scrollPane;
    private JButton searchButton;

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    CarSearch frame = new CarSearch();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public CarSearch() {

        setFont(new Font("Helvetica", Font.PLAIN, 14));
        setTitle("Car Search");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(1, 1, 1, 1));
        setContentPane(contentPane);
        contentPane.setLayout(new GridLayout(6, 6, 4, 4));

        JPanel p1 = new JPanel();
        contentPane.add(p1);
        p1.setLayout(new FlowLayout(FlowLayout.CENTER, 8, 8));

        searchBox = new JTextField();
        searchBox.setFont(new Font("Helvetica Neue", Font.PLAIN, 14));
        searchBox.setText("Keyword");

        JLabel searchLabel = new JLabel("Search: ");
        p1.add(searchLabel);
        p1.add(searchBox);
        searchBox.setColumns(20);

        p3 = new JPanel();
        contentPane.add(p3);
        p3.setLayout(new GridLayout(0, 3, 0, 0));



        String[] makes = {"Make", "BMW", "Mercedes-Benz", "Aston Martin", "Rolls-Royce", "Bentley", "Lamborghini", "Maserati", "Ferrari", "chevrolet"};
        String[] colors = {"Color", "Red", "Orange", "Yellow", "Green", "Blue", "White", "Black"};
        String[] transmissions = {"Transmission", "Manual", "Automatic"};
        String[] years = {"Year", "2012", "2013", "2014", "2015", "2016"};


        make = new JComboBox<String>();
        make.setFont(new Font("Helvetica Neue", Font.PLAIN, 12));
        make.setModel(new DefaultComboBoxModel<String>(makes));
        p3.add(make);


        color = new JComboBox<String>();
        color.setFont(new Font("Helvetica Neue", Font.PLAIN, 12));
        color.setModel(new DefaultComboBoxModel<String>(colors));
        p3.add(color);


        transmission = new JComboBox<String>();
        transmission.setFont(new Font("Helvetica Neue", Font.PLAIN, 12));
        transmission.setModel(new DefaultComboBoxModel<String>(transmissions));
        p3.add(transmission);

        p2 = new JPanel();
        contentPane.add(p2);
        p2.setLayout(new GridLayout(0, 3, 0, 0));


        year = new JComboBox<String>();
        year.setFont(new Font("Helvetica Neue", Font.PLAIN, 12));
        year.setModel(new DefaultComboBoxModel<String>(years));
        p2.add(year);


        p4 = new JPanel();
        contentPane.add(p4);

        searchButton = new JButton("Search");
        searchButton.setFont(new Font("Helvetica", Font.PLAIN, 14));

        resultList = new JList();
        scrollPane = new JScrollPane();
        contentPane.add(scrollPane);

        resultList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    System.out.println(resultList.getSelectedValue());
                    new Menu().setVisible(true);

                }
            }
        });

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                Connection c = null;
                Statement stmt = null;
                try {
                    Class.forName("org.sqlite.JDBC");
                    c = DriverManager.getConnection("jdbc:sqlite:CarRentalService.db");
                    c.setAutoCommit(false);
                    stmt = c.createStatement();
                    String query = buildQuery();
                    ResultSet rs = stmt.executeQuery(query);
                    ArrayList<Vehicle> cars = new ArrayList<Vehicle>();

                    while (rs.next()) {
                        cars.add(new Vehicle(rs));
                    }

                    if (cars.size() > 0) {

                        String[] data = new String[cars.size()];
                        for (int i = 0; i < cars.size(); i++) {
                            data[i] = cars.get(i).getYear() + " " + cars.get(i).getBrand() + " " + cars.get(i).getModel();
                        }

                        resultList.setListData(data);
                        scrollPane.setViewportView(resultList);
                    } else {
                        String[] noResults = {"No results found."};
                        resultList.setListData(noResults);
                    }

                    rs.close();
                    stmt.close();
                    c.close();

                } catch ( Exception ex ) {
                    System.err.println( ex.getClass().getName() + ": " + ex.getMessage() );
                    System.exit(0);
                }

            }

        });

        p4.add(searchButton);

    }


    public String buildQuery() {
        String query = "SELECT CAR_CODE, CAR_QUANTITY, VEHICLE_MODEL, VEHICLE_BRAND, VEHICLE_YEAR, \n" +
                "VEHICLE_PASSENGERS, VEHICLE_SIZE, VEHICLE_AC, VEHICLE_COLOR, VEHICLE_AUTOMATIC, PRICE_CODE " +
                "FROM CAR AS C INNER JOIN VEHICLE_DETAILS AS V ON C.VEHICLE_CODE = V.VEHICLE_CODE\n";

        String where = "";

        if (!searchBox.getText().equals("Keyword") && searchBox.getText().trim().length() > 0) {
            where += "VEHICLE_BRAND LIKE '%" + searchBox.getText() + "%'";
        }

        if (make.getSelectedIndex() != 0) {

            if (!where.equals("")) {
                where += " AND ";
            }
            where += " VEHICLE_BRAND = '" + make.getSelectedItem() + "' ";
        }

        if (transmission.getSelectedIndex() != 0) {

            if (!where.equals("")) {
                where += " AND ";
            }

            if (transmission.getSelectedItem().equals("Automatic"))
                where += " VEHICLE_AUTOMATIC = '1' ";
            else
                where += " VEHICLE_AUTOMATIC = '0' ";
        }

        if (color.getSelectedIndex() != 0) {

            if (!where.equals("")) {
                where += " AND ";
            }
            where += " VEHICLE_COLOR = '" + color.getSelectedItem() + "' ";
        }

        if (year.getSelectedIndex() != 0) {

            if (!where.equals("")) {
                where += " AND ";
            }
            where += " VEHICLE_YEAR = '" + year.getSelectedItem() + "' ";
        }

        if (!where.equals("")) {
            query = query.concat("WHERE ").concat(where);
        }

        return query;
    }
}
