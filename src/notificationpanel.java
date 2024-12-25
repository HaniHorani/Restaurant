package src;

import src.models.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class notificationpanel extends JPanel {
    List<UserNotification> notificationList = Helper.myUser.notification;
    private JTable table;
    private DefaultTableModel tableModel;
    public notificationpanel() {
        this.setLayout(new BorderLayout());

        String[] columnNames = {"messege", "Time"};

        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        table.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane, BorderLayout.CENTER);

//        try {
            List<UserNotification> notifications = Helper.myUser.notification;
if (notifications == null) {
    notifications = new ArrayList<>();
}
for (UserNotification notification : notifications) {
    tableModel.addRow(new Object[]{notification.message, notification.createdAt});
}
//        } catch (IOException | ClassNotFoundException e) {
//            JOptionPane.showMessageDialog(this, "Error loading Notifications: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//        }



    }
}