package src;

import src.models.Order;

import javax.swing.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class manegementpanel extends JPanel {
        private JTable table;
        private DefaultTableModel tableModel;
        private JButton editButton, deleteButton;

        public manegementpanel() {
            this.setLayout(new BorderLayout());

            // أسماء الأعمدة
            String[] columnNames = {"Order ID", "Customer Name", "Status"};
            tableModel = new DefaultTableModel(columnNames, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            // إعداد الجدول
            table = new JTable(tableModel);
            table.getTableHeader().setReorderingAllowed(false);

            JScrollPane scrollPane = new JScrollPane(table);
            this.add(scrollPane, BorderLayout.CENTER);

            // لوحة الأزرار
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

            editButton = new JButton("Edit Order");
            editButton.addActionListener(e -> editOrder());

            deleteButton = new JButton("Delete Order");
            deleteButton.addActionListener(e -> cancelorder());

            buttonPanel.add(editButton);
            buttonPanel.add(deleteButton);

            this.add(buttonPanel, BorderLayout.SOUTH);

            // تحديث الجدول
            updateTable();
        }

        // تحديث الجدول بالطلبات
        private void updateTable() {
            try{
                tableModel.setRowCount(0);
                List<Order> orders = Order.loadFromFile();
                for (Order order : orders) {
                       tableModel.addRow(new Object[]{order.getId(), order.getUser().userName,order.getOrderStatus()});
                }
            }catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getClass().getName() + " - " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

        }


        // تعديل طلب
        private void editOrder() {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select an order to edit.", "Edit Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            long selectedOrderId = (long) tableModel.getValueAt(selectedRow, 0);
            try {
                List<Order> orders = Order.loadFromFile();
                for (Order order : orders) {
                    if (order.getId() ==selectedOrderId) {
                        Order.saveToFile(orders);
                        updateTable();
                        return;
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error editing order: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void cancelorder() {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select an order to delete.", "Delete Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            long selectedOrderId = (long) tableModel.getValueAt(selectedRow, 0);
            try {
                List<Order> orders = Order.loadFromFile();
                for(Order order : orders)
                if(order.getId() ==selectedOrderId){
                    order.setOrderStatus(Order.OrderStatus.CANCELLED);
                }
                Order.saveToFile(orders);
                updateTable();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error deleting order: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
