import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.*;
import javax.swing.border.EmptyBorder;


public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField lnameField;
	private JTextField txtUserId;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Login() {
		
		setTitle("Login");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(1, 1, 1, 1));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(6, 6, 4, 4));
		
		JPanel panel = new JPanel();
		contentPane.add(panel);
		
		lnameField = new JTextField();
		lnameField.setText("Last Name");
		lnameField.setFont(new Font("Helvetica Neue", Font.PLAIN, 12));
		panel.add(lnameField);
		lnameField.setColumns(15);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1);
		
		txtUserId = new JTextField();
		txtUserId.setText("User ID");
		txtUserId.setFont(new Font("Helvetica Neue", Font.PLAIN, 12));
		panel_1.add(txtUserId);
		txtUserId.setColumns(15);
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setFont(new Font("Helvetica Neue", Font.PLAIN, 14));
		panel_2.add(btnLogin);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection c = null;
                    Statement stmt = null;
                    Class.forName("org.sqlite.JDBC");
                    c = DriverManager.getConnection("jdbc:sqlite:CarRentalService.db");
                    c.setAutoCommit(false);

                    stmt = c.createStatement();
                    String sql = "SELECT * FROM CUSTOMER WHERE `CUSTOMER_LNAME` = '" + lnameField.getText() +
                            "' AND `CUSTOMER_ID` = '" + txtUserId.getText() + "';";

                    ResultSet rs = stmt.executeQuery(sql);
                    while (rs.next()) {
                        if (rs.getInt("CUSTOMER_ID") >=0) {
                            new CarSearch().setVisible(true);
                            setVisible(false);
                        }
                    }

                    rs.close();
                    stmt.close();
                    c.close();
                } catch (Exception ex) {
                    System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
                    System.exit(0);
                }
            }
        });
		
		JPanel panel_3 = new JPanel();
		contentPane.add(panel_3);
		
		JButton btnNewUser = new JButton("New User?");
        btnNewUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Registration().setVisible(true);
                setVisible(false);
            }
        });

		btnNewUser.setFont(new Font("Helvetica Neue", Font.PLAIN, 14));
		panel_3.add(btnNewUser);
	}
}