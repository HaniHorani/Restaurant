package src;
import java.awt.HeadlessException;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Main {

    public static void main(String[] args) {
        if (Helper.myUser != null) {
            new Login();
        } else {
            // new Home();
        }

    }
}

class Login extends JFrame {

    public Login() throws HeadlessException {
        setTitle("new frame");
        setVisible(rootPaneCheckingEnabled);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        // setVisible(true);
    }
}
