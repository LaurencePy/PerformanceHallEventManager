import java.awt.EventQueue;
import java.awt.Font;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class ReceiptFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public ReceiptFrame(Address address, double total, String selectedMethod, String identifier) {
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setBounds(100, 100, 500, 300);
	    contentPane = new JPanel();
	    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	    contentPane.setLayout(null);
	    setContentPane(contentPane);

	    JButton btnLogout = new JButton("Logout");
        btnLogout.addActionListener(e -> {
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
            new LoginFrame().setVisible(true);
            dispose();
        });
	    btnLogout.setFont(new Font("Tahoma", Font.PLAIN, 15));
	    btnLogout.setBounds(20, 10, 90, 27);
	    contentPane.add(btnLogout);


	    String todayDate = java.time.LocalDate.now().toString();
	    String fullAddress = address.getFullAddress();
	    String receiptText = "";
	    if (selectedMethod.equals("PayPal")) {
	    	receiptText = String.format("£%.2f paid via PayPal using %s on %s, and the billing address is %s.",
                    total, identifier, todayDate, fullAddress);
	    } else if (selectedMethod.equals("Credit Card")) {
	    	receiptText = String.format("£%.2f paid by Credit Card using %s on %s, and the billing address is %s.",
                    total, identifier, todayDate, fullAddress);
	    } else {
	    	receiptText = "Error. Invalid payment method selected.";
	    }


	    JTextArea receiptArea = new JTextArea(receiptText);
	    receiptArea.setBounds(20, 50, 440, 180);
	    receiptArea.setLineWrap(true);
	    receiptArea.setWrapStyleWord(true);
	    receiptArea.setEditable(false);
	    contentPane.add(receiptArea);
	}


}
