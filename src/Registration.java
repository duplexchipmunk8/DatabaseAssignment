import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Registration extends JFrame {

	private JPanel contentPane;
	private JTextField txtFirstName;
	private JTextField txtLastName;
	private JPanel panel_1;
	private JTextField txtStreet;
	private JPanel panel_2;
	private JTextField txtCity;
	private JTextField txtProvince;
	private JTextField txtPostalCode;
	private JPanel panel_3;
	private JButton btnNewButton;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Registration frame = new Registration();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Registration() {
		
		setTitle("Registration");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(1, 1, 1, 1));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(6, 6, 4, 4));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		txtFirstName = new JTextField();
		txtFirstName.setText("First Name");
		txtFirstName.setFont(new Font("Helvetica Neue", Font.PLAIN, 12));
		panel.add(txtFirstName);
		txtFirstName.setColumns(15);
		
		txtLastName = new JTextField();
		txtLastName.setText("Last Name");
		txtLastName.setFont(new Font("Helvetica Neue", Font.PLAIN, 12));
		panel.add(txtLastName);
		txtLastName.setColumns(15);
		
		panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.WEST);
		
		txtStreet = new JTextField();
		txtStreet.setFont(new Font("Helvetica Neue", Font.PLAIN, 12));
		txtStreet.setText("Street");
		panel_1.add(txtStreet);
		txtStreet.setColumns(25);
		
		panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.WEST);
		
		txtCity = new JTextField();
		txtCity.setFont(new Font("Helvetica Neue", Font.PLAIN, 12));
		txtCity.setText("City");
		panel_2.add(txtCity);
		txtCity.setColumns(15);
		
		txtProvince = new JTextField();
		txtProvince.setText("Province");
		txtProvince.setFont(new Font("Helvetica Neue", Font.PLAIN, 12));
		panel_2.add(txtProvince);
		txtProvince.setColumns(7);
		
		txtPostalCode = new JTextField();
		txtPostalCode.setText("Postal Code");
		txtPostalCode.setFont(new Font("Helvetica Neue", Font.PLAIN, 12));
		panel_2.add(txtPostalCode);
		txtPostalCode.setColumns(7);
		
		panel_3 = new JPanel();
		contentPane.add(panel_3, BorderLayout.WEST);
		
		btnNewButton = new JButton("Register");
		btnNewButton.setFont(new Font("Helvetica Neue", Font.PLAIN, 14));
		panel_3.add(btnNewButton);
	}
}