import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import javax.swing.JButton;

public class CustomerPage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;


	/**
	 * Create the frame.
	 */
	public CustomerPage(String name) {
        setTitle(name + " - Customer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JButton btnLogout = new JButton("Logout");
        btnLogout.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });
        btnLogout.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnLogout.setBounds(3, 10, 92, 27);
        contentPane.add(btnLogout);
    }

}

