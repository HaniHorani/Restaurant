package src;

import src.models.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class notificationpanel extends JPanel {
    List<UserNotification> notificationList = Helper.myUser.notification;
    private JTable table;
    private DefaultTableModel tableModel;
    public notificationpanel() {
        // إعداد التخطيط
        this.setLayout(new BorderLayout());

        // أسماء الأعمدة
        String[] columnNames = {"ID", "messege", "Time"};

        // إنشاء النموذج الخاص بالجدول
        tableModel = new DefaultTableModel(columnNames, 0); // يبدأ بدون صفوف
        table = new JTable(tableModel);
        table.getTableHeader().setReorderingAllowed(false);

        // إضافة الجدول إلى ScrollPane
        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane, BorderLayout.CENTER);

        try {
            String msg;
//            if( ==Order.OrderType.DELEVERE){
//                msg = "Dilver";
//            }
//            else if( ==Order.OrderType.ON_TABLE){
//                msg = "On table";
//            }
//            else if(==Order.OrderType.PRIVATE_OR_OTHER){
//                msg = "private or other";
//            }
//           Helper.myUser.notification.add(new UserNotification(msg,));
            List<User> users = User.loadFromFile();
            for (User user : users) {
                if (user.userName == Helper.myUser.userName) {
                    user.notification = Helper.myUser.notification;
                }
            }
            User.saveToFile(users);
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Error loading Notifications: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }



    }
}