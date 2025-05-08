import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;

public class CheckoutFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public CheckoutFrame(String name, double total) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle(name + " - Customer");
        setBounds(100, 100, 1000, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnBack = new JButton("← Back");
		btnBack.addActionListener(e -> {
            new CustomerPage(name).setVisible(true);
            dispose();
        });
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnBack.setBounds(10, 10, 102, 27);
		contentPane.add(btnBack);
	}
}
