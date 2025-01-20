package src;

import src.Helper.Reports;
import src.models.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.*;
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
    private TreeMap<String,Integer> dailyordermap;
    private JTable table;
    private DefaultTableModel tableModel;

    public dailyorderpanel() {

        String[] columnNames = {"Day", "Count"};

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
        try {
            dailyordermap=Helper.Reports.countDailyOrdersReport();
        }catch (IOException | ClassNotFoundException e){
        }
        tableModel.setRowCount(0);
        for (int i=0;i<dailyordermap.size();i++) {
            tableModel.addRow(new Object[]{dailyordermap.keySet().toArray()[i],dailyordermap.get(dailyordermap.keySet().toArray()[i])});
        }
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
    private HashMap<String,Long> topmealmap;
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel label;
    private JTextField textField;

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
        label = new JLabel("The most requested meal:");
        textField = new JTextField(20);
        textField.setEditable(false);
        this.add(label,BorderLayout.NORTH);
        this.add(textField,BorderLayout.SOUTH);
    }

    private void update() {
        try {
            topmealmap = Helper.Reports.rankMealsByOrdersReport();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        List<Map.Entry<String, Long>> sortedEntries = new ArrayList<>(topmealmap.entrySet());
        sortedEntries.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));
        tableModel.setRowCount(0);
        for (Map.Entry<String, Long> entry : sortedEntries) {
            tableModel.addRow(new Object[]{entry.getKey(), entry.getValue()});
        }

        if(tableModel.getRowCount()>0){
            textField.setText(tableModel.getValueAt(0, 0).toString());}
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
    private TreeMap<String, Long> dailyfundsmap;
    private JTable table;
    private DefaultTableModel tableModel;

    public dailyfunds() {

        String[] columnNames = {"Day", "Total Price"};

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
         try {
            dailyfundsmap=Helper.Reports.dailyRevenueReport();
        }catch (IOException | ClassNotFoundException e){
        }
        tableModel.setRowCount(0);
        for (int i=0;i<dailyfundsmap.size();i++) {
            tableModel.addRow(new Object[]{dailyfundsmap.keySet().toArray()[i],dailyfundsmap.get(dailyfundsmap.keySet().toArray()[i])});
        }
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
    private HashMap<String,Integer> topcustomermap;
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel label;
    private JTextField textField;

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
        label = new JLabel("A regular customer of the restaurant:");
        textField = new JTextField(20);
        textField.setEditable(false);
        this.add(label,BorderLayout.NORTH);
        this.add(textField,BorderLayout.SOUTH);
    }

    private void update() {
        try {
            topcustomermap = Helper.Reports.rankCustomersByVisitsReport();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(topcustomermap.entrySet());
        sortedEntries.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        tableModel.setRowCount(0);

        for (Map.Entry<String, Integer> entry : sortedEntries) {
            tableModel.addRow(new Object[]{entry.getKey(), entry.getValue()});
        }
        if(tableModel.getRowCount()>0){
            textField.setText(tableModel.getValueAt(0, 0).toString());}

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
