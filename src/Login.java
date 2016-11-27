import java.awt.*;
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
		
		JPanel panel_3 = new JPanel();
		contentPane.add(panel_3);
		
		JButton btnNewUser = new JButton("New User?");
		btnNewUser.setFont(new Font("Helvetica Neue", Font.PLAIN, 14));
		panel_3.add(btnNewUser);
	}
}