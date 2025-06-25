import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PharmacyBillingSystem extends JFrame {

    // UI Components
    private JTextField txtDrugName, txtPrice, txtQuantity, txtTotal;
    private JTable billTable;
    private DefaultTableModel tableModel;
    private JButton btnAdd, btnClear, btnGenerateBill;

    public PharmacyBillingSystem() {
        // Set up the frame
        setTitle("Pharmacy Billing System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel for input fields
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2, 10, 10));

        inputPanel.add(new JLabel("Drug Name:"));
        txtDrugName = new JTextField();
        inputPanel.add(txtDrugName);

        inputPanel.add(new JLabel("Price:"));
        txtPrice = new JTextField();
        inputPanel.add(txtPrice);

        inputPanel.add(new JLabel("Quantity:"));
        txtQuantity = new JTextField();
        inputPanel.add(txtQuantity);

        inputPanel.add(new JLabel("Total:"));
        txtTotal = new JTextField();
        txtTotal.setEditable(false);
        inputPanel.add(txtTotal);

        // Add input panel to the frame
        add(inputPanel, BorderLayout.NORTH);

        // Table for displaying added items
        String[] columnNames = {"Drug Name", "Price", "Quantity", "Total"};
        tableModel = new DefaultTableModel(columnNames, 0);
        billTable = new JTable(tableModel);
        JScrollPane tableScroll = new JScrollPane(billTable);
        add(tableScroll, BorderLayout.CENTER);

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        // Add item button
        btnAdd = new JButton("Add Item");
        buttonPanel.add(btnAdd);

        // Clear button
        btnClear = new JButton("Clear");
        buttonPanel.add(btnClear);

        // Generate Bill button
        btnGenerateBill = new JButton("Generate Bill");
        buttonPanel.add(btnGenerateBill);

        // Add button panel to the frame
        add(buttonPanel, BorderLayout.SOUTH);

        // Action Listeners
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addItemToBill();
            }
        });

        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields(); // Clear input fields
                clearTable();  // Clear the table
            }
        });

        btnGenerateBill.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateBill();
            }
        });
    }

    // Method to add item to the table
    private void addItemToBill() {
        String drugName = txtDrugName.getText();
        String priceStr = txtPrice.getText();
        String quantityStr = txtQuantity.getText();

        if (drugName.isEmpty() || priceStr.isEmpty() || quantityStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!");
            return;
        }

        try {
            double price = Double.parseDouble(priceStr);
            int quantity = Integer.parseInt(quantityStr);
            double total = price * quantity;

            // Add row to the table
            tableModel.addRow(new Object[]{drugName, price, quantity, total});
            txtTotal.setText(String.valueOf(total));

            // Clear the input fields for the next entry
            clearFields();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input for price or quantity.");
        }
    }

    // Method to clear input fields
    private void clearFields() {
        txtDrugName.setText("");
        txtPrice.setText("");
        txtQuantity.setText("");
        txtTotal.setText("");
    }

    // Method to clear the table
    private void clearTable() {
        tableModel.setRowCount(0); // Removes all rows from the table model
    }

    // Method to generate the bill
    private void generateBill() {
        double grandTotal = 0.0;

        // Loop through the table rows and calculate the grand total
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            grandTotal += (double) tableModel.getValueAt(i, 3);
        }

        // Show the bill in a dialog box
        JOptionPane.showMessageDialog(this, "Total Bill: " + grandTotal);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Create and display the form
                PharmacyBillingSystem frame = new PharmacyBillingSystem();
                frame.setVisible(true);
            }
        });
    }
}
