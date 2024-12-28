package src;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;
import src.models.Order;
import src.models.User;
// import src.models.UserNotification;

public class Login extends JFrame implements ActionListener {

    Font cocon = new Font("Cocon", Font.PLAIN, 18);
    JTextField userTextField;
    JPasswordField passField;
    String loginame, loginpassword;
    JButton loginButton;
    JButton SginButton;
    JPanel mainPanel;

    public Login() {
        this.setTitle("Login");
        this.setIconImage(new ImageIcon("images\\Resturant-main.png").getImage());
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(600, 700);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        this.setLocation((screenWidth - this.getWidth()) / 2, (screenHeight - this.getHeight()) / 2);

        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Resizer imagePanel = new Resizer("images\\Tablet login-amico.png");
        imagePanel.setBackground(Color.white);
        imagePanel.setPreferredSize(new Dimension(500, 300));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(imagePanel, gbc);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(cocon);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(userLabel, gbc);

        userTextField = new JTextField();
        userTextField.setFont(cocon);
        userTextField.setPreferredSize(new Dimension(300, 40));
        userTextField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.BLACK, 1),
                new EmptyBorder(10, 10, 10, 10)
        ));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(userTextField, gbc);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(cocon);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(passLabel, gbc);

        passField = new JPasswordField();
        passField.setFont(cocon);
        passField.setPreferredSize(new Dimension(300, 40));
        passField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.BLACK, 1),
                new EmptyBorder(10, 10, 10, 10)
        ));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(passField, gbc);

        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        loginButton.setFont(cocon);
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(new Color(64, 123, 255));
        loginButton.setPreferredSize(new Dimension(150, 40));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginButton.setFocusable(false);
        mainPanel.add(loginButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        SginButton = new JButton("sign up");
        SginButton.setFont(cocon);
        SginButton.setForeground(new Color(64, 123, 255));
        SginButton.setBackground(Color.WHITE);
        SginButton.setBorder(null);
        SginButton.setFocusable(false);
        SginButton.addActionListener(this);

        mainPanel.add(SginButton, gbc);

        mainPanel.setBackground(Color.white);

        this.add(mainPanel);

        this.pack();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == SginButton) {
            this.remove(mainPanel);
            mainPanel = new Register();
            this.repaint();
            this.add(mainPanel);
            this.revalidate();
            this.setTitle("Sgin up");

        } else if (e.getSource() == loginButton) {
            loginame = userTextField.getText();
            loginpassword = new String(passField.getPassword());
            if (loginame.isEmpty()|| loginpassword.isEmpty()) {
                loginame="bahaa";
                loginpassword="123";
//                JOptionPane.showMessageDialog(null, "The user name or password is empty", "Falied", JOptionPane.WARNING_MESSAGE);
//                return;
            }
            try {
                List<User> currentUsres;
                currentUsres = User.loadFromFile();
                for (User u : currentUsres) {
                    if (u.userName.equals(loginame) && u.password.equals(loginpassword)) {
                        System.out.println("==================================");
                        Helper.myUser = u;
                        this.dispose();
                        new Home();
                        break;
                    }
                }
                if (Helper.myUser.userName == null ) {
                    JOptionPane.showMessageDialog(null, "User not found", "Falied", JOptionPane.WARNING_MESSAGE);
                }
            } catch (IOException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }

        }
    }
}

class Resizer extends JPanel {

    private Image image;

    public Resizer(String path) {
        image = new ImageIcon(path).getImage();
        if (image == null) {
            System.out.println("Failed to load image.");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            int panelWidth = getWidth();
            int panelHeight = getHeight();

            int imgWidth = image.getWidth(null);
            int imgHeight = image.getHeight(null);

            double scaleX = (double) panelWidth / imgWidth;
            double scaleY = (double) panelHeight / imgHeight;
            double scale = Math.min(scaleX, scaleY);

            int newWidth = (int) (imgWidth * scale);
            int newHeight = (int) (imgHeight * scale);

            int x = (panelWidth - newWidth) / 2;
            int y = (panelHeight - newHeight) / 2;

            g.drawImage(image, x, y, newWidth, newHeight, this);
        }
    }
}

class Register extends JPanel implements ActionListener {

    Font cocon = new Font("Cocon", Font.PLAIN, 18);
    JButton acceptButton;
    String newusername;
    String newpassword;
    String confirmpassword;
    JTextField newuserTextField;
    JTextField newpassField;
    JTextField confirmPassField;

    public Register() {
        this.setLayout(new GridBagLayout());
        this.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Resizer imagePanel = new Resizer("images\\Sign up-amico.png");
        imagePanel.setBackground(Color.white);
        imagePanel.setPreferredSize(new Dimension(500, 300));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(imagePanel, gbc);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(cocon);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        this.add(userLabel, gbc);

        newuserTextField = new JTextField();
        newuserTextField.setFont(cocon);
        newuserTextField.setPreferredSize(new Dimension(300, 40));
        newuserTextField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.BLACK, 1),
                new EmptyBorder(10, 10, 10, 10)
        ));

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        this.add(newuserTextField, gbc);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(cocon);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        this.add(passLabel, gbc);

        newpassField = new JPasswordField();
        newpassField.setFont(cocon);
        newpassField.setPreferredSize(new Dimension(300, 40));
        newpassField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.BLACK, 1),
                new EmptyBorder(10, 10, 10, 10)
        ));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        this.add(newpassField, gbc);

        JLabel confirmPassLabel = new JLabel("Confirm Password:");
        confirmPassLabel.setFont(cocon);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        this.add(confirmPassLabel, gbc);

        confirmPassField = new JPasswordField();
        confirmPassField.setFont(cocon);
        confirmPassField.setPreferredSize(new Dimension(300, 40));
        confirmPassField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.BLACK, 1),
                new EmptyBorder(10, 10, 10, 10)
        ));
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        this.add(confirmPassField, gbc);

        acceptButton = new JButton("Save");
        acceptButton.setFont(cocon);
        acceptButton.setForeground(Color.WHITE);
        acceptButton.setBackground(new Color(64, 123, 255));
        acceptButton.setPreferredSize(new Dimension(150, 40));
        acceptButton.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        acceptButton.setFocusable(false);
        this.add(acceptButton, gbc);

        this.setBackground(Color.white);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == acceptButton) {
            newusername = newuserTextField.getText();
            newpassword = newpassField.getText();
            confirmpassword = confirmPassField.getText();
            if (newusername.isEmpty() || newpassword.isEmpty() || confirmpassword.isEmpty()) {
                JOptionPane.showMessageDialog(null, "The user name or password is empty", "Falied", JOptionPane.WARNING_MESSAGE);
                return;
            }
            // while (true) {
            if (!(newpassword.equals(confirmpassword))) {
                JOptionPane.showMessageDialog(null, "Password mismatch", "Falied", JOptionPane.WARNING_MESSAGE);
                return;
            }
            User newuser = new User();
            newuser.userName = newusername;
            newuser.password = newpassword;
            newuser.type = User.UserType.CUSTOMER;

            try {
                List<User> currentusers = User.loadFromFile();
                if (User.check(newuser)) {
                    currentusers.add(newuser);
                } else {
                    JOptionPane.showMessageDialog(null, "The User is already exsit", "Falied",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                User.saveToFile(currentusers);

                JFrame temp = (JFrame) SwingUtilities.getWindowAncestor(this);
                temp.dispose();
                Helper.myUser = newuser;
                new Home();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }

        }
    }
}
