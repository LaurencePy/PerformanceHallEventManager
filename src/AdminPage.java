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

            tableModel.addRow(new Object[] {
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
        tblEvents.setAutoCreateRowSorter(true);
        tblEvents.setFillsViewportHeight(true);


        setColumnWidths(tblEvents);


        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        tblEvents.setRowSorter(sorter);
        sorter.setSortKeys(java.util.Collections.singletonList(
            new RowSorter.SortKey(7, SortOrder.ASCENDING)
        ));

        JScrollPane scrollPane = new JScrollPane(tblEvents);
        scrollPane.setBounds(10, 50, 910, 280);
        contentPane.add(scrollPane);
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
