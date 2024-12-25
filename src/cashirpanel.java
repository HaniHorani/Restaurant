package src;
import src.models.Meal;
import src.models.Order;
import src.models.OrderDetail;
import src.models.UserNotification;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
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
    private List<Meal> meals;

    public cashirpanel() {
            try {
                meals=Meal.loadFromFile();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
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

        JPanel foodPanel =new  JPanel();    //new JPanel(new GridLayout(meals.size(), 1, 10, 10));
        foodPanel.setLayout(new BoxLayout(foodPanel, BoxLayout.Y_AXIS));
        foodPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                for(Meal meal:meals){
                    addfoodbutton(foodPanel,meal.name,meal.Price);
                }
        JScrollPane scrollPane = new JScrollPane(foodPanel);
        rightPanel.add(scrollPane, BorderLayout.CENTER);
//        addfoodbutton(foodPanel, "Burger", (long) 5.00);
//        addfoodbutton(foodPanel, "Pizza", (long)8.00);
//        addfoodbutton(foodPanel, "Pasta", (long)7.00);
//        addfoodbutton(foodPanel, "Salad", (long)4.00);
//        addfoodbutton(foodPanel, "Drink", (long)2.00);
        //rightPanel.add(foodPanel, BorderLayout.NORTH);

        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.Y_AXIS));
        actionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel orderTypePanel = new JPanel();
        orderTypePanel.setLayout(new BoxLayout(orderTypePanel, BoxLayout.Y_AXIS));
        JLabel orderTypeLabel = new JLabel("Order Type");
        orderTypeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        orderType = new JComboBox<>(new String[]{"In Resturant", "Delivery", "Else"});
        orderType.setMaximumSize(new Dimension(200, 30));

        orderTypePanel.add(orderTypeLabel);
        orderTypePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        orderTypePanel.add(orderType);

        JButton saleButton = new JButton("SALE");
        saleButton.addActionListener(e -> {try{

            List<OrderDetail> orderDetails = getdetalis();
            Order.OrderType currenttype= getCurrentordertype() ;
            orderDetails.add(new OrderDetail(1,new Meal()));
            double dgrandTotal= Double.parseDouble(grandTotalLabel.getText());
            long lgrandTotal = (long) dgrandTotal;
            double dtip = Double.parseDouble(tipField.getText());
            long ltip = (long) dtip;
            Order myorder=new Order(Helper.myUser, Order.OrderStatus.PENDING,lgrandTotal,ltip,currenttype,orderDetails);
            List<Order>myorders=Order.loadFromFile();
            myorders.add(myorder);
            Order.saveToFile(myorders);
            int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to confirm?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                Helper.myUser.notification = new ArrayList<>();
                Helper.myUser.notification.add(new UserNotification("oreder been submited",myorder));
                tableModel.setRowCount(0);
                totalLabel.setText("0.0");
                tipField.setText("");
                grandTotalLabel.setText("0.0");
                JOptionPane.showMessageDialog(this, "Sale completed!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }catch (IOException | NumberFormatException| ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Error Submitting Order: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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

    private void addfoodbutton(JPanel panel, String foodName, long price) {
        JButton foodButton = new JButton(foodName + " ($" +  price + ")");
        foodButton.setFocusable(false);
        foodButton.addActionListener(e -> updatefoodintable(foodName, price));
        panel.add(foodButton);
    }
    private void updatefoodintable(String foodName, double price) {
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

        updatetotal();
    }

    private void updatetotal() {
        double total = 0.0;
        for (int row = 0; row < tableModel.getRowCount(); row++) {
            total += (double) tableModel.getValueAt(row, 3);
        }
        totalLabel.setText(String.format("%.2f", total));
        calculategrandtotal();
    }

    private void calculategrandtotal() {
        try {
            double total = Double.parseDouble(totalLabel.getText());
            double tip = tipField.getText().isEmpty() ? 0.0 : Double.parseDouble(tipField.getText());
            double grandTotal = total + tip;
            grandTotalLabel.setText(String.format("%.2f", grandTotal));
        } catch (NumberFormatException e) {
            grandTotalLabel.setText("Error");
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
private List<OrderDetail> getdetalis(){
    List<OrderDetail> orderDetails = new ArrayList<>();
    for (int i=0,j =0;i<table.getRowCount()&&j<table.getColumnCount();i++,j++){
       // OrderDetail neworderdetals =new OrderDetail();

    }

    return orderDetails;
}
}