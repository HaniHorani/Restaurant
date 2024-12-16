package src;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class Login extends JFrame {
    Font font =new Font("Cocon",Font.PLAIN,18);

    public Login() {
        this.setTitle("Login");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(600,700);

        Dimension screenSize =Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth =screenSize.width;
        int screenHeight =screenSize.height;
        this.setLocation((screenWidth-this.getWidth())/2,(screenHeight-this.getHeight())/2);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(new EmptyBorder(20,20,20,20));

        GridBagConstraints gbc =new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill =GridBagConstraints.HORIZONTAL;

        Resizer imagePanel = new Resizer("images\\Tablet login-amico.png");
        imagePanel.setBackground(Color.white);
        imagePanel.setPreferredSize(new Dimension(500, 300));
        gbc.gridx =0;
        gbc.gridy =0;
        gbc.gridwidth =2;
        gbc.anchor =GridBagConstraints.CENTER;
        mainPanel.add(imagePanel, gbc);


        JLabel userLabel =new JLabel("Username:");
        userLabel.setFont(font);
        gbc.gridx =0;
        gbc.gridy =1;
        gbc.gridwidth =1;
        gbc.anchor =GridBagConstraints.EAST;
        mainPanel.add(userLabel,gbc);

        JTextField userTextField =new JTextField();
        userTextField.setFont(font);
        userTextField.setPreferredSize(new Dimension(300,40));
        userTextField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.BLACK,1),
                new EmptyBorder(10,10,10,10)
        ));
        gbc.gridx =1;
        gbc.gridy =1;
        gbc.anchor =GridBagConstraints.WEST;
        mainPanel.add(userTextField,gbc);

        // Password label and field
        JLabel passLabel =new JLabel("Password:");
        passLabel.setFont(font);
        gbc.gridx =0;
        gbc.gridy =2;
        gbc.anchor =GridBagConstraints.EAST;
        mainPanel.add(passLabel,gbc);

        JPasswordField passField =new JPasswordField();
        passField.setFont(font);
        passField.setPreferredSize(new Dimension(300,40));
        passField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.BLACK, 1),
                new EmptyBorder(10, 10, 10, 10)
        ));
        gbc.gridx =1;
        gbc.gridy =2;
        gbc.anchor =GridBagConstraints.WEST;
        mainPanel.add(passField,gbc);

        JButton loginButton = new JButton("Login");
        loginButton.setFont(font);
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(new Color(64, 123, 255));
        loginButton.setPreferredSize(new Dimension(150, 40));
        gbc.gridx =0;
        gbc.gridy =3;
        gbc.gridwidth =2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(loginButton,gbc);

        gbc.gridx =0;
        gbc.gridy =4;
        gbc.gridwidth =2;
        gbc.anchor =GridBagConstraints.CENTER;
        JButton SginButton =new JButton("sgin up");
        SginButton.setFont(font);
        SginButton.setForeground(new Color(64,123,255));
        SginButton.setBackground(Color.WHITE);
        SginButton.setBorder(null);
        mainPanel.add(SginButton,gbc);

        mainPanel.setBackground(Color.white);

        this.add(mainPanel);

        this.setVisible(true);
    }
}

class Resizer extends JPanel {
    private Image image;

    public Resizer(String path) {
        image =new ImageIcon(path).getImage();
        if (image == null) {
            System.out.println("Failed to load image.");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            int panelWidth =getWidth();
            int panelHeight =getHeight();

            int imgWidth =image.getWidth(null);
            int imgHeight =image.getHeight(null);

            double scaleX =(double)panelWidth/imgWidth;
            double scaleY =(double)panelHeight/imgHeight;
            double scale = Math.min(scaleX,scaleY);

            int newWidth =(int)(imgWidth*scale);
            int newHeight =(int)(imgHeight*scale);

            int x =(panelWidth-newWidth)/2;
            int y =(panelHeight-newHeight)/2;

            g.drawImage(image,x,y,newWidth,newHeight,this);
        }
    }
}
