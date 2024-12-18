package src;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import src.models.User;

public class Home extends JFrame implements ActionListener {

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
    private JPanel orederspanel;
    private JPanel userspanel;

    public Home() {
        usersButton.setVisible(false);
        if (Helper.myUser.type == User.UserType.ADMIN) {
            usersButton.setVisible(true);
        }
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        homeButton.addActionListener(this);
        logoutButton.addActionListener(this);
        orderButton.addActionListener(this);
        usersButton.addActionListener(this);

        orederspanel = new JPanel();
        userspanel = new userpanel();

        orederspanel.setBackground(Color.RED);
        orederspanel.setOpaque(true);

        Home.setOpaque(true);
        this.add(panel1);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JPanel current = center;
        if (e.getSource() == orderButton) {
            center.removeAll();
            panel1.repaint();
            toplabel.setText("Oreders");
            center.add(orederspanel);
            panel1.revalidate();

        } else if (e.getSource() == homeButton) {
            center.removeAll();
            panel1.repaint();
            toplabel.setText("Home");
            center.add(mainsecreen);
            panel1.revalidate();
        } else if (e.getSource() == usersButton) {
            center.removeAll();
            panel1.repaint();
            toplabel.setText("Users");
            center.add(userspanel);
            panel1.revalidate();
        } else if (e.getSource() == logoutButton) {
            int option = JOptionPane.showOptionDialog(null, "Are you sure you want to logout?", "logout", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
            if (option == 0) {
                Helper.myUser = null;
                new Login();
                this.dispose();
            }
        }

    }

}
