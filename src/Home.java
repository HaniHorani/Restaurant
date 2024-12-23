package src;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import src.models.User;

public class Home extends JFrame  {

    private JPanel panel1;
    private JPanel Home;
    private JPanel left;
    private JButton homeButton;
    private JButton logoutButton;
    private JButton usersButton;
    private JButton orderButton;
    private JPanel center;
    private JLabel mainsecreen;
    private JLabel toplabel;
    private JButton notificationbutton;
    private JButton mealsButton;
    private JPanel mealspanel;
    private JPanel orederspanel;
    private JPanel userspanel;
    private JPanel notificationpanel;

    public Home() {
        this.setTitle("Resturant");
        this.setIconImage(new ImageIcon("images\\Resturant-main.png").getImage());
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        orederspanel = new JPanel();

        usersButton.setVisible(false);
        if (Helper.myUser.type == User.UserType.ADMIN) {
            usersButton.setVisible(true);
        }
        if(Helper.myUser.type == User.UserType.CUSTOMER){
            mealsButton.setVisible(false);
            orderButton.setText("Add Order");
            orederspanel = new cashirpanel();
        }
        else{
            mealspanel=new JPanel();
            orederspanel= new manegementpanel();
        }
        userspanel = new userpanel();
        notificationpanel = new notificationpanel();

        orederspanel.setBackground(Color.RED);
        orederspanel.setOpaque(true);

        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                center.removeAll();
                panel1.repaint();
                toplabel.setText("Home");
                center.add(mainsecreen);
                panel1.revalidate();
            }
        });
        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                center.removeAll();
                panel1.repaint();
                toplabel.setText("Oreders");
                center.add(orederspanel);
                panel1.revalidate();
            }
        });


        usersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                center.removeAll();
                panel1.repaint();
                toplabel.setText("Users");
                center.add(userspanel);
                panel1.revalidate();
            }
        });
        notificationbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                center.removeAll();
                panel1.repaint();
                toplabel.setText("Notification");
                center.add(notificationpanel);
                panel1.revalidate();
            }
        });
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });


        Home.setOpaque(true);
        this.add(panel1);
        this.setVisible(true);
    }
   public void logout(){
        int option = JOptionPane.showOptionDialog(null, "Are you sure you want to logout?", "logout", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        if (option == 0) {
            Helper.myUser.userName = null;
            Helper.myUser.password = null;
            new Login();
            this.dispose();
        }
    }

}
