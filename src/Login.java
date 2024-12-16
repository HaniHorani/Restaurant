package src;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class Login extends JFrame {
    Font font = new Font("Cocon", Font.PLAIN, 18); // زيادة حجم الخط

    public Login() {
        this.setTitle("Login");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(600, 700); // تكبير حجم النافذة

        // Center frame on screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        this.setLocation((screenWidth - this.getWidth()) / 2, (screenHeight - this.getHeight()) / 2);

        // Main panel with GridBagLayout to center everything
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20)); // Add padding around the main panel

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15); // زيادة padding بين المكونات
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Add image at the top (تكبير الصورة)
        // Add image at the top (تصغير الصورة قليلاً)
        Resizer imagePanel = new Resizer("images\\Tablet login-amico.png");
        imagePanel.setBackground(Color.white);
        imagePanel.setPreferredSize(new Dimension(500, 300)); // تصغير الأبعاد
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Span across both columns
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(imagePanel, gbc);


        // Username label and field
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(userLabel, gbc);

        JTextField userTextField = new JTextField();
        userTextField.setFont(font);
        userTextField.setPreferredSize(new Dimension(300, 40)); // تكبير حجم الحقل
        userTextField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.BLACK, 1),
                new EmptyBorder(10, 10, 10, 10) // زيادة الحشوة داخل الحقل
        ));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(userTextField, gbc);

        // Password label and field
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(passLabel, gbc);

        JPasswordField passField = new JPasswordField();
        passField.setFont(font);
        passField.setPreferredSize(new Dimension(300, 40)); // تكبير حجم الحقل
        passField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.BLACK, 1),
                new EmptyBorder(10, 10, 10, 10) // زيادة الحشوة داخل الحقل
        ));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(passField, gbc);

        // Login button
        JButton loginButton = new JButton("Login");
        loginButton.setFont(font);
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(new Color(64, 123, 255));
        loginButton.setPreferredSize(new Dimension(150, 40)); // تكبير الزر
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2; // Span across both columns
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(loginButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2; // Span across both columns
        gbc.anchor = GridBagConstraints.CENTER;
        JButton sginubutton = new JButton("sgin up");
        sginubutton.setFont(font);
        sginubutton.setForeground(new Color(64, 123, 255));
        sginubutton.setBackground(Color.WHITE);
        sginubutton.setBorder(null);
        mainPanel.add(sginubutton,gbc);

        mainPanel.setBackground(Color.white);
        // Add main panel to frame
        this.add(mainPanel);


        this.setVisible(true);
    }

    public static void main(String[] args) {
        new Login();
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
