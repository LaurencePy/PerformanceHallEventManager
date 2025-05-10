import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class CustomerPage extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTable tblEvents;
    private JTable tblBasket;
    private JTextField txtEventIDFilter;
    private JTextField txtLanguageFilter;
    private JButton btnFilter;
    private JButton btnLogout;
	private Customer currentCustomer;

    public CustomerPage(Customer customer) {
    	
    	/*
    	 Page opened if selected user on login is a customer role according to UserAccounts.txt
    	 */
    	
    	this.currentCustomer = customer;
    	setTitle(customer.getUserID() + " - " + customer.getName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 600);

        JPanel contentPane = new JPanel(new BorderLayout(10, 10));
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);

        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        btnLogout = new JButton("Logout");
        btnLogout.setFont(new Font("Tahoma", Font.PLAIN, 15));
        topPanel.add(btnLogout, BorderLayout.WEST);

        txtEventIDFilter = new JTextField(5);
        txtEventIDFilter.setDocument(new PlainDocument() {
            @Override
            public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {
                if ((getLength() + str.length()) <= 5 && str.matches("\\d+")) {
                    super.insertString(offset, str, a);
                } // filter validation/logic
            }
        });

        txtLanguageFilter = new JTextField(10);
        btnFilter = new JButton("Filter");

        filterPanel.add(new JLabel("Filter by Event ID:"));
        filterPanel.add(txtEventIDFilter);
        filterPanel.add(new JLabel("Filter by Language:"));
        filterPanel.add(txtLanguageFilter);
        filterPanel.add(btnFilter);

        topPanel.add(filterPanel, BorderLayout.CENTER);
        contentPane.add(topPanel, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();
        contentPane.add(tabbedPane, BorderLayout.CENTER);

        tabbedPane.addChangeListener(e -> {
            int selectedIndex = tabbedPane.getSelectedIndex();
            String selectedTitle = tabbedPane.getTitleAt(selectedIndex);
            filterPanel.setVisible("View Events".equals(selectedTitle));
        });

        JPanel viewEventsPanel = new JPanel();
        viewEventsPanel.setLayout(new BorderLayout(10, 10));
        tabbedPane.addTab("View Events", viewEventsPanel);

        String[] headers = { "Select", "Event ID", "Event Category", "Event Type", "Event Name", "Age Restrictions", "Quantity", "Ticket Price", "Additional Info" };

        DefaultTableModel tableModel = new DefaultTableModel(headers, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0 && ((int) getValueAt(row, 6) > 0);
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return switch (columnIndex) {
                    case 0 -> Boolean.class;
                    case 1 -> Integer.class;
                    case 6 -> Integer.class;
                    case 7 -> Double.class;
                    default -> String.class;
                };
            }
        }; 
        
        /* populate view events table
        */
        List<LiveEvent> events = new ManageEvents("Stock.txt").getAllEvents();
        for (LiveEvent e : events) {
            String info = e instanceof MusicEvent m ? m.getAdditionalInfo() :
                          e instanceof Performance p ? p.getAdditionalInfo() : "";

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
            new RowSorter.SortKey(7, SortOrder.ASCENDING))); // sort by ticket price ascending

        setColumnWidths(tblEvents);
        JScrollPane scrollPane = new JScrollPane(tblEvents);
        viewEventsPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel viewEventsButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnAddToBasket = new JButton("Add Selected to Basket");
        viewEventsButtonPanel.add(btnAddToBasket);
        viewEventsPanel.add(viewEventsButtonPanel, BorderLayout.SOUTH);

        JPanel basketTabPanel = new JPanel(new BorderLayout(10, 10));
        tabbedPane.addTab("Basket", basketTabPanel);
        
        /*
        Loading basket from Basket.txt
        */
        DefaultTableModel basketModel = new DefaultTableModel(
            new Object[]{"Event ID", "Category", "Type", "Name", "Age", "Quantity", "Price", "Info"}, 0);
        tblBasket = new JTable(basketModel);
        tblBasket.setFillsViewportHeight(true);
        try {
            List<String> lines = Files.readAllLines(Paths.get("Basket.txt"));
            for (String line : lines) {
                String[] parts = line.split(",\\s*");
                if (parts.length == 8) {
                    basketModel.addRow(parts);
                } 
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading basket contents");
        }

        

        JScrollPane basketScrollPane = new JScrollPane(tblBasket);
        basketScrollPane.setPreferredSize(new Dimension(940, 400));
        basketTabPanel.add(basketScrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnClearBasket = new JButton("Clear Basket");
        JButton btnCheckout = new JButton("Checkout");

        buttonPanel.add(btnClearBasket);
        buttonPanel.add(btnCheckout);

        basketTabPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        
        /*
        Populating basket from items added to basket using add to basket button
        */
        btnAddToBasket.addActionListener(e -> {
            try (FileWriter writer = new FileWriter("Basket.txt", true)) {
            	Boolean itemsSelected = false;
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    Boolean selected = (Boolean) tableModel.getValueAt(i, 0);
                    int quantity = (Integer) tableModel.getValueAt(i, 6);
                    if (selected != null && selected && quantity > 0) {
                    	itemsSelected = true;
                        StringBuilder line = new StringBuilder();
                        for (int j = 1; j < tableModel.getColumnCount(); j++) {
                            line.append(tableModel.getValueAt(i, j));
                            if (j < tableModel.getColumnCount() - 1) {
                                line.append(", ");
                            }
                        }
                        writer.write(line.toString() + "\n");

                        basketModel.addRow(new Object[] {
                            tableModel.getValueAt(i, 1),
                            tableModel.getValueAt(i, 2),
                            tableModel.getValueAt(i, 3),
                            tableModel.getValueAt(i, 4),
                            tableModel.getValueAt(i, 5),
                            tableModel.getValueAt(i, 6),
                            tableModel.getValueAt(i, 7),
                            tableModel.getValueAt(i, 8)
                        });
                    }
                }
                if (itemsSelected == true) {
                	JOptionPane.showMessageDialog(this, "Selected events added to basket");
                } else {
                	JOptionPane.showMessageDialog(this, "You have not selected any events");
                }
                
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error writing to basket.txt: " + ex.getMessage());
            }
        });

        
        /*
         Clear basket by overwriting file to empty
         */
        btnClearBasket.addActionListener(e -> {
            try {
                Path basketPath = Paths.get("Basket.txt");
                long lineCount = Files.lines(basketPath).count();

                if (lineCount > 0) {
                    try (FileWriter writer = new FileWriter("Basket.txt", false)) {
                        writer.write("");
                    }
                    basketModel.setRowCount(0);
                    JOptionPane.showMessageDialog(this, "Basket cleared");
                } else {
                    JOptionPane.showMessageDialog(this, "Your basket is empty");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Basket error");
            }
        });

        /*
         * checking if there are items in the basket, 
         * then adding up prices from each item for the total to pass on to the checkout
         */
        btnCheckout.addActionListener(e -> {
            if (basketModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Your basket is empty. Please add items before checking out");
                return;
            }

            double total = 0;
            for (int i = 0; i < basketModel.getRowCount(); i++) {
                String priceStr = basketModel.getValueAt(i, 6).toString();
                double price = Double.parseDouble(priceStr);
                total += price;
            }

            
            new CheckoutFrame(currentCustomer, total).setVisible(true);

            dispose();
        });
        
        // clear basket on logout. basket only retained while user is logged in
        btnLogout.addActionListener(e -> {
            try {
                Path basketPath = Paths.get("Basket.txt");
                long lineCount = Files.lines(basketPath).count();
                if (lineCount > 0) {
                    try (FileWriter writer = new FileWriter("Basket.txt", false)) {
                        writer.write("");
                    }
                    basketModel.setRowCount(0);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Basket error");
            }
            new LoginFrame().setVisible(true);
            dispose();
        });

        btnFilter.addActionListener(e -> {
            String idInput = txtEventIDFilter.getText().trim();
            String langInput = txtLanguageFilter.getText().trim().toLowerCase();

            DefaultTableModel model = (DefaultTableModel) tblEvents.getModel();
            model.setRowCount(0);

            List<LiveEvent> filteredEvents = new ManageEvents("Stock.txt").getAllEvents();
            
            // filter by checking for matches in items in teh table to the inputs
            for (LiveEvent ev : filteredEvents) {
                boolean matchesID = idInput.isEmpty() || String.valueOf(ev.getEventID()).equals(idInput);
                boolean matchesLang = true;

                if (!langInput.isEmpty()) {
                    if (ev instanceof Performance p) {
                        matchesLang = p.getAdditionalInfo().toLowerCase().contains(langInput);
                    } else {
                        matchesLang = false;
                    }
                }
                
                // if there are matches, then add to the table and reload.
                // effectively a new table
                if (matchesID && matchesLang) {
                    String info = ev instanceof MusicEvent m ? m.getAdditionalInfo() :
                                  ev instanceof Performance p ? p.getAdditionalInfo() : "";

                    model.addRow(new Object[] {
                        false,
                        ev.getEventID(),
                        ev.getEventCategory(),
                        ev.getEventType(),
                        ev.getEventName(),
                        ev.getAgeRestriction(),
                        ev.getQuantityInStock(),
                        ev.getTicketPrice(),
                        info
                    });
                    
                    
                }
            }
        });
    }


    // making spacing for the table headers
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
