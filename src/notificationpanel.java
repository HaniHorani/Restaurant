package src;

import src.models.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
        myrunnable runnable = new myrunnable();
        Thread thread = new Thread(runnable);
        thread.setDaemon(true);
        thread.start();
    }
    private void update(){
        List<UserNotification> notifications = Helper.myUser.notification;
        if (notifications == null) {
            notifications = new ArrayList<>();
        }
        tableModel.setRowCount(0);
        for (UserNotification notification : notifications) {
            tableModel.addRow(new Object[]{notification.message, notification.createdAt});
        }
    }
    private class myrunnable implements Runnable{
        @Override
        public void run() {
            JFrame temp = (JFrame) SwingUtilities.getWindowAncestor(notificationpanel.this);
            while (true) {
                update();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
