import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.List;

public class AdminPage extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable tblEvents;

    public AdminPage(String name) {
        setTitle(name + " - Admin");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 950, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JLabel label = new JLabel("Welcome, " + name);
        label.setBounds(410, 10, 200, 25);
        label.setFont(new Font("Tahoma", Font.PLAIN, 15));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(label);

        JButton btnLogout = new JButton("Logout");
        btnLogout.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });
        btnLogout.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnLogout.setBounds(10, 10, 92, 27);
        contentPane.add(btnLogout);


        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBounds(10, 50, 910, 300);
        contentPane.add(tabbedPane);


        JPanel viewEventsPanel = new JPanel();
        viewEventsPanel.setLayout(null);
        tabbedPane.addTab("View Events", viewEventsPanel);

        String[] headers = {
            "Event ID", "Event Category", "Event Type", "Event Name",
            "Age Restrictions", "Quantity", "Performance Fee", "Ticket Price", "Additional Info"
        };

        DefaultTableModel tableModel = new DefaultTableModel(headers, 0);
        List<LiveEvent> events = new ManageEvents("Stock.txt").getAllEvents();

        for (LiveEvent e : events) {
            String eventType = e instanceof MusicEvent ? "MusicEvent" : "Performance";
            String info = "";

            if (e instanceof MusicEvent m) {
                info = m.getAdditionalInfo();
            } else if (e instanceof Performance p) {
                info = p.getAdditionalInfo();
            }

            tableModel.addRow(new Object[]{
                e.getEventID(),
                e.getEventCategory(),
                eventType,
                e.getEventName(),
                e.getAgeRestriction(),
                e.getQuantityInStock(),
                e.getPerformanceFee(),
                e.getTicketPrice(),
                info
            });
        }

        tblEvents = new JTable(tableModel);
        tblEvents.setEnabled(false);
        tblEvents.setAutoCreateRowSorter(true);
        tblEvents.setFillsViewportHeight(true);
        setColumnWidths(tblEvents);

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        tblEvents.setRowSorter(sorter);
        sorter.setSortKeys(java.util.Collections.singletonList(
            new RowSorter.SortKey(7, SortOrder.ASCENDING)
        ));

        JScrollPane scrollPane = new JScrollPane(tblEvents);
        scrollPane.setBounds(0, 0, 890, 260);
        viewEventsPanel.add(scrollPane);


        JPanel addEventPanel = new JPanel();
        addEventPanel.setLayout(null);

        String[] ageOptions = { "All", "Adults" };
        String[] categoryOptions = { "Music", "Performance" };

        JLabel lblEventID = new JLabel("Event ID:");
        lblEventID.setBounds(20, 20, 100, 25);
        addEventPanel.add(lblEventID);
        JTextField txtEventID = new JTextField();
        txtEventID.setBounds(130, 20, 200, 25);
        addEventPanel.add(txtEventID);

        JLabel lblCategory = new JLabel("Category:");
        lblCategory.setBounds(20, 60, 100, 25);
        addEventPanel.add(lblCategory);
        JComboBox<String> cmbCategory = new JComboBox<>(categoryOptions);
        cmbCategory.setBounds(130, 60, 200, 25);
        addEventPanel.add(cmbCategory);

        JLabel lblType = new JLabel("Event Type:");
        lblType.setBounds(20, 100, 100, 25);
        addEventPanel.add(lblType);
        JTextField txtType = new JTextField();
        txtType.setBounds(130, 100, 200, 25);
        addEventPanel.add(txtType);

        JLabel lblName = new JLabel("Event Name:");
        lblName.setBounds(20, 140, 100, 25);
        addEventPanel.add(lblName);
        JTextField txtName = new JTextField();
        txtName.setBounds(130, 140, 200, 25);
        addEventPanel.add(txtName);

        JLabel lblAge = new JLabel("Age Restriction:");
        lblAge.setBounds(20, 180, 100, 25);
        addEventPanel.add(lblAge);
        JComboBox<String> cmbAge = new JComboBox<>(ageOptions);
        cmbAge.setBounds(130, 180, 200, 25);
        addEventPanel.add(cmbAge);

        JLabel lblQty = new JLabel("Quantity:");
        lblQty.setBounds(370, 20, 100, 25);
        addEventPanel.add(lblQty);
        JTextField txtQty = new JTextField();
        txtQty.setBounds(480, 20, 200, 25);
        addEventPanel.add(txtQty);

        JLabel lblFee = new JLabel("Performance Fee:");
        lblFee.setBounds(370, 60, 100, 25);
        addEventPanel.add(lblFee);
        JTextField txtFee = new JTextField();
        txtFee.setBounds(480, 60, 200, 25);
        addEventPanel.add(txtFee);

        JLabel lblPrice = new JLabel("Ticket Price:");
        lblPrice.setBounds(370, 100, 100, 25);
        addEventPanel.add(lblPrice);
        JTextField txtPrice = new JTextField();
        txtPrice.setBounds(480, 100, 200, 25);
        addEventPanel.add(txtPrice);

        JLabel lblInfo = new JLabel("Additional Info:");
        lblInfo.setBounds(370, 140, 100, 25);
        addEventPanel.add(lblInfo);
        JTextField txtInfo = new JTextField();
        txtInfo.setBounds(480, 140, 200, 25);
        addEventPanel.add(txtInfo);

        // Submit Button
        JButton btnSubmit = new JButton("Add Event");
        btnSubmit.setBounds(370, 200, 150, 30);
        addEventPanel.add(btnSubmit);

        tabbedPane.addTab("Add Event", addEventPanel);
    }

    private void setColumnWidths(JTable table) {
        TableColumn column;
        for (int i = 0; i < table.getColumnCount(); i++) {
            column = table.getColumnModel().getColumn(i);
            switch (i) {
                case 0:
                    column.setPreferredWidth(100);
                    break;
                case 1:
                    column.setPreferredWidth(120);
                    break;
                case 2:
                    column.setPreferredWidth(150);
                    break;
                case 3:
                    column.setPreferredWidth(250);
                    break;
                case 4:
                    column.setPreferredWidth(120);
                    break;
                case 5:
                    column.setPreferredWidth(100);
                    break;
                case 6:
                    column.setPreferredWidth(120);
                    break;
                case 7:
                    column.setPreferredWidth(120);
                    break;
                case 8:
                    column.setPreferredWidth(200);
                    break;
                default:
                    column.setPreferredWidth(100);
            }
        }
    }
}
