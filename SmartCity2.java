import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SmartCity {
    private JFrame frame;
    private JTextField addressField;
    private JTextField descriptionField;
    private JComboBox<String> typeComboBox;
    private JTable locationTable;
    private DefaultTableModel tableModel;
    private ArrayList<Location> locations;

    public SmartCity() {
        locations = new ArrayList<>();
        frame = new JFrame("Smart City");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        initUIComponents();

        frame.setVisible(true);
    }

    private void initUIComponents() {
        JLabel addressLabel = new JLabel("ADDRESS:");
        addressLabel.setBounds(20, 20, 100, 30);
        frame.add(addressLabel);

        addressField = new JTextField();
        addressField.setBounds(120, 20, 250, 30);
        frame.add(addressField);

        JLabel descriptionLabel = new JLabel("DESCRIPTION:");
        descriptionLabel.setBounds(20, 70, 100, 30);
        frame.add(descriptionLabel);

        descriptionField = new JTextField();
        descriptionField.setBounds(120, 70, 250, 30);
        frame.add(descriptionField);

        JLabel typeLabel = new JLabel("TYPE:");
        typeLabel.setBounds(20, 120, 100, 30);
        frame.add(typeLabel);

        typeComboBox = new JComboBox<>(new String[]{"ATMs", "Parks", "Hospitals"});
        typeComboBox.setBounds(120, 120, 250, 30);
        frame.add(typeComboBox);

        JButton addBtn = new JButton("ADD LOCATION");
        addBtn.setBounds(20, 170, 150, 30);
        frame.add(addBtn);

        JButton searchBtn = new JButton("SEARCH PLACE");
        searchBtn.setBounds(180, 170, 150, 30);
        frame.add(searchBtn);
        
        tableModel = new DefaultTableModel(new String[]{"Type", "Description", "Address"}, 0);
        locationTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(locationTable);
        scrollPane.setBounds(20, 220, 350, 100);
        frame.add(scrollPane);

        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String address = addressField.getText().trim();
                String description = descriptionField.getText().trim();
                String type = (String) typeComboBox.getSelectedItem();

                if (!address.isEmpty() && !description.isEmpty()) {
                    Location location = new Location(type, description, address);
                    locations.add(location);
                    tableModel.addRow(new Object[]{type, description, address});
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(frame, "Please fill in all fields.");
                }
            }
        });

        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String address = addressField.getText().trim();
                if (!address.isEmpty()) {
                    for (Location loc : locations) {
                        if (loc.getAddress().equalsIgnoreCase(address)) {
                            JOptionPane.showMessageDialog(frame, "Found: " + loc);
                            return;
                        }
                    }
                    JOptionPane.showMessageDialog(frame, "Location not found.");
                } else {
                    JOptionPane.showMessageDialog(frame, "Please enter an address to search.");
                }
            }
        });
    }

    private void clearFields() {
        addressField.setText("");
        descriptionField.setText("");
        typeComboBox.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SmartCity::new);
    }
}

class Location {
    private String type;
    private String description;
    private String address;

    public Location(String type, String description, String address) {
        this.type = type;
        this.description = description;
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return type + ": " + description + " at " + address;
    }
}
