package src;

import src.models.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class reprotpanel extends JPanel {
    private JPanel center;
    private JPanel DailyOrder;
    private JPanel TopMeal;
    private JPanel DailyFunds;
    private JPanel TopCustomer;
    private JButton DailyOrderButton;
    private JButton TopMealButton;
    private JButton DailyFundsButton;
    private JButton TopCustomerButton;

    reprotpanel(){
        this.setLayout(new BorderLayout());
        center=new JPanel();
        DailyOrder=new dailyorderpanel();
        TopMeal=new topmealpanel();
        DailyFunds = new dailyfunds();
        TopCustomer = new topcustomer();

        DailyOrderButton = new JButton("Daily Order");
        DailyOrderButton.setFocusable(false);
        DailyOrderButton.setForeground(Color.RED);
        DailyOrderButton.setBackground(Color.orange);
        DailyOrderButton.setOpaque(true);

        TopMealButton = new JButton("Top Meal");
        TopMealButton.setFocusable(false);
        TopMealButton.setForeground(Color.RED);
        TopMealButton.setBackground(Color.orange);
        TopMealButton.setOpaque(true);

        DailyFundsButton = new JButton("Daily Funds");
        DailyFundsButton.setFocusable(false);
        DailyFundsButton.setForeground(Color.RED);
        DailyFundsButton.setBackground(Color.orange);
        DailyFundsButton.setOpaque(true);

        TopCustomerButton = new JButton("Top\n User");
        TopCustomerButton.setFocusable(false);
        TopCustomerButton.setForeground(Color.RED);
        TopCustomerButton.setBackground(Color.orange);
        TopCustomerButton.setOpaque(true);

        DailyOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                center.removeAll();
                reprotpanel.this.repaint();
                center.add(DailyOrder);
                reprotpanel.this.revalidate();
            }
        });
        TopMealButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                center.removeAll();
                reprotpanel.this.repaint();
                center.add(TopMeal);
                reprotpanel.this.revalidate();
            }
        });
        DailyFundsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                center.removeAll();
                reprotpanel.this.repaint();
                center.add(DailyFunds);
                reprotpanel.this.revalidate();
            }
        });
        TopCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                center.removeAll();
                reprotpanel.this.repaint();
                center.add(TopCustomer);
                reprotpanel.this.revalidate();
            }
        });
        JPanel buttonpanel = new JPanel();
        buttonpanel.setLayout(new GridLayout(4,1,0,0));
        buttonpanel.setPreferredSize(new Dimension(100,50));

        buttonpanel.add(DailyOrderButton);
        buttonpanel.add(TopMealButton);
        buttonpanel.add(DailyFundsButton);
        buttonpanel.add(TopCustomerButton);
        buttonpanel.setOpaque(true);
        buttonpanel.setBackground(Color.orange);
        this.add(buttonpanel,BorderLayout.EAST);
        this.add(center,BorderLayout.CENTER);
    }

}
class dailyorderpanel extends JPanel {
    //    List<n> n = ;
    private JTable table;
    private DefaultTableModel tableModel;

    public dailyorderpanel() {

        String[] columnNames = {"Order", "Count"};

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
        myrunnable runnable = new myrunnable();
        Thread thread = new Thread(runnable);
        thread.setDaemon(true);
        thread.start();
    }

    private void update() {
//        List<> l = ;
//        if (l == null) {
//            l = new <> ();
//        }
//        tableModel.setRowCount(0);
//        for (UserNotification notification : notifications) {
//            tableModel.addRow(new Object[]{notification.message, notification.createdAt});
//        }
    }

    private class myrunnable implements Runnable {
        @Override
        public void run() {
            while (!Helper.myUser.equals(null)) {
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
class topmealpanel extends JPanel {
    //    List<n> n = ;
    private JTable table;
    private DefaultTableModel tableModel;

    public topmealpanel() {

        String[] columnNames = {"Meal", "Rank"};

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
        myrunnable runnable = new myrunnable();
        Thread thread = new Thread(runnable);
        thread.setDaemon(true);
        thread.start();
    }

    private void update() {
//        List<> l = ;
//        if (l == null) {
//            l = new <> ();
//        }
//        tableModel.setRowCount(0);
//        for (UserNotification notification : notifications) {
//            tableModel.addRow(new Object[]{notification.message, notification.createdAt});
//        }
    }

    private class myrunnable implements Runnable {
        @Override
        public void run() {
            while (!Helper.myUser.equals(null)) {
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
class dailyfunds extends JPanel {
    //    List<n> n = ;
    private JTable table;
    private DefaultTableModel tableModel;

    public dailyfunds() {

        String[] columnNames = {"Day", "Fund"};

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
        myrunnable runnable = new myrunnable();
        Thread thread = new Thread(runnable);
        thread.setDaemon(true);
        thread.start();
    }

    private void update() {
//        List<> l = ;
//        if (l == null) {
//            l = new <> ();
//        }
//        tableModel.setRowCount(0);
//        for (UserNotification notification : notifications) {
//            tableModel.addRow(new Object[]{notification.message, notification.createdAt});
//        }
    }

    private class myrunnable implements Runnable {
        @Override
        public void run() {
            while (!Helper.myUser.equals(null)) {
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
class topcustomer extends JPanel {
    //    List<n> n = ;
    private JTable table;
    private DefaultTableModel tableModel;

    public topcustomer() {

        String[] columnNames = {"Name", "Rank"};

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
        myrunnable runnable = new myrunnable();
        Thread thread = new Thread(runnable);
        thread.setDaemon(true);
        thread.start();
    }

    private void update() {
//        List<> l = ;
//        if (l == null) {
//            l = new <> ();
//        }
//        tableModel.setRowCount(0);
//        for (UserNotification notification : notifications) {
//            tableModel.addRow(new Object[]{notification.message, notification.createdAt});
//        }
    }

    private class myrunnable implements Runnable {
        @Override
        public void run() {
            while (!Helper.myUser.equals(null)) {
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
//    List<n> n = ;
//    private JTable table;
//    private DefaultTableModel tableModel;
//    public reprotpanel() {
//
//        String[] columnNames = {" ", " "};
//
//        tableModel = new DefaultTableModel(columnNames, 0) {
//            @Override
//            public boolean isCellEditable(int row, int column) {
//                return false;
//            }
//        };
//        table = new JTable(tableModel);
//        table.getTableHeader().setReorderingAllowed(false);
//        JScrollPane scrollPane = new JScrollPane(table);
//        this.add(scrollPane, BorderLayout.CENTER);
//        myrunnable runnable = new myrunnable();
//        Thread thread = new Thread(runnable);
//        thread.setDaemon(true);
//        thread.start();
//    }
//    private void update(){
//        List<>  l= ;
//        if (l == null) {
//            l = new <>();
//        }
//        tableModel.setRowCount(0);
//        for (UserNotification notification : notifications) {
//            tableModel.addRow(new Object[]{notification.message, notification.createdAt});
//        }
//    }
//private class myrunnable implements Runnable{
//    @Override
//    public void run() {
//        while (true) {
//            update();
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
//}
