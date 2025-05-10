import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.awt.event.ActionEvent;

public class LoginFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame frame = new LoginFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
				    Path basketPath = Paths.get("Basket.txt");
				    long lineCount = Files.lines(basketPath).count();
				    if (lineCount > 0) {
				        try (FileWriter writer = new FileWriter("Basket.txt", false)) {
				            writer.write("");
				        }
				    }
				} catch (IOException ex) {
				    ex.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 10, 416, 243);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 15));
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
		model.addElement("Select");

		ManageUsers manageUsers = new ManageUsers("UserAccounts.txt");
		for (User user : manageUsers.getUsers()) {
		    model.addElement(user.getUserID() + " - " + user.getName());
		}

		comboBox.setModel(model);

		comboBox.setBounds(113, 121, 155, 26);
		panel.add(comboBox);
		
		JLabel userText = new JLabel("Select your user:");
		userText.setFont(new Font("Tahoma", Font.PLAIN, 15));
		userText.setBounds(130, 77, 155, 34);
		panel.add(userText);
		
		JLabel titleText = new JLabel("Login");
		titleText.setFont(new Font("Tahoma", Font.PLAIN, 20));
		titleText.setBounds(163, 10, 59, 26);
		panel.add(titleText);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(e -> {
				String selectedName = (String) comboBox.getSelectedItem();
				ManageUsers userManager = new ManageUsers("UserAccounts.txt");
				User user = userManager.getUserFromName(selectedName);
				
				try {
				    Path basketPath = Paths.get("Basket.txt");
				    long lineCount = Files.lines(basketPath).count();
				    if (lineCount > 0) {
				        try (FileWriter writer = new FileWriter("Basket.txt", false)) {
				            writer.write("");
				        }
				    }
				} catch (IOException ex) {
				    ex.printStackTrace();
				    JOptionPane.showMessageDialog(this, "Basket error");
				}

				
				if (user != null && !selectedName.equals("Select")) {
				    user.openPage();
				    dispose();
				} else {
				    JOptionPane.showMessageDialog(null, "Invalid user selected.");
				}
				
		});
		btnLogin.setBackground(new Color(192, 192, 192));
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnLogin.setBounds(99, 169, 186, 26);
		panel.add(btnLogin);
	}
}
