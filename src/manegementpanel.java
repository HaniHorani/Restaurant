package src;

import src.models.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class manegementpanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton acceptButton,detailButton ,cancelButton;

    public manegementpanel() {
        this.setLayout(new BorderLayout());
        String[] columnNames = {"Order ID", "Customer Name", "Status","Created At","Completed At","Tips","Total Price"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        acceptButton = new JButton("Accept Order");
        acceptButton.addActionListener(e -> acceptorder());

        detailButton = new JButton("Show Detalis");
        detailButton.addActionListener(e -> details());

        cancelButton = new JButton("Cancel Order");
        cancelButton.addActionListener(e -> cancelorder());
        if(Helper.myUser.type == User.UserType.CUSTOMER){
            acceptButton.setVisible(false);
            cancelButton.setVisible(false);
        }

        buttonPanel.add(acceptButton);
        buttonPanel.add(detailButton);
        buttonPanel.add(cancelButton);

        this.add(buttonPanel, BorderLayout.SOUTH);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Helper.myUser.equals(null)) {
                    updateTable();
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                    }
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
    private void updateTable() {
        try{
            tableModel.setRowCount(0);
            if(Helper.myUser.type == User.UserType.CUSTOMER){
            List<Order> orders = Order.loadFromFile();
            for (Order order : orders) {
                if (Helper.myUser.userName.equals(order.getUser().userName)) {
                    tableModel.addRow(new Object[]{order.getId(), order.getUser().userName,order.getOrderStatus(),order.getCreatedAt(),order.getCompletedAt(),order.getTips(),order.getTotalPrice()});
                }
            }
            }
            else {
                List<Order> orders = Order.loadFromFile();
                for (Order order : orders) {
                        tableModel.addRow(new Object[]{order.getId(), order.getUser().userName,order.getOrderStatus(),order.getCreatedAt(),order.getCompletedAt(),order.getTips(),order.getTotalPrice()});
                }
            }
        }catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getClass().getName() + " - " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }
    private void details() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an order to show detalis.", "Show Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        long selectedOrderId = (long) tableModel.getValueAt(selectedRow, 0);
        try {
            List<Order> orders = Order.loadFromFile();
            for (Order order : orders) {
                if (order.getId() ==selectedOrderId) {
                    List<OrderDetail> orderDetails = order.getOrderDetails();
                    String msg = "";
                    for (OrderDetail orderDetail : orderDetails) {
                        msg += (orderDetail.meal.name + " " + orderDetail.count + "\n");
                    }
                    JOptionPane.showMessageDialog(this, msg, "Details", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error showing order: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancelorder() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an order to cancel.", "Cancel Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int response =JOptionPane.showConfirmDialog(this, "Are you sure you want to cancel this order?", "Cancel", JOptionPane.ERROR_MESSAGE);
        if (response == JOptionPane.YES_OPTION) {
            long selectedOrderId = (long) tableModel.getValueAt(selectedRow, 0);
            try {
                List<Order> orders = Order.loadFromFile();
                for (Order order : orders) {
                    if (order.getId() == selectedOrderId && order.getOrderStatus().equals(Order.OrderStatus.PENDING)) {
                        order.setOrderStatus(Order.OrderStatus.CANCELLED);
                        Order.saveToFile(orders);
                        updateTable();
                        if (Helper.myUser.notification == null) {
                            Helper.myUser.notification = new ArrayList<>();
                        }
                        Helper.myUser.notification.add(new UserNotification("Order "+order.getId()+" been canceled",order));
                        List<User> users = User.loadFromFile();
                        for (User user : users) {
                            if (user.userName.equals(Helper.myUser.userName)) {
                                user.notification = Helper.myUser.notification;
                            }

                        }
                        User customerUser = order.getUser();
                        for (User user : users) {
                            if (user.userName.equals(customerUser.userName)) {
                                if (user.notification == null) {
                                    user.notification = new ArrayList<>();
                                }
                                user.notification.add(new UserNotification("Order " + order.getId() + " has been canceled", order));
                            }
                        }
                        User.saveToFile(users);
                        return;
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error canceling order: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private void acceptorder() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an order to accept.", "Accept Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int response =JOptionPane.showConfirmDialog(this, "Are you sure you want to accept this order?", "Accept", JOptionPane.ERROR_MESSAGE);
        if (response == JOptionPane.YES_OPTION) {
            long selectedOrderId = (long) tableModel.getValueAt(selectedRow, 0);
            try {
                List<Order> orders = Order.loadFromFile();
                for (Order order : orders) {
                    if (order.getId() == selectedOrderId && order.getOrderStatus().equals(Order.OrderStatus.PENDING)) {
                        order.setOrderStatus(Order.OrderStatus.COMPLETED);
                        order.setCompletedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
                        if (Helper.myUser.notification == null) {
                            Helper.myUser.notification = new ArrayList<>();
                        }
                        Helper.myUser.notification.add(new UserNotification("Order "+order.getId()+" been accepted",order));
                        List<User> users = User.loadFromFile();
                        for (User user : users) {
                            if (user.userName.equals(Helper.myUser.userName)) {
                                user.notification = Helper.myUser.notification;
                            }
                        }
                        User customerUser = order.getUser();
                        for (User user : users) {
                            if (user.userName.equals(customerUser.userName)) {
                                if (user.notification == null) {
                                    user.notification = new ArrayList<>();
                                }
                                user.notification.add(new UserNotification("Order " + order.getId() + " has been completed", order));
                            }
                        }
                        User.saveToFile(users);
                        Order.saveToFile(orders);
                        updateTable();
                        return;
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error accepting order: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}