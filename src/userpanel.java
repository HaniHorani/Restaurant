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

        String[] columnNames = {"User Name", "Password", "Type", "Created At"};
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

        addButton = new JButton("Add");
        addButton.addActionListener(e -> addUser());

        editButton = new JButton("Edit");
        editButton.addActionListener(e -> editUser());

        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showOptionDialog(null, "Are you sure you want to delete?", "Delete", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (option == 0) {
                   deleteUser();
                }
            }
        });

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        this.add(buttonPanel, BorderLayout.SOUTH);

        updateTable();
    }

    private void updateTable() {
        tableModel.setRowCount(0);
        try {
            List<User> users = User.loadFromFile();
            for (User user : users) {
                tableModel.addRow(new Object[]{
                        user.userName, user.password, user.type, user.createdAt
                });
            }
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Error loading users: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addUser() {
        String userName = JOptionPane.showInputDialog(this, "Enter User Name:");
        if (userName == null || userName.trim().isEmpty()) return;

        String password = JOptionPane.showInputDialog(this, "Enter Password:");
        if (password == null || password.trim().isEmpty()) return;

        String[] types = {"ADMIN", "CUSTOMER","EMPLOYEE"};
        String type = (String) JOptionPane.showInputDialog(
                this, "Select User Type:", "User Type",
                JOptionPane.QUESTION_MESSAGE, null, types, types[0]
        );
        if (type == null) return;

        try {
            User newUser = new User(userName, password, User.UserType.valueOf(type));
            if (User.check(newUser)) {
                List<User> users = User.loadFromFile();
                users.add(newUser);
                User.saveToFile(users);
                updateTable();
            } else {
                JOptionPane.showMessageDialog(this, "User Name already exists!", "Add Error", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding user: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editUser() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to edit.", "Edit Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(tableModel.getValueAt(selectedRow,2)!= User.UserType.ADMIN){
        String selectedUserName = (String) tableModel.getValueAt(selectedRow, 0);
        try {
            List<User> users = User.loadFromFile();
            for (User user : users) {
                if (user.userName.equals(selectedUserName)) {
                    String newUserName = JOptionPane.showInputDialog(this, "Enter New User Name:", user.userName);
                    if (newUserName != null && !newUserName.trim().isEmpty()) user.userName = newUserName;

                    String newPassword = JOptionPane.showInputDialog(this, "Enter New Password:", user.password);
                    if (newPassword != null && !newPassword.trim().isEmpty()) user.password = newPassword;

                    String[] types = {"ADMIN", "CUSTOMER","EMPLOYEE"};
                    String newType = (String) JOptionPane.showInputDialog(
                            this, "Select New User Type:", "User Type",
                            JOptionPane.QUESTION_MESSAGE, null, types, user.type.name()
                    );
                    if (newType != null) user.type = User.UserType.valueOf(newType);

                    User.saveToFile(users);
                    updateTable();
                    return;
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error editing user: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
      }
        else
            JOptionPane.showMessageDialog(this, "This is Admin User ", "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void deleteUser() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to delete.", "Delete Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(tableModel.getValueAt(selectedRow,2)!= User.UserType.ADMIN){
        String selectedUserName = (String) tableModel.getValueAt(selectedRow, 0);
        try {
            List<User> users = User.loadFromFile();
            users.removeIf(user -> user.userName.equals(selectedUserName));
            User.saveToFile(users);
            updateTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error deleting user: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
      }
        else
            JOptionPane.showMessageDialog(this, "This is Admin User ", "Error", JOptionPane.ERROR_MESSAGE);
    }
}