import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextArea;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CheckoutFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtPaypalEmailAddress;
	private JTextField txtCardNo;
	private JTextField txtSecurityCode;
	

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
		
		JTextArea txtrSelectPaymentMethod = new JTextArea();
		txtrSelectPaymentMethod.setEditable(false);
		txtrSelectPaymentMethod.setBackground(new Color(241,240,241,255));
		txtrSelectPaymentMethod.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtrSelectPaymentMethod.setText("Select Payment Method:");
		txtrSelectPaymentMethod.setBounds(421, 116, 192, 27);
		contentPane.add(txtrSelectPaymentMethod);
		
		JComboBox payMethodBox = new JComboBox();
		payMethodBox.setFont(new Font("Tahoma", Font.PLAIN, 15));
		payMethodBox.setModel(new DefaultComboBoxModel(new String[] {"Select", "PayPal", "Credit Card"}));
		payMethodBox.setBounds(426, 153, 147, 39);
		contentPane.add(payMethodBox);
		
		JLabel lblTotalHead = new JLabel("Total price: £" + total);
		lblTotalHead.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblTotalHead.setBounds(437, 430, 147, 27);
		contentPane.add(lblTotalHead);
		
		txtPaypalEmailAddress = new JTextField();
		txtPaypalEmailAddress.setVisible(false);
		txtPaypalEmailAddress.setToolTipText("");
		txtPaypalEmailAddress.setBounds(388, 265, 225, 33);
		contentPane.add(txtPaypalEmailAddress);
		txtPaypalEmailAddress.setColumns(10);
		
		JLabel lblPaypal = new JLabel("PayPal email address:");
		lblPaypal.setVisible(false);
		lblPaypal.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPaypal.setBounds(426, 242, 199, 13);
		contentPane.add(lblPaypal);
		
		JButton btnPay = new JButton("Pay");
		btnPay.setVisible(false);
		btnPay.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnPay.setBounds(388, 467, 225, 57);
		contentPane.add(btnPay);
		
		txtCardNo = new JTextField();
		txtCardNo.setVisible(false);
		txtCardNo.setBounds(269, 334, 186, 39);
		contentPane.add(txtCardNo);
		txtCardNo.setColumns(10);
		
		txtSecurityCode = new JTextField();
		txtSecurityCode.setVisible(false);
		txtSecurityCode.setBounds(536, 334, 199, 39);
		contentPane.add(txtSecurityCode);
		txtSecurityCode.setColumns(10);
		
		JLabel lblCardNo = new JLabel("Card Number:");
		lblCardNo.setVisible(false);
		lblCardNo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblCardNo.setBounds(304, 309, 136, 27);
		contentPane.add(lblCardNo);
		
		JLabel lblSecurityCode = new JLabel("Security Code:");
		lblSecurityCode.setVisible(false);
		lblSecurityCode.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblSecurityCode.setBounds(580, 310, 168, 27);
		contentPane.add(lblSecurityCode);
		
		payMethodBox.addActionListener(e -> {
			String selectedMethod = (String) payMethodBox.getSelectedItem();
			if (selectedMethod.equals("PayPal")) {
				txtCardNo.setVisible(false);
				txtSecurityCode.setVisible(false);
				lblCardNo.setVisible(false);
				lblSecurityCode.setVisible(false);
				
				lblPaypal.setVisible(true);
				btnPay.setVisible(true);
				txtPaypalEmailAddress.setVisible(true);
			} else if (selectedMethod.equals("Credit Card")) {
				lblPaypal.setVisible(false);
				txtPaypalEmailAddress.setVisible(false);
				
				btnPay.setVisible(true);
				txtCardNo.setVisible(true);
				txtSecurityCode.setVisible(true);
				lblCardNo.setVisible(true);
				lblSecurityCode.setVisible(true);
			} else {
				lblPaypal.setVisible(false);
				btnPay.setVisible(false);
				txtPaypalEmailAddress.setVisible(false);
				txtCardNo.setVisible(false);
				txtSecurityCode.setVisible(false);
				lblCardNo.setVisible(false);
				lblSecurityCode.setVisible(false);
			}
		});
		
		btnPay.addActionListener(e -> {
            String cardNo = txtCardNo.getText().trim();
            String selectedMethod = (String) payMethodBox.getSelectedItem();
            
            if (selectedMethod.equals("Credit Card")) {
            if (!cardNo.matches("\\d{6}")) {
                JOptionPane.showMessageDialog(null, "Card number must be 6 digits.");
                return;
            }
            String securityCode = txtSecurityCode.getText().trim();
            if (!securityCode.matches("\\d{3}")) {
                JOptionPane.showMessageDialog(null, "Security code must be 3 digits.");
                return;
            }
            } else if (selectedMethod.equals("PayPal")) {
                String PaypalEmailAddress = txtPaypalEmailAddress.getText().trim();
                Pattern pattern = Pattern.compile("[A-Za-z0-9]+@[A-Za-z0-9]+", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(PaypalEmailAddress);
                boolean matchFound = matcher.find();
                if (!matchFound) {
                    JOptionPane.showMessageDialog(null, "Invalid email address");
                    return;
                }
            }
		});
		
		
		

		

	}
}
