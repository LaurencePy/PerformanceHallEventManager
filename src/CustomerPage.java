import java.awt.Font;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

public class CustomerPage extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable tblEvents;

    public CustomerPage(String name) {
        setTitle(name + " - Customer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 450);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JButton btnLogout = new JButton("Logout");
        btnLogout.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });
        btnLogout.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnLogout.setBounds(10, 10, 92, 27);
        contentPane.add(btnLogout);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBounds(10, 50, 960, 340);
        contentPane.add(tabbedPane);

        JPanel viewEventsPanel = new JPanel();
        viewEventsPanel.setLayout(null);
        tabbedPane.addTab("View Events", viewEventsPanel);

        String[] headers = {
            "Select", "Event ID", "Event Category", "Event Type", "Event Name",
            "Age Restrictions", "Quantity", "Ticket Price", "Additional Info"
        };

        DefaultTableModel tableModel = new DefaultTableModel(headers, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0 && ((int) getValueAt(row, 6) > 0);
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return switch (columnIndex) {
                    case 0 -> Boolean.class;   // Checkbox for basket
                    case 1 -> Integer.class;   // Event ID integer
                    case 6 -> Integer.class;   // Quantity integer
                    case 7 -> Double.class;    // Ticket Price double
                    default -> String.class; // otherwise, all string inputs
                };
            }
        };

        List<LiveEvent> events = new ManageEvents("Stock.txt").getAllEvents();

        for (LiveEvent e : events) {
            String info = "";
            if (e instanceof MusicEvent m) {
                info = m.getAdditionalInfo();
            } else if (e instanceof Performance p) {
                info = p.getAdditionalInfo();
            }

            boolean canSelect = e.getQuantityInStock() > 0;

            tableModel.addRow(new Object[] {
                false,
                e.getEventID(),
                e.getEventCategory(),
                e.getEventType(),
                e.getEventName(),
                e.getAgeRestriction(),
                e.getQuantityInStock(),
                e.getTicketPrice(),
                info
            });
        }

        tblEvents = new JTable(tableModel);
        tblEvents.setAutoCreateRowSorter(true);
        tblEvents.setFillsViewportHeight(true);

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        tblEvents.setRowSorter(sorter);
        sorter.setSortKeys(java.util.Collections.singletonList(
            new RowSorter.SortKey(7, SortOrder.ASCENDING)
        ));

        setColumnWidths(tblEvents);

        JScrollPane scrollPane = new JScrollPane(tblEvents);
        scrollPane.setBounds(0, 0, 940, 260);
        viewEventsPanel.add(scrollPane);

        JButton btnAddToBasket = new JButton("Add Selected to Basket");
        btnAddToBasket.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnAddToBasket.setBounds(700, 270, 200, 30);
        viewEventsPanel.add(btnAddToBasket);
        
        
        btnAddToBasket.addActionListener(e -> {
            try (FileWriter writer = new FileWriter("Basket.txt", true)) {
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    Boolean selected = (Boolean) tableModel.getValueAt(i, 0);
                    int quantity = (Integer) tableModel.getValueAt(i, 6);
                    if (selected != null && selected && quantity > 0) {
                        StringBuilder line = new StringBuilder();
                        for (int j = 1; j < tableModel.getColumnCount(); j++) {
                            line.append(tableModel.getValueAt(i, j));
                            if (j < tableModel.getColumnCount() - 1) {
                                line.append(", ");
                            }
                        }
                        writer.write(line.toString() + "\n");
                    }
                }
                JOptionPane.showMessageDialog(this, "Selected events added to basket.");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error writing to basket.txt: " + ex.getMessage());
            }
        });
        
        JButton btnClearBasket = new JButton("Clear Basket");
        btnClearBasket.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnClearBasket.setBounds(20, 270, 142, 33);
        viewEventsPanel.add(btnClearBasket);
        btnClearBasket.addActionListener(e -> {
        	try (FileWriter writer = new FileWriter("Basket.txt", false)) {
        		writer.write("");
        		writer.close();
        		JOptionPane.showMessageDialog(this, "Basket cleared");
        	} catch (IOException e1) {
				e1.printStackTrace();
			}
        });
        
    }

    
    	

    
    
    
    
    private void setColumnWidths(JTable table) {
        TableColumn column;
        for (int i = 0; i < table.getColumnCount(); i++) {
            column = table.getColumnModel().getColumn(i);
            switch (i) {
                case 0 -> column.setPreferredWidth(60);
                case 1 -> column.setPreferredWidth(100);
                case 2 -> column.setPreferredWidth(120);
                case 3 -> column.setPreferredWidth(150);
                case 4 -> column.setPreferredWidth(250);
                case 5 -> column.setPreferredWidth(120);
                case 6 -> column.setPreferredWidth(100);
                case 7 -> column.setPreferredWidth(120);
                case 8 -> column.setPreferredWidth(200);
                default -> column.setPreferredWidth(100);
            }
        }
    }
}
