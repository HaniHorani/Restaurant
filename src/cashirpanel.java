package src;
import src.models.Meal;
import src.models.Order;
import src.models.OrderDetail;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class cashirpanel extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel totalLabel;
    private JTextField tipField;
    private JLabel grandTotalLabel;

    public cashirpanel() {
        this.setLayout(new BorderLayout());

        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setBackground(Color.WHITE);

        String[] columnNames = {"Name", "Price", "Quantity", "Total"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.LEFT);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

        JScrollPane tableScroll = new JScrollPane(table);
        table.getTableHeader().setReorderingAllowed(false);
        mainContent.add(tableScroll, BorderLayout.CENTER);

        this.add(mainContent, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setPreferredSize(new Dimension(250, 0));

        JPanel foodPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        foodPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        addFoodButton(foodPanel, "Burger", 5.00);
        addFoodButton(foodPanel, "Pizza", 8.00);
        addFoodButton(foodPanel, "Pasta", 7.00);
        addFoodButton(foodPanel, "Salad", 4.00);
        addFoodButton(foodPanel, "Drink", 2.00);

        rightPanel.add(foodPanel, BorderLayout.NORTH);

        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.Y_AXIS));
        actionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel orderTypePanel = new JPanel();
        orderTypePanel.setLayout(new BoxLayout(orderTypePanel, BoxLayout.Y_AXIS));
        JLabel orderTypeLabel = new JLabel("Order Type");
        orderTypeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JComboBox<String> orderType = new JComboBox<>(new String[]{"In Resturant", "Delivery", "Else"});
        orderType.setMaximumSize(new Dimension(200, 30));

        orderTypePanel.add(orderTypeLabel);
        orderTypePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        orderTypePanel.add(orderType);

        JButton saleButton = new JButton("SALE");
        saleButton.addActionListener(e -> {
            List<OrderDetail> orderDetails = new ArrayList<>();
            orderDetails.add(new OrderDetail(1,new Meal()));
//            new Order();

            int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to confirm?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                tableModel.setRowCount(0);
                totalLabel.setText("0.0");
                tipField.setText("");
                grandTotalLabel.setText("0.0");
                JOptionPane.showMessageDialog(this, "Sale completed!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        saleButton.setFocusable(false);
        saleButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        actionPanel.add(orderTypePanel);
        actionPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        actionPanel.add(saleButton);

        rightPanel.add(actionPanel, BorderLayout.SOUTH);

        this.add(rightPanel, BorderLayout.EAST);

        JPanel summaryPanel = new JPanel(new GridBagLayout());
        summaryPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        summaryPanel.add(new JLabel("Total:"), gbc);

        gbc.gridx = 1;
        totalLabel = new JLabel("0.0");
        summaryPanel.add(totalLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        summaryPanel.add(new JLabel("Tip:"), gbc);

        gbc.gridx = 1;
        tipField = new JTextField();
        tipField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                calculateGrandTotal();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                calculateGrandTotal();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                calculateGrandTotal();
            }
        });
        summaryPanel.add(tipField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        summaryPanel.add(new JLabel("Grand Total:"), gbc);

        gbc.gridx = 1;
        grandTotalLabel = new JLabel("0.0");
        summaryPanel.add(grandTotalLabel, gbc);

        this.add(summaryPanel, BorderLayout.SOUTH);
    }

    private void addFoodButton(JPanel panel, String foodName, double price) {
        JButton foodButton = new JButton(foodName + " ($" + (int) price + ")");
        foodButton.setFocusable(false);
        foodButton.addActionListener(e -> updateFoodInTable(foodName, price));
        panel.add(foodButton);
    }

    private void updateFoodInTable(String foodName, double price) {
        boolean itemExists = false;

        for (int row = 0; row < tableModel.getRowCount(); row++) {
            String existingFoodName = (String) tableModel.getValueAt(row, 0);
            if (existingFoodName.equals(foodName)) {
                int quantity = (int) tableModel.getValueAt(row, 2);
                quantity++;
                double total = quantity * price;
                tableModel.setValueAt(quantity, row, 2);
                tableModel.setValueAt(total, row, 3);
                itemExists = true;
                break;
            }
        }

        if (!itemExists) {
            int quantity = 1;
            double total = quantity * price;
            tableModel.addRow(new Object[]{foodName, price, quantity, total});
        }

        updateTotal();
    }

    private void updateTotal() {
        double total = 0.0;
        for (int row = 0; row < tableModel.getRowCount(); row++) {
            total += (double) tableModel.getValueAt(row, 3);
        }
        totalLabel.setText(String.format("%.2f", total));
        calculateGrandTotal();
    }

    private void calculateGrandTotal() {
        try {
            double total = Double.parseDouble(totalLabel.getText());
            double tip = tipField.getText().isEmpty() ? 0.0 : Double.parseDouble(tipField.getText());
            double grandTotal = total + tip;
            grandTotalLabel.setText(String.format("%.2f", grandTotal));
        } catch (NumberFormatException e) {
            grandTotalLabel.setText("Error");
        }
    }
}