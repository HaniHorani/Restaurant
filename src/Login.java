package src;

import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {
    public Login(){

        this.setTitle("Login");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(200,500);
        JLabel label = new JLabel();
        label.setIcon(new ImageIcon("images\\Tablet login-amico.png"));
        this.add(label);
        show();
    }
    public void show(){
        this.setVisible(true);
    }
}
