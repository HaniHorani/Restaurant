package src;
import src.models.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class userpanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton addButton, editButton, deleteButton;

    public userpanel() {
        this.setLayout(new BorderLayout());

        // إنشاء النموذج الخاص بالجدول
        String[] columnNames = {"User Name", "Password", "Type", "Created At"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            // تعطيل تحرير الخلايا مباشرة
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // جميع الخلايا غير قابلة للتعديل
            }
        };

        table = new JTable(tableModel);
        table.getTableHeader().setReorderingAllowed(false); // منع تحريك الأعمدة

        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane, BorderLayout.CENTER);

        // إنشاء لوحة الأزرار
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // زر الإضافة
        addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addRow();
            }
        });

        // زر التعديل
        editButton = new JButton("Edit");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editRow();
            }
        });

        // زر الحذف
        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteRow();
            }
        });

        // إضافة الأزرار إلى اللوحة
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        // إضافة لوحة الأزرار أسفل الجدول
        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    // دالة لإضافة صف جديد
    private void addRow() {
        try {
            List<User> users= User.loadFromFile();
            for(User user: users){
                String[] rowData = {user.userName, user.password, user.type.toString(), user.createdAt.toString()}; // بيانات فارغة للصف الجديد
                tableModel.addRow(rowData);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    // دالة لتعديل الصف المحدد
    private void editRow() {
        int selectedRow = table.getSelectedRow(); // الحصول على الصف المحدد
        if (selectedRow != -1) {
            // تعديل البيانات مباشرة داخل الجدول
            for (int col = 0; col < table.getColumnCount(); col++) {
                String newValue = JOptionPane.showInputDialog(
                        this,
                        "Enter value for Column " + (col + 1),
                        table.getValueAt(selectedRow, col)
                );
                if (newValue != null) {
                    table.setValueAt(newValue, selectedRow, col);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row to edit.", "Edit Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    // دالة لحذف الصف المحدد
    private void deleteRow() {
        int selectedRow = table.getSelectedRow(); // الحصول على الصف المحدد
        if (selectedRow != -1) {
            tableModel.removeRow(selectedRow); // حذف الصف من النموذج
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row to delete.", "Delete Error", JOptionPane.WARNING_MESSAGE);
        }
    }
}
