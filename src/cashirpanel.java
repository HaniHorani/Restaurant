package src;

import src.models.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class cashirpanel extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel totalLabel;
    private JTextField tipField;
    private JLabel grandTotalLabel;
    private JComboBox<String> orderType;
    private JComboBox<String> payType;
    private List<Meal> meals;

    public cashirpanel() {
        try {
            meals = Meal.loadFromFile();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        this.setLayout(new BorderLayout());

        String[] columnNames = {"Name", "Price", "Quantity", "Total", "Remove", "Details"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4 || column == 5;
            }
        };

        table = new JTable(tableModel) {
            @Override
            public TableCellRenderer getCellRenderer(int row, int column) {
                if (column == 4 || column == 5) {
                    return new ButtonRenderer();
                }
                return super.getCellRenderer(row, column);
            }

            @Override
            public TableCellEditor getCellEditor(int row, int column) {
                if (column == 4 || column == 5) {
                    return new ButtonEditor((ActionEvent e) -> {
                        if (column == 4) {
                            removeRow(row);
                        } else if (column == 5) {
                            showDetails(row);
                        }
                    });
                }
                return super.getCellEditor(row, column);
            }
        };
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int column = table.columnAtPoint(e.getPoint());

                if (column == 4) {
                    int quantity = (int) tableModel.getValueAt(row, 2);
                    if (quantity > 1) {
                        quantity--;
                        double price = (double) tableModel.getValueAt(row, 1);
                        double total = quantity * price;
                        tableModel.setValueAt(quantity, row, 2);
                        tableModel.setValueAt(total, row, 3);
                    } else {
                        tableModel.removeRow(row);
                    }
                    cashirpanel.this.calculategrandtotal();
                } else if (column == 5) {
                    showDetails(row);
                }
            }
        });

        JScrollPane tableScroll = new JScrollPane(table);
        table.getTableHeader().setReorderingAllowed(false);

        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.add(tableScroll, BorderLayout.CENTER);
        this.add(mainContent, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setPreferredSize(new Dimension(250, 0));

        JPanel foodPanel = new JPanel();
        foodPanel.setLayout(new BoxLayout(foodPanel, BoxLayout.Y_AXIS));
        foodPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (Meal meal : meals) {
            JButton foodButton = new JButton(meal.name + " ($" + meal.Price + ")");
            foodButton.addActionListener(e -> updatefoodintable(meal.name, meal.Price));
            foodPanel.add(foodButton);
        }

        JScrollPane scrollPane = new JScrollPane(foodPanel);
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.Y_AXIS));
        actionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel orderTypeLabel = new JLabel("Order Type:");
        orderType = new JComboBox<>(new String[]{"In Restaurant", "Delivery", "Else"});
        orderType.setMaximumSize(new Dimension(200, 30));

        JLabel payTypeLabel = new JLabel("Pay Type:");
        payType = new JComboBox<>(new String[]{"Cash", "Card"});
        payType.setMaximumSize(new Dimension(200, 30));

        JButton saleButton = new JButton("SALE");
        saleButton.addActionListener(e -> completeSale());
        saleButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        actionPanel.add(orderTypeLabel);
        actionPanel.add(orderType);
        actionPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        actionPanel.add(payTypeLabel);
        actionPanel.add(payType);
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
        tipField = new JTextField("0.0");
        tipField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                calculategrandtotal();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                calculategrandtotal();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                calculategrandtotal();
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

    private void updatefoodintable(String foodName, double price) {
        boolean found = false;
        for (int row = 0; row < tableModel.getRowCount(); row++) {
            if (tableModel.getValueAt(row, 0).equals(foodName)) {
                int quantity = (int) tableModel.getValueAt(row, 2);
                quantity++;
                tableModel.setValueAt(quantity, row, 2);
                tableModel.setValueAt(quantity * price, row, 3);
                found = true;
                break;
            }
        }
        if (!found) {
            tableModel.addRow(new Object[]{foodName, price, 1, price, "Remove", "Details"});
        }
        calculategrandtotal();
    }

    private void calculategrandtotal() {
        double total = 0.0;
        for (int row = 0; row < tableModel.getRowCount(); row++) {
            total += (double) tableModel.getValueAt(row, 3);
        }
        totalLabel.setText(String.valueOf(total));
        double tip = tipField.getText().isEmpty() ? 0.0 : Double.parseDouble(tipField.getText());
        grandTotalLabel.setText(String.valueOf(total + tip));
    }

    private void removeRow(int row) {
        tableModel.removeRow(row);
        calculategrandtotal();
    }

    private void showDetails(int row) {
        String name = (String) tableModel.getValueAt(row, 0);
        for (Meal meal : meals) {
            if (meal.name.equals(name)) {
                String details = meal.name + ": \n";

                for (src.models.Component component : meal.components) {
                    details += component.name + "\n";
                }
                JOptionPane.showMessageDialog(this, details , "Details", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }

    }

    private void completeSale() {
        try{
            List<OrderDetail> orderDetails = getdetalis();
            Order.OrderType currenttype= getCurrentordertype() ;
            Order.OrderPay currentpay= getCurrentpaytype();
            double dgrandTotal= Double.parseDouble(grandTotalLabel.getText());
            long lgrandTotal = (long) dgrandTotal;
            double dtip = Double.parseDouble(tipField.getText());
            long ltip = (long) dtip;
            Order myorder=new Order(Helper.myUser, Order.OrderStatus.PENDING,lgrandTotal,ltip,currenttype,currentpay,orderDetails);
            List<Order>myorders=Order.loadFromFile();
            myorders.add(myorder);
            Order.saveToFile(myorders);
            int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to confirm?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                if (Helper.myUser.notification == null) {
                    Helper.myUser.notification = new ArrayList<>();
                }
                Helper.myUser.notification.add(new UserNotification("Order "+myorder.getId()+" been submited",myorder));
                List<User> users = User.loadFromFile();
                for (User user : users) {
                    if (user.userName.equals(Helper.myUser.userName)) {
                        user.notification = Helper.myUser.notification;
                        User.saveToFile(users);
                    }

                }
                tableModel.setRowCount(0);
                totalLabel.setText("0.0");
                tipField.setText("");
                grandTotalLabel.setText("0.0");
                JOptionPane.showMessageDialog(this, "Sale completed!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }catch (IOException | NumberFormatException| ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Please set tip numbers only!!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private Order.OrderType getCurrentordertype(){
        if (orderType.getSelectedItem()=="In Resturant"){
            return Order.OrderType.ON_TABLE;
        }else if (orderType.getSelectedItem()== "Delivery"){
            return Order.OrderType.DELEVERE;
        }else{
            return Order.OrderType.PRIVATE_OR_OTHER;
        }
    }
    private Order.OrderPay getCurrentpaytype(){
        if (payType.getSelectedItem()=="Cash"){
            return Order.OrderPay.Cash;
        }else {
            return Order.OrderPay.Card;
        }
    }
    private List<OrderDetail> getdetalis(){
        List<OrderDetail> orderDetails = new ArrayList<>();
        for(Meal meal:meals){
            for (int i =0;i<tableModel.getRowCount();i++){
                if(tableModel.getValueAt(i,0).equals(meal.name)){
                    int temp =(int) tableModel.getValueAt(i,2);
                    orderDetails.add(new OrderDetail( temp,meal));
                }
            }
        }
        return orderDetails;
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText(column == 4 ? "Remove" : "Details");
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        private final JButton button;

        public ButtonEditor(ActionListener actionListener) {
            super(new JTextField());
            this.button = new JButton();
            this.button.addActionListener(e -> {
                fireEditingStopped();
                actionListener.actionPerformed(e);
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            button.setText(column == 4 ? "Remove" : "Details");
            return button;
        }
    }
}
